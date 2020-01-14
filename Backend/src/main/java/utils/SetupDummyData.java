package utils;

import entities.Cargo;
import entities.Delivery;
import entities.Driver;
import entities.Truck;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Martin Frederiksen
 */
public class SetupDummyData {
    public void setup() {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
        EntityManager em = emf.createEntityManager();

        Driver d1 = new Driver("Martin");
        Driver d2 = new Driver("Andreas");
        

        Truck t1 = new Truck("Tr01", 10);
        Truck t2 = new Truck("Tr02", 10);
        
        Delivery de1 = new Delivery(new Date(), "Somewhere 123", "Elsewhere 321");
        Delivery de2 = new Delivery(new Date(), "Somewhere 123", "Elsewhere 321");
        Delivery de3 = new Delivery(new Date(), "Somewhere 123", "Elsewhere 321");
        Delivery de4 = new Delivery(new Date(), "Somewhere 123", "Elsewhere 321");
        
        de1.setTruck(t1);
        de2.setTruck(t1);
        de3.setTruck(t2);
        de4.setTruck(t2);
        
        
        Cargo c1 = new Cargo("Hardware", 10.0, 1);
        Cargo c2 = new Cargo("Mouse", 0.2, 10);
        Cargo c3 = new Cargo("software", 0.1, 100);
        Cargo c4 = new Cargo("keyboard", 0.5, 10);
        Cargo c5 = new Cargo("Soundbar", 5, 5);
        
        c1.setDelivery(de1);
        c2.setDelivery(de1);
        c3.setDelivery(de2);
        c4.setDelivery(de3);
        c5.setDelivery(de4);
        
        try {
            em.getTransaction().begin();
            
            
            em.persist(t1);
            em.persist(t2);
            em.persist(de1);
            em.persist(de2);
            em.persist(de3);
            em.persist(de4);
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(c4);
            em.persist(c5);
            
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    /*
    public static void main(String[] args) {
        new SetupDummyData().setup();   
    }*/
}
