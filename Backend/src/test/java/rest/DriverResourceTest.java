package rest;

import entities.Driver;
import entities.dto.DriverDTO;
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
public class DriverResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private Driver d1, d2;
    private List<DriverDTO> drivers;

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
            em.createQuery("DELETE FROM Driver").executeUpdate();
            d1 = new Driver("Martin");
            d2 = new Driver("Andreas");
            em.persist(d1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(d2);
            em.getTransaction().commit();
            drivers = new ArrayList();
            drivers.add(new DriverDTO(d1));
            drivers.add(new DriverDTO(d2));
        } finally {
            em.close();
        }
    }

    @Test
    public void testDriverAll200() throws Exception {
        given()
                .contentType("application/json")
                .get("/Driver/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(drivers.size()));
    }

    @Test
    public void testDriverById200() throws Exception {
        given()
                .contentType("application/json")
                .get("/Driver/" + d1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo(d1.getName()));
    }

    @Test
    public void testDriverById400() throws Exception {
        given()
                .contentType("application/json")
                .get("/Driver/0").then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode());
    }

    @Test
    public void testDriverById404() throws Exception {
        given()
                .contentType("application/json")
                .get("/Driver/3").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }

    @Test
    public void testDriverAdd200() throws Exception {
        given()
                .contentType("application/json")
                .body(new Driver("TestDriver"))
                .post("/Driver/add").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("TestDriver"));
    }

    @Test
    public void testDriverAdd400() throws Exception {
        given()
                .contentType("application/json")
                .body(d1)
                .post("/Driver/add").then()
                .assertThat()
                .statusCode(HttpStatus.FOUND_302.getStatusCode());
    }

    @Test
    public void testDriverEdit200() throws Exception {
        d2.setName("newNameForDriver2");
        given()
                .contentType("application/json")
                .body(d2)
                .put("/Driver/edit/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("newNameForDriver2"));
    }

    @Test
    public void testDriverEdit404() throws Exception {
        given()
                .contentType("application/json")
                .body(new Driver("TestDriver"))
                .put("/Driver/edit").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }

    @Test
    public void testDriverDelete200() throws Exception {
        given()
                .contentType("application/json")
                .delete("/Driver/delete/" + d1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }

    @Test
    public void testDriverDelete400() throws Exception {
        given()
                .contentType("application/json")
                .delete("/Driver/delete/0").then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode());
    }

    @Test
    public void testDriverDelete404() throws Exception {
        given()
                .contentType("application/json")
                .delete("/Driver/delete/99").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }
}
