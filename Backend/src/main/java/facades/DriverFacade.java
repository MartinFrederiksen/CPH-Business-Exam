package facades;

import entities.Driver;
import entities.Truck;
import entities.dto.DriverDTO;
import entities.dto.TruckDTO;
import java.util.ArrayList;
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
                throw new WebApplicationException("Could not find driver with id: " + id);
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
            Driver driver = em.find(Driver.class, driverDTO.getId());
            if(driver == null){
                throw new WebApplicationException("Driver already exists");
            }
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
                throw new WebApplicationException("Could not find driver with id: " + id);
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
            Driver driver = em.find(Driver.class, driverDTO.getId());
            if(driver == null) {
                throw new WebApplicationException("Could not find driver with id: " + driverDTO.getId());
            }
            List<Truck> trucks = new ArrayList();
            for(TruckDTO t : driverDTO.getTrucks()) {
                trucks.add(em.find(Truck.class, t.getId()));
            }
            driver.setName(driverDTO.getName());
            driver.setTrucks(trucks);
            em.getTransaction().begin();
            em.merge(driver);
            em.getTransaction().commit();
            return new DriverDTO(driver);
        } finally {
            em.close();
        }
    }
}
