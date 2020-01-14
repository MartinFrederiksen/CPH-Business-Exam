package facades;

import entities.Truck;
import entities.dto.TruckDTO;
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
public class TruckFacadeTest {
    private static EntityManagerFactory emf;
    private static TruckFacade facade;
    private Truck t1, t2, t3;
    private List<TruckDTO> trucks;
    
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = TruckFacade.getTruckFacade(emf);
    }
    
    @BeforeAll
    public static void setUpClassV2() {
       emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST,EMF_Creator.Strategy.DROP_AND_CREATE);
       facade = TruckFacade.getTruckFacade(emf);
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
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
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getTruckFacade method, of class TruckFacade.
     */
    @Test
    public void testGetTruckFacade() {
        EntityManagerFactory _emf = emf.createEntityManager().getEntityManagerFactory();
        TruckFacade expResult = facade;
        TruckFacade result = TruckFacade.getTruckFacade(_emf);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAll method, of class TruckFacade.
     */
    @Test
    public void testGetAll() {
        List<TruckDTO> expResult = trucks;
        List<TruckDTO> result = facade.getAll();
        assertEquals(expResult, result);
    }

    /**
     * Test of getById method, of class TruckFacade.
     */
    @Test
    public void testGetById() {
        TruckDTO expResult = new TruckDTO(t2);
        TruckDTO result = facade.getById(expResult.getId());
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class TruckFacade.
     */
    @Test
    public void testAdd() {
        t3 = new Truck("Tr03", 10);
        List<TruckDTO> expResult;
        TruckDTO result = facade.add(new TruckDTO(t3));
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            expResult = em.createQuery("SELECT new entities.dto.TruckDTO(truck) FROM Truck truck", TruckDTO.class).getResultList();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        assertEquals(expResult.get(2), result);
    }

    /**
     * Test of delete method, of class TruckFacade.
     */
    @Test
    public void testDelete() {
        EntityManager em = emf.createEntityManager();
        t3 = new Truck("Tr03", 10); 
        try{
            em.getTransaction().begin();
            em.persist(t3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        TruckDTO expResult = new TruckDTO(t3);
        TruckDTO result = facade.delete(expResult.getId());
        assertEquals(expResult, result);
    }

    /**
     * Test of edit method, of class TruckFacade.
     */
    @Test
    public void testEdit() {
        t2.setName("TestTruck");
        TruckDTO expResult = new TruckDTO(t2);
        TruckDTO result = facade.edit(expResult);
        assertEquals(expResult, result);
    }
    
}
