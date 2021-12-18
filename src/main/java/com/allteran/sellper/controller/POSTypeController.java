package com.allteran.sellper.controller;

import com.allteran.sellper.domain.POSType;
import com.allteran.sellper.service.POSTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pos-type")
public class POSTypeController {

    private final POSTypeService typeService;


    public POSTypeController(POSTypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping()
    public List<POSType> getAllTypes() {
        return typeService.getAllTypes();
    }

    @GetMapping("{id}")
    public POSType getOneType(@PathVariable("id") POSType type) {
        return type;
    }

    @PostMapping
    public POSType create(@RequestBody POSType type) {
        return typeService.create(type);
    }

    @PutMapping("{id}")
    public POSType updateOne(
            @PathVariable("id") POSType typeFromDb,
            @RequestBody POSType type) {
        return typeService.update(typeFromDb, type);
    }

    @DeleteMapping("{id}")
    public void deleteOne(@PathVariable("id") POSType type) {
        typeService.delete(type);
    }


}
