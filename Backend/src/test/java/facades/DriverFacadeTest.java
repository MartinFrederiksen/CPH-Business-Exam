package facades;

import entities.Driver;
import entities.dto.DriverDTO;
import java.util.ArrayList;
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
public class DriverFacadeTest {
    private static EntityManagerFactory emf;
    private static DriverFacade facade;
    private Driver d1, d2, d3;
    private List<DriverDTO> drivers;
    
    public DriverFacadeTest() {
    }
    
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = DriverFacade.getDriverFacade(emf);
    }
    
    @BeforeAll
    public static void setUpClassV2() {
       emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST,EMF_Creator.Strategy.DROP_AND_CREATE);
       facade = DriverFacade.getDriverFacade(emf);
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
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
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getDriverFacade method, of class DriverFacade.
     */
    @Test
    public void testGetDriverFacade() {
        EntityManagerFactory _emf = emf.createEntityManager().getEntityManagerFactory();
        DriverFacade expResult = facade;
        DriverFacade result = DriverFacade.getDriverFacade(_emf);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAll method, of class DriverFacade.
     */
    @Test
    public void testGetAll() {
        List<DriverDTO> expResult = drivers;
        List<DriverDTO> result = facade.getAll();
        assertEquals(expResult, result);
    }

    /**
     * Test of getById method, of class DriverFacade.
     */
    @Test
    public void testGetById() {
        DriverDTO expResult = new DriverDTO(d2);
        DriverDTO result = facade.getById(expResult.getId());
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class DriverFacade.
     */
    @Test
    public void testAdd() {
        d3 = new Driver("TestDriver");
        List<DriverDTO> expResult;
        DriverDTO result = facade.add(new DriverDTO(d3));
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            expResult = em.createQuery("SELECT new entities.dto.DriverDTO(driver) FROM Driver driver", DriverDTO.class).getResultList();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        assertEquals(expResult.get(2), result);
    }

    /**
     * Test of delete method, of class DriverFacade.
     */
    @Test
    public void testDelete() {
        EntityManager em = emf.createEntityManager();
        d3 = new Driver("TestDriver"); 
        try{
            em.getTransaction().begin();
            em.persist(d3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        DriverDTO expResult = new DriverDTO(d3);
        DriverDTO result = facade.delete(expResult.getId());
        assertEquals(expResult, result);
    }

    /**
     * Test of edit method, of class DriverFacade.
     */
    @Test
    public void testEdit() {
        d2.setName("Testname");
        DriverDTO expResult = new DriverDTO(d2);
        DriverDTO result = facade.edit(expResult);
        assertEquals(expResult, result);
    }
    
}
