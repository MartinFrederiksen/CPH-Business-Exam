package facades;

import entities.Delivery;
import entities.dto.DeliveryDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;

/**
 *
 * @author Martin Frederiksen
 */
public class DeliveryFacadeTest {
    private static EntityManagerFactory emf;
    private static DeliveryFacade facade;
    private Delivery d1, d2, d3;
    private List<DeliveryDTO> deliveries;
    
    public DeliveryFacadeTest() {
    }
    
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = DeliveryFacade.getDeliveryFacade(emf);
    }
    
    @BeforeAll
    public static void setUpClassV2() {
       emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST,EMF_Creator.Strategy.DROP_AND_CREATE);
       facade = DeliveryFacade.getDeliveryFacade(emf);
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Delivery").executeUpdate();
            d1 = new Delivery(new Date(), "Somewhere 123", "Elsewhere 321");
            d2 = new Delivery(new Date(), "Somewhere 123", "Elsewhere 321");
            em.persist(d1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(d2);
            em.getTransaction().commit();
            deliveries = new ArrayList();
            deliveries.add(new DeliveryDTO(d1));
            deliveries.add(new DeliveryDTO(d2));
        } finally {
            em.close();
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getDeliveryFacade method, of class DeliveryFacade.
     */
    @Test
    public void testGetDeliveryFacade() {
        EntityManagerFactory _emf = emf.createEntityManager().getEntityManagerFactory();
        DeliveryFacade expResult = facade;
        DeliveryFacade result = DeliveryFacade.getDeliveryFacade(_emf);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAll method, of class DeliveryFacade.
     */
    @Test
    public void testGetAll() {
        List<DeliveryDTO> expResult = deliveries;
        List<DeliveryDTO> result = facade.getAll();
        System.out.println(expResult.toString());
        System.out.println(result.toString());
        assertEquals(expResult, result);
    }

    /**
     * Test of getById method, of class DeliveryFacade.
     */
    @Test
    public void testGetById() {
        DeliveryDTO expResult = new DeliveryDTO(d2);
        DeliveryDTO result = facade.getById(expResult.getId());
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class DeliveryFacade.
     */
    @Test
    public void testAdd() {
        d3 = new Delivery(new Date(), "Somewhere 123", "Elsewhere 321");
        List<DeliveryDTO> expResult;
        DeliveryDTO result = facade.add(new DeliveryDTO(d3));
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            expResult = em.createQuery("SELECT new entities.dto.DeliveryDTO(delivery) FROM Delivery delivery", DeliveryDTO.class).getResultList();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        assertEquals(expResult.get(2), result);
    }

    /**
     * Test of delete method, of class DeliveryFacade.
     */
    @Test
    public void testDelete() {
        EntityManager em = emf.createEntityManager();
        d3 = new Delivery(new Date(), "Somewhere 1234", "Elsewhere 4321");
        try{
            em.getTransaction().begin();
            em.persist(d3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        DeliveryDTO expResult = new DeliveryDTO(d3);
        DeliveryDTO result = facade.delete(expResult.getId());
        assertEquals(expResult, result);
    }

    /**
     * Test of edit method, of class DeliveryFacade.
     */
    @Test
    public void testEdit() {
        d2.setDestination("NewDestination 123");
        DeliveryDTO expResult = new DeliveryDTO(d2);
        DeliveryDTO result = facade.edit(expResult);
        assertEquals(expResult, result);
    }
    
}
