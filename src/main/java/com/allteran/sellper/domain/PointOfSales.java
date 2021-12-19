package com.allteran.sellper.domain;

import javax.persistence.*;

@Entity
@Table
public class PointOfSales {
    @Id
    private Long id;
    private String city;
    private String street;
    private String building;

    @OneToOne
    @JoinColumn(name = "type_id")
    private POSType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setType(POSType type) {
        this.type = type;
    }

    public POSType getType() {
        return type;
    }
}
