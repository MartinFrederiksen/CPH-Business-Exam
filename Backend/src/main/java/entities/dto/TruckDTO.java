package entities.dto;

import entities.Driver;
import entities.Truck;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Martin Frederiksen
 */
public class TruckDTO {
    private Long id;
    private String name;
    private int Capacity;
    private List<DriverDTO> drivers;

    public TruckDTO() {
    }

    public TruckDTO(String name, int Capacity, List<DriverDTO> drivers) {
        this.name = name;
        this.Capacity = Capacity;
        this.drivers = drivers;
    }
    
    public TruckDTO(Truck truck) {
        this.id = truck.getId();
        this.name = truck.getName();
        this.Capacity = truck.getCapacity();
        this.drivers = new ArrayList();
        for(Driver d : truck.getDrivers()){
            drivers.add(new DriverDTO(d));
        }
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
        return Capacity;
    }

    public void setCapacity(int Capacity) {
        this.Capacity = Capacity;
    }

    public List<DriverDTO> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<DriverDTO> drivers) {
        this.drivers = drivers;
    }
    
}
