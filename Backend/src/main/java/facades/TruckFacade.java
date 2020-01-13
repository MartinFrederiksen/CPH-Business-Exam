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
public class TruckFacade implements IFacade<TruckDTO> {
    private static TruckFacade instance;
    private static EntityManagerFactory emf;
    
    private TruckFacade() {}
 
    public static TruckFacade getTruckFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TruckFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public List<TruckDTO> getAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT new entities.dto.TruckDTO(truck) FROM Truck truck", TruckDTO.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public TruckDTO getById(long id) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Truck truck = em.find(Truck.class, id);
            if(truck == null) {
                throw new WebApplicationException("Could not find truck with id: " + id);
            }
            return new TruckDTO(truck);
        } finally {
            em.close();
        }
    }
    
    @Override
    public TruckDTO add(TruckDTO truckDTO) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Truck truck = em.find(Truck.class, truckDTO.getId());
            if(truck == null){
                throw new WebApplicationException("Truck already exists");
            }
            em.getTransaction().begin();
            em.persist(truck);
            em.getTransaction().commit();
            return new TruckDTO(truck);
        } finally {
            em.close();
        }
    }

    @Override
    public TruckDTO delete(long id) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Truck truck = em.find(Truck.class, id);
            if(truck == null) {
                throw new WebApplicationException("Could not find truck with id: " + id);
            }
            em.getTransaction().begin();
            em.remove(truck);
            em.getTransaction().commit();
            return new TruckDTO(truck);
        } finally {
            em.close();
        }
    }

    @Override
    public TruckDTO edit(TruckDTO truckDTO) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Truck truck = em.find(Truck.class, truckDTO.getId());
            if(truck == null) {
                throw new WebApplicationException("Could not find truck with id: " + truckDTO.getId());
            }
            List<Driver> drivers = new ArrayList();
            for(DriverDTO d : truckDTO.getDrivers()) {
                drivers.add(em.find(Driver.class, d.getId()));
            }
            truck.setName(truckDTO.getName());
            truck.setCapacity(truck.getCapacity());
            truck.setDrivers(drivers);
            em.getTransaction().begin();
            em.merge(truck);
            em.getTransaction().commit();
            return new TruckDTO(truck);
        } finally {
            em.close();
        }
    }
}
