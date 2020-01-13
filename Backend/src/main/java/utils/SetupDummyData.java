package utils;

import entities.Cargo;
import entities.Delivery;
import entities.Driver;
import entities.Truck;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Martin Frederiksen
 */
public class SetupDummyData {
    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
        EntityManager em = emf.createEntityManager();

        Driver d1 = new Driver("Martin");
        Driver d2 = new Driver("Andreas");
        List<Driver> drivers = new ArrayList();
        drivers.add(d1);
        drivers.add(d2);
        
        Cargo c1 = new Cargo("Hardware", 10.0, 1);
        Cargo c2 = new Cargo("Mouse", 0.2, 10);
        List<Cargo> cargoes1 = new ArrayList();
        cargoes1.add(c1);
        cargoes1.add(c2);
        
        Cargo c3 = new Cargo("software", 0.1, 100);
        Cargo c4 = new Cargo("keyboard", 0.5, 10);
        List<Cargo> cargoes2 = new ArrayList();
        cargoes2.add(c3);
        cargoes2.add(c4);
          
        Truck t1 = new Truck("Tr01", 10, drivers, null);
        
        Delivery de1 = new Delivery(new Date(), "Somewhere 123", "Elsewhere 321", cargoes1, t1);
        Delivery de2 = new Delivery(new Date(), "Somewhere 123", "Elsewhere 321", cargoes2, t1);
        List<Delivery> deliveries = new ArrayList();
        
        t1.setDeliveries(deliveries);
        
        try {
            em.getTransaction().begin();
            em.persist(d1);
            em.persist(d2);
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(c4);
            em.persist(t1);
            em.persist(de1);
            em.persist(de2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        
    }
}
