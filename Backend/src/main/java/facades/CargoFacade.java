package facades;

import entities.Cargo;
import entities.dto.CargoDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Martin Frederiksen
 */
public class CargoFacade implements IFacade<CargoDTO> {
    private static CargoFacade instance;
    private static EntityManagerFactory emf;
    
    private CargoFacade() {}
 
    public static CargoFacade getCargoFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CargoFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public List<CargoDTO> getAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT new entities.dto.CargoDTO(cargo) FROM Cargo cargo", CargoDTO.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public CargoDTO getById(long id) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Cargo cargo = em.find(Cargo.class, id);
            if(cargo == null) {
                throw new WebApplicationException("Could not find cargo with id: " + id, 404);
            }
            return new CargoDTO(cargo);
        } finally {
            em.close();
        }
    }
    
    @Override
    public CargoDTO add(CargoDTO cargoDTO) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            if(cargoDTO.getId() != null){
                throw new WebApplicationException("Cargo already exists", 302);
            }
            Cargo cargo = new Cargo(cargoDTO.getName(), cargoDTO.getWeight(), cargoDTO.getUnits());
            em.getTransaction().begin();
            em.persist(cargo);
            em.getTransaction().commit();
            return new CargoDTO(cargo);
        } finally {
            em.close();
        }
    }

    @Override
    public CargoDTO delete(long id) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Cargo cargo = em.find(Cargo.class, id);
            if(cargo == null) {
                throw new WebApplicationException("Could not find cargo with id: " + id, 404);
            }
            em.getTransaction().begin();
            em.remove(cargo);
            em.getTransaction().commit();
            return new CargoDTO(cargo);
        } finally {
            em.close();
        }
    }

    @Override
    public CargoDTO edit(CargoDTO cargoDTO) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            if(cargoDTO.getId() == null) {
                throw new WebApplicationException("Could not find cargo with id: " + cargoDTO.getId(), 404);
            }
            Cargo cargo = new Cargo(cargoDTO.getName(), cargoDTO.getWeight(), cargoDTO.getUnits());
            em.getTransaction().begin();
            em.merge(cargo);
            em.getTransaction().commit();
            return new CargoDTO(cargo);
        } finally {
            em.close();
        }
    }
}
