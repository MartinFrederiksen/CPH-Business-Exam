package entities.dto;

import entities.Truck;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Martin Frederiksen
 */
public class TruckDTO {
    private Long id;
    private String name;
    private int capacity;
    private List<DriverDTO> drivers;
    private List<DeliveryDTO> deliveries;

    public TruckDTO() {
    }

    public TruckDTO(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.drivers = drivers;
    }
    
    public TruckDTO(Truck truck) {
        this.id = truck.getId();
        this.name = truck.getName();
        this.capacity = truck.getCapacity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int Capacity) {
        this.capacity = Capacity;
    }

    public List<DriverDTO> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<DriverDTO> drivers) {
        this.drivers = drivers;
    }

    public List<DeliveryDTO> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<DeliveryDTO> deliveries) {
        this.deliveries = deliveries;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.id);
        hash = 61 * hash + Objects.hashCode(this.name);
        hash = 61 * hash + this.capacity;
        hash = 61 * hash + Objects.hashCode(this.drivers);
        hash = 61 * hash + Objects.hashCode(this.deliveries);
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
        final TruckDTO other = (TruckDTO) obj;
        if (this.capacity != other.capacity) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.drivers, other.drivers)) {
            return false;
        }
        if (!Objects.equals(this.deliveries, other.deliveries)) {
            return false;
        }
        return true;
    }
    
}
