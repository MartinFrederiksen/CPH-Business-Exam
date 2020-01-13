package facades;

import entities.Cargo;
import entities.Delivery;
import entities.Truck;
import entities.dto.CargoDTO;
import entities.dto.DeliveryDTO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Martin Frederiksen
 */
public class DeliveryFacade implements IFacade<DeliveryDTO> {
    private static DeliveryFacade instance;
    private static EntityManagerFactory emf;
    
    private DeliveryFacade() {}
 
    public static DeliveryFacade getDeliveryFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DeliveryFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public List<DeliveryDTO> getAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT new entities.dto.DeliveryDTO(delivery) FROM Delivery delivery", DeliveryDTO.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public DeliveryDTO getById(long id) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Delivery delivery = em.find(Delivery.class, id);
            if(delivery == null) {
                throw new WebApplicationException("Could not find delivery with id: " + id);
            }
            return new DeliveryDTO(delivery);
        } finally {
            em.close();
        }
    }
    
    @Override
    public DeliveryDTO add(DeliveryDTO deliveryDTO) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Delivery delivery = em.find(Delivery.class, deliveryDTO.getId());
            if(delivery == null){
                throw new WebApplicationException("Delivery already exists");
            }
            em.getTransaction().begin();
            em.persist(delivery);
            em.getTransaction().commit();
            return new DeliveryDTO(delivery);
        } finally {
            em.close();
        }
    }

    @Override
    public DeliveryDTO delete(long id) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Delivery delivery = em.find(Delivery.class, id);
            if(delivery == null) {
                throw new WebApplicationException("Could not find delivery with id: " + id);
            }
            em.getTransaction().begin();
            em.remove(delivery);
            em.getTransaction().commit();
            return new DeliveryDTO(delivery);
        } finally {
            em.close();
        }
    }

    @Override
    public DeliveryDTO edit(DeliveryDTO deliveryDTO) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Delivery delivery = em.find(Delivery.class, deliveryDTO.getId());
            if(delivery == null) {
                throw new WebApplicationException("Could not find delivery with id: " + deliveryDTO.getId());
            }
            List<Cargo> cargoes = new ArrayList();
            for(CargoDTO c : deliveryDTO.getCargoes()) {
                cargoes.add(em.find(Cargo.class, c.getId()));
            }
            Truck truck = em.find(Truck.class, deliveryDTO.getTruck().getId());
            delivery.setShippingDate(deliveryDTO.getShippingDate());
            delivery.setFromLocation(deliveryDTO.getFromLocation());
            delivery.setDestination(deliveryDTO.getDestination());
            delivery.setCargoes(cargoes);
            delivery.setTruck(truck);
            em.getTransaction().begin();
            em.merge(delivery);
            em.getTransaction().commit();
            return new DeliveryDTO(delivery);
        } finally {
            em.close();
        }
    }
}
