package entities.dto;

import entities.Cargo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;

/**
 *
 * @author Martin Frederiksen
 */
@Schema(name="Cargo")
public class CargoDTO {
    private Long id;
    private String name;
    private double weight;
    private int units;

    public CargoDTO() {
    }

    public CargoDTO(String name, double weight, int units) {
        this.name = name;
        this.weight = weight;
        this.units = units;
    }
    
    public CargoDTO(Cargo cargo) {
        this.id = cargo.getId();
        this.name = cargo.getName();
        this.weight = cargo.getWeight();
        this.units = cargo.getUnits();
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.weight) ^ (Double.doubleToLongBits(this.weight) >>> 32));
        hash = 79 * hash + this.units;
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
        final CargoDTO other = (CargoDTO) obj;
        if (Double.doubleToLongBits(this.weight) != Double.doubleToLongBits(other.weight)) {
            return false;
        }
        if (this.units != other.units) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
