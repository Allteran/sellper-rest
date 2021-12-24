package com.allteran.sellper.service;

import com.allteran.sellper.domain.RepairOrder;
import com.allteran.sellper.domain.RepairStatus;
import com.allteran.sellper.repo.RepairOrderRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanProperty;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RepairOrderService {
    private static final Long STATUS_CREATED_ID = 12L; //ID for status 'принят'

    private final RepairOrderRepo orderRepo;
    private final RepairStatusService statusService;

    @Autowired
    public RepairOrderService(RepairOrderRepo orderRepo, RepairStatusService statusService) {
        this.orderRepo = orderRepo;
        this.statusService = statusService;
    }

    public List<RepairOrder> getAllOrders() {
        return orderRepo.findAll();
    }

    public RepairOrder createOrder(RepairOrder order) {
        order.setCreationDate(LocalDateTime.now());
        order.setIssueDate(LocalDateTime.MIN);

        RepairStatus statusCreated = statusService.findById(STATUS_CREATED_ID);
        order.setStatus(statusCreated);
        return orderRepo.save(order);
    }

    public RepairOrder updateOrder(RepairOrder orderFromDb, RepairOrder order) {
        BeanUtils.copyProperties(order, orderFromDb, "id");
        return orderRepo.save(orderFromDb);
    }

    public void deleteOrder(RepairOrder order) {
        orderRepo.delete(order);
    }
}
