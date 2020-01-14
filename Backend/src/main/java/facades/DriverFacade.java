package facades;

import entities.Driver;
import entities.dto.DriverDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Martin Frederiksen
 */
public class DriverFacade implements IFacade<DriverDTO> {
    private static DriverFacade instance;
    private static EntityManagerFactory emf;
    
    private DriverFacade() {}
 
    public static DriverFacade getDriverFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DriverFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public List<DriverDTO> getAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT new entities.dto.DriverDTO(driver) FROM Driver driver", DriverDTO.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public DriverDTO getById(long id) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Driver driver = em.find(Driver.class, id);
            if(driver == null) {
                throw new WebApplicationException("Could not find driver with id: " + id, 404);
            }
            return new DriverDTO(driver);
        } finally {
            em.close();
        }
    }
    
    @Override
    public DriverDTO add(DriverDTO driverDTO) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            if(driverDTO.getId() != null){
                throw new WebApplicationException("Driver already exists", 302);
            }
            Driver driver = new Driver(driverDTO.getName());
            em.getTransaction().begin();
            em.persist(driver);
            em.getTransaction().commit();
            return new DriverDTO(driver);
        } finally {
            em.close();
        }
    }

    @Override
    public DriverDTO delete(long id) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Driver driver = em.find(Driver.class, id);
            if(driver == null) {
                throw new WebApplicationException("Could not find driver with id: " + id, 404);
            }
            em.getTransaction().begin();
            em.remove(driver);
            em.getTransaction().commit();
            return new DriverDTO(driver);
        } finally {
            em.close();
        }
    }

    @Override
    public DriverDTO edit(DriverDTO driverDTO) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            if(driverDTO.getId() == null) {
                throw new WebApplicationException("Could not find driver with id: " + driverDTO.getId(), 404);
            }
            Driver driver = em.find(Driver.class, driverDTO.getId());
            driver.setName(driverDTO.getName());
            em.getTransaction().begin();
            em.merge(driver);
            em.getTransaction().commit();
            return new DriverDTO(driver);
        } finally {
            em.close();
        }
    }
}
