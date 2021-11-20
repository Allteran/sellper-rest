package com.allteran.sellper.controller;

import com.allteran.sellper.domain.Nomenclature;
import com.allteran.sellper.domain.NomenclatureGroup;
import com.allteran.sellper.domain.Views;
import com.allteran.sellper.repo.NomenclatureGroupRepo;
import com.allteran.sellper.repo.NomenclatureRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("nomenclature")
public class NomenclatureController {
    private final NomenclatureRepo nomenclatureRepo;
    private final NomenclatureGroupRepo groupRepo;
//    private final BiConsumer<EventType, Nomenclature> wsSender;

    @Autowired
    public NomenclatureController(NomenclatureRepo nomenclatureRepo, NomenclatureGroupRepo groupRepo) {
        this.nomenclatureRepo = nomenclatureRepo;
        this.groupRepo = groupRepo;
//        this.wsSender = wsSender.getSender(ObjectType.NOMENCLATURE, Views.FullNomenclature.class);
    }

    @GetMapping
    public List<Nomenclature> getList() {
        return nomenclatureRepo.findAll();
    }

    @GetMapping("{id}")
    public Nomenclature getOne(@PathVariable("id") Nomenclature nomenclature) {
        return nomenclature;
    }

    @PostMapping
    public Nomenclature create(@RequestBody Nomenclature nomenclature) {
        //now I test creating entity with WebSocket, so I'll fill NomenclatureGroup parameter with some static data
        NomenclatureGroup group = new NomenclatureGroup();
        group.setId((long) 15);
        group.setName("Аксессуары");
        nomenclature.setGroup(group);

        //        wsSender.accept(EventType.CREATE, updatedNom);

        return nomenclatureRepo.save(nomenclature);
    }

    @PutMapping("{id}")
    public Nomenclature updateOne(
            @PathVariable("id") Nomenclature nomenclatureFromDb,
            @RequestBody Nomenclature nomenclature) {
        BeanUtils.copyProperties(nomenclature, nomenclatureFromDb, "id");

        //        wsSender.accept(EventType.UPDATE, updatedNom);

        return nomenclatureRepo.save(nomenclatureFromDb);
    }

    @DeleteMapping("{id}")
    public void deleteOne(@PathVariable("id") Nomenclature nomenclature) {
        nomenclatureRepo.delete(nomenclature);
//        wsSender.accept(EventType.REMOVE, nomenclature);
    }


}
