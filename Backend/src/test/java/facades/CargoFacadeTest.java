package facades;

import entities.Cargo;
import entities.dto.CargoDTO;
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
public class CargoFacadeTest {
    private static EntityManagerFactory emf;
    private static CargoFacade facade;
    private Cargo c1, c2, c3;
    private List<CargoDTO> cargoes;
    
    public CargoFacadeTest() {
    }
    
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = CargoFacade.getCargoFacade(emf);
    }
    
    @BeforeAll
    public static void setUpClassV2() {
       emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST,EMF_Creator.Strategy.DROP_AND_CREATE);
       facade = CargoFacade.getCargoFacade(emf);
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
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
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getCargoFacade method, of class CargoFacade.
     */
    @Test
    public void testGetCargoFacade() {
        EntityManagerFactory _emf = emf.createEntityManager().getEntityManagerFactory();
        CargoFacade expResult = facade;
        CargoFacade result = CargoFacade.getCargoFacade(_emf);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAll method, of class CargoFacade.
     */
    @Test
    public void testGetAll() {
        List<CargoDTO> expResult = cargoes;
        List<CargoDTO> result = facade.getAll();
        assertEquals(expResult, result);
    }

    /**
     * Test of getById method, of class CargoFacade.
     */
    @Test
    public void testGetById() {
        CargoDTO expResult = new CargoDTO(c2);
        CargoDTO result = facade.getById(expResult.getId());
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class CargoFacade.
     */
    @Test
    public void testAdd() {
        c3 = new Cargo("software", 0.1, 100);
        List<CargoDTO> expResult;
        CargoDTO result = facade.add(new CargoDTO(c3));
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            expResult = em.createQuery("SELECT new entities.dto.CargoDTO(cargo) FROM Cargo cargo", CargoDTO.class).getResultList();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        assertEquals(expResult.get(2), result);
    }

    /**
     * Test of delete method, of class CargoFacade.
     */
    @Test
    public void testDelete() {
        EntityManager em = emf.createEntityManager();
        c3 = new Cargo("software", 0.1, 100); 
        try{
            em.getTransaction().begin();
            em.persist(c3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        CargoDTO expResult = new CargoDTO(c3);
        CargoDTO result = facade.delete(expResult.getId());
        assertEquals(expResult, result);

    }

    /**
     * Test of edit method, of class CargoFacade.
     */
    @Test
    public void testEdit() {
        c2.setName("Testname");
        CargoDTO expResult = new CargoDTO(c2);
        CargoDTO result = facade.edit(expResult);
        assertEquals(expResult, result);

    }
    
}
