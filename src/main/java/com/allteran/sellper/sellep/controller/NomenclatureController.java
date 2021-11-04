package com.allteran.sellper.sellep.controller;

import com.allteran.sellper.sellep.domain.Nomenclature;
import com.allteran.sellper.sellep.repo.NomenclatureRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("nomenclature")
public class NomenclatureController {
    private final NomenclatureRepo nomenclatureRepo;

    @Autowired
    public NomenclatureController(NomenclatureRepo nomenclatureRepo) {
        this.nomenclatureRepo = nomenclatureRepo;
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
}
