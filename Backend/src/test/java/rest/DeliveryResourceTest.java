package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Delivery;
import entities.dto.DeliveryDTO;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
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
public class DeliveryResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private Delivery d1, d2;
    private List<DeliveryDTO> deliveries;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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
            em.createQuery("DELETE FROM Delivery").executeUpdate();
            d1 = new Delivery(new Date(), "Somewhere 123", "Elsewhere 321");
            d2 = new Delivery(new Date(), "Somewhere 123", "Elsewhere 321");
            em.persist(d1);
            em.persist(d2);
            em.getTransaction().commit();
            deliveries = new ArrayList();
            deliveries.add(new DeliveryDTO(d1));
            deliveries.add(new DeliveryDTO(d2)); 
        } finally {
            em.close();
        }
    }
    
    @Test
    public void testDeliveryAll200() throws Exception {
        given()
                .contentType("application/json")
                .get("/Delivery/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(deliveries.size()));
    }
    
    @Test
    public void testDeliveryById200() throws Exception {
        given()
                .contentType("application/json")
                .get("/Delivery/" + d1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("destination", equalTo(d1.getDestination()));
    }

    @Test
    public void testDeliveryById400() throws Exception {
        given()
                .contentType("application/json")
                .get("/Delivery/0").then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode());
    }
    
    @Test
    public void testDeliveryById404() throws Exception {
        given()
                .contentType("application/json")
                .get("/Delivery/3").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }
    
    @Test
    public void testDeliveryAdd200() throws Exception {
        given()
                .contentType("application/json")
                .body("{\"destination\":\"Elsewhere 32133\", \"fromLocation\":\"Somewhere 123\"}")
                .post("/Delivery/add").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("destination", equalTo("Elsewhere 32133"));
    }
    
    @Test
    public void testDeliveryAdd400() throws Exception {
        given()
                .contentType("application/json")
                .body("{\"id\":\"" + d1.getId() + "\", \"destination\":\"Elsewhere 32133\", \"fromLocation\":\"Somewhere 123\"}")
                .post("/Delivery/add").then()
                .assertThat()
                .statusCode(HttpStatus.FOUND_302.getStatusCode());
    }
    
    @Test
    public void testDeliveryEdit200() throws Exception {
        d2.setFromLocation("ThisIsANewLocation");
        given()
                .contentType("application/json")
                .body("{\"id\":\"" + d2.getId() + "\", \"destination\":\"" + d2.getDestination() + "\", \"fromLocation\":\"" + d2.getFromLocation() + "\"}")
                .put("/Delivery/edit/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("fromLocation", equalTo("ThisIsANewLocation"));
    }
    
    @Test
    public void testDeliveryEdit404() throws Exception {
        given()
                .contentType("application/json")
                .body("{\"destination\":\"Elsewhere 32133\", \"fromLocation\":\"Somewhere 123\"}")
                .put("/Delivery/edit").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }
    
    @Test
    public void testDeliveryDelete200() throws Exception {
        given()
                .contentType("application/json")
                .delete("/Delivery/delete/" + d1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }
    
    @Test
    public void testDeliveryDelete400() throws Exception {
        given()
                .contentType("application/json")
                .delete("/Delivery/delete/0").then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode());
    }
    
    @Test
    public void testDeliveryDelete404() throws Exception {
        given()
                .contentType("application/json")
                .delete("/Delivery/delete/99").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }
}
