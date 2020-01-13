package entities.dto;

import entities.Driver;
import java.util.List;

/**
 *
 * @author Martin Frederiksen
 */
public class DriverDTO {
    private Long id;
    private String name;
    private List<TruckDTO> trucks;

    public DriverDTO() {
    }

    public DriverDTO(String name, List<TruckDTO> trucks) {
        this.name = name;
        this.trucks = trucks;
    }
    
    public DriverDTO(Driver driver) {
        this.id = driver.getId();
        this.name = driver.getName();
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

    public List<TruckDTO> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<TruckDTO> trucks) {
        this.trucks = trucks;
    }
    
}
