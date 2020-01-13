package entities.dto;

import entities.Driver;
import java.util.List;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.trucks);
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
        final DriverDTO other = (DriverDTO) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.trucks, other.trucks)) {
            return false;
        }
        return true;
    }
    
}
