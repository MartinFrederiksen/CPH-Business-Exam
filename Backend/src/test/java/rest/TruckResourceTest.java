package rest;

import entities.Truck;
import entities.dto.TruckDTO;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Martin Frederiksen
 */
public class TruckResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private Truck t1, t2;
    private List<TruckDTO> trucks;

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
            em.createQuery("DELETE FROM Truck").executeUpdate();
            t1 = new Truck("Tr01", 10);
            t2 = new Truck("Tr02", 10);
            em.persist(t1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(t2);
            em.getTransaction().commit();
            trucks = new ArrayList();
            trucks.add(new TruckDTO(t1));
            trucks.add(new TruckDTO(t2)); 
        } finally {
            em.close();
        }
    }
    
    @Test
    public void testTruckAll200() throws Exception {
        given()
                .contentType("application/json")
                .get("/Truck/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(trucks.size()));
    }
    
    @Test
    public void testTruckById200() throws Exception {
        given()
                .contentType("application/json")
                .get("/Truck/" + t1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo(t1.getName()));
    }

    @Test
    public void testTruckById400() throws Exception {
        given()
                .contentType("application/json")
                .get("/Truck/0").then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode());
    }
    
    @Test
    public void testTruckById404() throws Exception {
        given()
                .contentType("application/json")
                .get("/Truck/3").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }
    
    @Test
    public void testTruckAdd200() throws Exception {
        given()
                .contentType("application/json")
                .body(new Truck("Tr03", 10))
                .post("/Truck/add").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("Tr03"));
    }
    
    @Test
    public void testTruckAdd400() throws Exception {
        given()
                .contentType("application/json")
                .body(t1)
                .post("/Truck/add").then()
                .assertThat()
                .statusCode(HttpStatus.FOUND_302.getStatusCode());
    }
    
    @Test
    public void testTruckEdit200() throws Exception {
        t2.setName("Tr04");
        given()
                .contentType("application/json")
                .body(t2)
                .put("/Truck/edit/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("Tr04"));
    }
    
    @Test
    public void testTruckEdit404() throws Exception {
        given()
                .contentType("application/json")
                .body(new Truck("Tr03", 10))
                .put("/Truck/edit").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }
    
    @Test
    public void testTruckDelete200() throws Exception {
        given()
                .contentType("application/json")
                .delete("/Truck/delete/" + t1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }
    
    @Test
    public void testTruckDelete400() throws Exception {
        given()
                .contentType("application/json")
                .delete("/Truck/delete/0").then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode());
    }
    
    @Test
    public void testTruckDelete404() throws Exception {
        given()
                .contentType("application/json")
                .delete("/Truck/delete/99").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }
}
