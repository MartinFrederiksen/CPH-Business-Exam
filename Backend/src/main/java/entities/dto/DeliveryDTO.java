package entities.dto;

import entities.Delivery;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Martin Frederiksen
 */
public class DeliveryDTO {
    private Long id;
    private Date shippingDate;
    private String fromLocation;
    private String destination;
    private List<CargoDTO> cargoes;
    private TruckDTO truck;

    public DeliveryDTO() {
    }

    public DeliveryDTO(Date shippingDate, String fromLocation, String destination) {
        this.shippingDate = shippingDate;
        this.fromLocation = fromLocation;
        this.destination = destination;
    }
    
    public DeliveryDTO(Delivery delivery) {
        this.id = delivery.getId();
        this.shippingDate = delivery.getShippingDate();
        this.fromLocation = delivery.getFromLocation();
        this.destination = delivery.getDestination();
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

    public List<CargoDTO> getCargoes() {
        return cargoes;
    }

    public void setCargoes(List<CargoDTO> cargoes) {
        this.cargoes = cargoes;
    }

    public TruckDTO getTruck() {
        return truck;
    }

    public void setTruck(TruckDTO truck) {
        this.truck = truck;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        //hash = 59 * hash + Objects.hashCode(this.shippingDate);
        hash = 59 * hash + Objects.hashCode(this.fromLocation);
        hash = 59 * hash + Objects.hashCode(this.destination);
        hash = 59 * hash + Objects.hashCode(this.cargoes);
        hash = 59 * hash + Objects.hashCode(this.truck);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeliveryDTO other = (DeliveryDTO) obj;
        if (!Objects.equals(this.fromLocation, other.fromLocation)) {
            return false;
        }
        if (!Objects.equals(this.destination, other.destination)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        /*if (!Objects.equals(this.shippingDate, other.shippingDate)) {
            return false;
        }*/
        if (!Objects.equals(this.cargoes, other.cargoes)) {
            return false;
        }
        if (!Objects.equals(this.truck, other.truck)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DeliveryDTO{" + "id=" + id + ", shippingDate=" + shippingDate + ", fromLocation=" + fromLocation + ", destination=" + destination + ", cargoes=" + cargoes + ", truck=" + truck + '}';
    }
    
}
