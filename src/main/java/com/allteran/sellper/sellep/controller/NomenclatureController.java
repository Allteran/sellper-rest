package com.allteran.sellper.sellep.controller;

import com.allteran.sellper.sellep.domain.Nomenclature;
import com.allteran.sellper.sellep.domain.NomenclatureGroup;
import com.allteran.sellper.sellep.repo.NomenclatureGroupRepo;
import com.allteran.sellper.sellep.repo.NomenclatureRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("nomenclature")
public class NomenclatureController {
    private final NomenclatureRepo nomenclatureRepo;
    private final NomenclatureGroupRepo groupRepo;

    @Autowired
    public NomenclatureController(NomenclatureRepo nomenclatureRepo, NomenclatureGroupRepo groupRepo) {
        this.nomenclatureRepo = nomenclatureRepo;
        this.groupRepo = groupRepo;
    }

    @GetMapping
    public List<Nomenclature> getList() {
        List<Nomenclature> list = nomenclatureRepo.findAll();
        return list;
    }

    @GetMapping("{id}")
    public Nomenclature getOne(@PathVariable("id") Nomenclature nomenclature) {
        return nomenclature;
    }

    @PostMapping
    public Nomenclature createOne(@RequestBody Nomenclature nomenclature) {
        return nomenclatureRepo.save(nomenclature);
    }

    @PutMapping("{id}")
    public Nomenclature updateOne(
            @PathVariable("id") Nomenclature nomenclatureFromDb,
            @RequestBody Nomenclature nomenclature) {
        BeanUtils.copyProperties(nomenclature, nomenclatureFromDb, "id");
        return nomenclatureRepo.save(nomenclature);
    }

    @DeleteMapping("{id}")
    public void deleteOne(@PathVariable("id") Nomenclature nomenclature) {
        nomenclatureRepo.delete(nomenclature);
    }

    @MessageMapping("/changeNomenclature")
    @SendTo("/topic/activity")
    public Nomenclature change(Nomenclature nomenclature) {
        return nomenclatureRepo.save(nomenclature);
    }
}
