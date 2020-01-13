package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Martin Frederiksen
 */
@Entity
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date shippingDate;
    private String fromLocation;
    private String destination;
    
    @OneToMany(mappedBy = "delivery")
    private List<Cargo> cargoes;
    @ManyToOne
    private Truck truck;

    public Delivery() {
    }

    public Delivery(Date shippingDate, String fromLocation, String destination) {
        this.shippingDate = shippingDate;
        this.fromLocation = fromLocation;
        this.destination = destination;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<Cargo> getCargoes() {
        return cargoes;
    }

    public void setCargoes(List<Cargo> cargoes) {
        this.cargoes = cargoes;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }
}
