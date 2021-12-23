package com.allteran.sellper.controller;

import com.allteran.sellper.domain.RepairDeviceType;
import com.allteran.sellper.service.RepairDeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/repair/device-type")
public class RepairDeviceTypeController {
    private final RepairDeviceTypeService deviceTypeService;

    @Autowired
    public RepairDeviceTypeController(RepairDeviceTypeService deviceTypeService) {
        this.deviceTypeService = deviceTypeService;
    }

    @GetMapping
    public List<RepairDeviceType> getAll() {
        return deviceTypeService.getAll();
    }

    @GetMapping("{id}")
    public RepairDeviceType getOne(@PathVariable("id") RepairDeviceType deviceType) {
        return deviceType;
    }

    @PostMapping
    public RepairDeviceType create(@RequestBody RepairDeviceType deviceType) {
        return deviceTypeService.create(deviceType);
    }

    @PutMapping("{id}")
    public RepairDeviceType update(@PathVariable("id") RepairDeviceType typeFromDb,
                                   @RequestBody RepairDeviceType type) {
        return deviceTypeService.update(typeFromDb, type);
    }

    @DeleteMapping("{id}")
    public void deleteOne(@PathVariable("id") RepairDeviceType type) {
        deviceTypeService.delete(type);
    }
}
