package com.allteran.sellper.domain;

import javax.persistence.*;

@Table
@Entity
public class POSType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;


    public POSType() {
    }

    public POSType(String id, String name) {
        this.id = Long.parseLong(id);
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
