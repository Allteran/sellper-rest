package com.allteran.sellper.service;

import com.allteran.sellper.domain.PointOfSales;
import com.allteran.sellper.repo.POSRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class POSService {
    private final POSRepo posRepo;

    @Autowired
    public POSService(POSRepo posRepo) {
        this.posRepo = posRepo;
    }

    public List<PointOfSales> getAll() {
        return posRepo.findAll();
    }

}
