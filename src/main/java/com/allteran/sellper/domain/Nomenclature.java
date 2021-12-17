package com.allteran.sellper.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@ToString(of = {"id", "name", "group", "price"})
@EqualsAndHashCode(of = {"id"})
public class Nomenclature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdNamePrice.class)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    @JsonView(Views.FullNomenclature.class)
    private NomenclatureGroup group;

    @JsonView(Views.IdNamePrice.class)
    private Long price;

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

    public NomenclatureGroup getGroup() {
        return group;
    }

    public void setGroup(NomenclatureGroup group) {
        this.group = group;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
