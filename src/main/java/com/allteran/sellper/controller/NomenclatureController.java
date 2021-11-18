package com.allteran.sellper.controller;

import com.allteran.sellper.dto.EventType;
import com.allteran.sellper.dto.ObjectType;
import com.allteran.sellper.domain.Nomenclature;
import com.allteran.sellper.domain.NomenclatureGroup;
import com.allteran.sellper.domain.Views;
import com.allteran.sellper.repo.NomenclatureGroupRepo;
import com.allteran.sellper.repo.NomenclatureRepo;
import com.allteran.sellper.util.WsSender;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("nomenclature")
public class NomenclatureController {
    private final NomenclatureRepo nomenclatureRepo;
    private final NomenclatureGroupRepo groupRepo;
    private final BiConsumer<EventType, Nomenclature> wsSender;

    @Autowired
    public NomenclatureController(NomenclatureRepo nomenclatureRepo, NomenclatureGroupRepo groupRepo, WsSender wsSender) {
        this.nomenclatureRepo = nomenclatureRepo;
        this.groupRepo = groupRepo;
        this.wsSender = wsSender.getSender(ObjectType.NOMENCLATURE, Views.FullNomenclature.class);
    }

    @GetMapping
    @JsonView(Views.IdNamePrice.class)
    public List<Nomenclature> getList() {
        return nomenclatureRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView({Views.FullNomenclature.class})
    public Nomenclature getOne(@PathVariable("id") Nomenclature nomenclature) {
        return nomenclature;
    }

    @PostMapping
    @JsonView(Views.FullNomenclature.class)
    public Nomenclature create(@RequestBody Nomenclature nomenclature) {
        //now I test creating entity with WebSocket, so I'll fill NomenclatureGroup parameter with some static data
        NomenclatureGroup group = new NomenclatureGroup();
        group.setId((long) 15);
        group.setName("Аксессуары");
        nomenclature.setGroup(group);

        Nomenclature updatedNom = nomenclatureRepo.save(nomenclature);

        wsSender.accept(EventType.CREATE, updatedNom);

        return updatedNom;
    }

    @PutMapping("{id}")
    @JsonView(Views.FullNomenclature.class)
    public Nomenclature updateOne(
            @PathVariable("id") Nomenclature nomenclatureFromDb,
            @RequestBody Nomenclature nomenclature) {
        BeanUtils.copyProperties(nomenclature, nomenclatureFromDb, "id");

        Nomenclature updatedNom = nomenclatureRepo.save(nomenclatureFromDb);
        wsSender.accept(EventType.UPDATE, updatedNom);

        return updatedNom;
    }

    @DeleteMapping("{id}")
    @JsonView(Views.FullNomenclature.class)
    public void deleteOne(@PathVariable("id") Nomenclature nomenclature) {
        nomenclatureRepo.delete(nomenclature);
        wsSender.accept(EventType.REMOVE, nomenclature);
    }


}
