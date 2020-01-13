package entities.dto;

import entities.Cargo;
import entities.Delivery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Martin Frederiksen
 */
public class DeliveryDTO {
    private Long id;
    private Date shippingDate;
    private String fromLocation;
    private String Destination;
    private List<CargoDTO> cargoes;
    private TruckDTO truck;

    public DeliveryDTO() {
    }

    public DeliveryDTO(Long id, Date shippingDate, String fromLocation, String Destination, List<CargoDTO> cargoes, TruckDTO truck) {
        this.id = id;
        this.shippingDate = shippingDate;
        this.fromLocation = fromLocation;
        this.Destination = Destination;
        this.cargoes = cargoes;
        this.truck = truck;
    }
    
    public DeliveryDTO(Delivery delivery) {
        this.shippingDate = delivery.getShippingDate();
        this.fromLocation = delivery.getFromLocation();
        this.Destination = delivery.getDestination();
        this.cargoes = new ArrayList();
        for(Cargo c : delivery.getCargoes()){
            cargoes.add(new CargoDTO(c));
        }
        this.truck = new TruckDTO(delivery.getTruck());
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
        return Destination;
    }

    public void setDestination(String Destination) {
        this.Destination = Destination;
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
}
