package com.allteran.sellper.service;

import com.allteran.sellper.domain.Nomenclature;
import com.allteran.sellper.domain.POSType;
import com.allteran.sellper.repo.POSTypeRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class POSTypeService {
    private final POSTypeRepo typeRepo;

    public POSTypeService(POSTypeRepo typeRepo) {
        this.typeRepo = typeRepo;
    }

    public List<POSType> getAllTypes() {
       return typeRepo.findAll();
    }

    public POSType create(POSType type) {
        return typeRepo.save(type);
    }

    public POSType update(POSType typeFromDb, POSType type) {
        typeFromDb.setName(type.getName());
        return typeRepo.save(typeFromDb);
    }

    public void delete(POSType type) {
        typeRepo.delete(type);
    }
}