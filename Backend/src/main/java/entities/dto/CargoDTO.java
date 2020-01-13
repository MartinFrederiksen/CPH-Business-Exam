package entities.dto;

import entities.Cargo;

/**
 *
 * @author Martin Frederiksen
 */
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
    
}
