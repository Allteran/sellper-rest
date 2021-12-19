package com.allteran.sellper.controller;

import com.allteran.sellper.domain.PointOfSales;
import com.allteran.sellper.service.POSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pos")
public class POSController {
    private final POSService posService;

    @Autowired
    public POSController(POSService posService) {
        this.posService = posService;
    }

    @GetMapping
    public List<PointOfSales> getAll() {
        return posService.getAll();
    }

    @GetMapping("{id}")
    public PointOfSales getOne(@PathVariable("id") PointOfSales pos) {
        return pos;
    }

    @PostMapping
    public PointOfSales create(@RequestBody PointOfSales pos) {
        return posService.create(pos);
    }

    @PutMapping("{id}")
    public PointOfSales update(
            @PathVariable("id") PointOfSales posFromDb,
            @RequestBody PointOfSales pos) {
        return posService.update(posFromDb, pos);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") PointOfSales pos) {
        posService.remove(pos);
    }
}
