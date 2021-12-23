package com.allteran.sellper.service;

import com.allteran.sellper.domain.RepairDeviceType;
import com.allteran.sellper.repo.RepairDeviceTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairDeviceTypeService {
    private final RepairDeviceTypeRepo typeRepo;

    @Autowired
    public RepairDeviceTypeService(RepairDeviceTypeRepo deviceTypeRepo) {
        this.typeRepo = deviceTypeRepo;
    }

    public List<RepairDeviceType> getAll() {
        return typeRepo.findAll();
    }

    public RepairDeviceType create(RepairDeviceType deviceType) {
        return typeRepo.save(deviceType);
    }

    public RepairDeviceType update(RepairDeviceType typeFromDb, RepairDeviceType type) {
        typeFromDb.setName(type.getName());
        return typeFromDb;
    }

    public void delete(RepairDeviceType type) {
        typeRepo.delete(type);
    }
}
