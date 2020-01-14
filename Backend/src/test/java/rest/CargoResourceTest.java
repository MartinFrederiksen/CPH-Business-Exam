package rest;

import entities.Cargo;
import entities.dto.CargoDTO;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Martin Frederiksen
 */
public class CargoResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private Cargo c1, c2;
    private List<CargoDTO> cargoes;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }
    
    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }
    
    @AfterAll
    public static void tearDownClass() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }
    
    @BeforeEach
    public void setUp() throws MalformedURLException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Cargo").executeUpdate();
            c1 = new Cargo("Hardware", 10.0, 1);
            c2 = new Cargo("Mouse", 0.2, 10);
            em.persist(c1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(c2);
            em.getTransaction().commit();
            cargoes = new ArrayList();
            cargoes.add(new CargoDTO(c1));
            cargoes.add(new CargoDTO(c2)); 
        } finally {
            em.close();
        }
    }
    
    @Test
    public void testCargoAll200() throws Exception {
        given()
                .contentType("application/json")
                .get("/Cargo/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(cargoes.size()));
    }
    
    @Test
    public void testCargoById200() throws Exception {
        given()
                .contentType("application/json")
                .get("/Cargo/" + c1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo(c1.getName()));
    }

    @Test
    public void testCargoById400() throws Exception {
        given()
                .contentType("application/json")
                .get("/Cargo/0").then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode());
    }
    
    @Test
    public void testCargoById404() throws Exception {
        given()
                .contentType("application/json")
                .get("/Cargo/3").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }
    
    @Test
    public void testCargoAdd200() throws Exception {
        given()
                .contentType("application/json")
                .body(new Cargo("software", 0.1, 100))
                .post("/Cargo/add").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("software"));
    }
    
    @Test
    public void testCargoAdd400() throws Exception {
        given()
                .contentType("application/json")
                .body(c1)
                .post("/Cargo/add").then()
                .assertThat()
                .statusCode(HttpStatus.FOUND_302.getStatusCode());
    }
    
    @Test
    public void testCargoEdit200() throws Exception {
        c2.setName("software");
        given()
                .contentType("application/json")
                .body(c2)
                .put("/Cargo/edit/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("software"));
    }
    
    @Test
    public void testCargoEdit404() throws Exception {
        given()
                .contentType("application/json")
                .body(new Cargo("software", 0.1, 100))
                .put("/Cargo/edit").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }
    
    @Test
    public void testCargoDelete200() throws Exception {
        given()
                .contentType("application/json")
                .delete("/Cargo/delete/" + c1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }
    
    @Test
    public void testCargoDelete400() throws Exception {
        given()
                .contentType("application/json")
                .delete("/Cargo/delete/0").then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode());
    }
    
    @Test
    public void testCargoDelete404() throws Exception {
        given()
                .contentType("application/json")
                .delete("/Cargo/delete/99").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }
}
