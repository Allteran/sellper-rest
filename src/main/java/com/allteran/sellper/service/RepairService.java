package com.allteran.sellper.service;

import com.allteran.sellper.domain.RepairOrder;
import com.allteran.sellper.repo.RepairOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairService {
    private final RepairOrderRepo orderRepo;

    @Autowired
    public RepairService(RepairOrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public List<RepairOrder> getAllOrders() {
        return orderRepo.findAll();
    }
}
