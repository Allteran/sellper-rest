package com.allteran.sellper.service;

import com.allteran.sellper.domain.RepairOrder;
import com.allteran.sellper.domain.RepairStatus;
import com.allteran.sellper.repo.RepairOrderRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RepairOrderService {
    @Value("${repair.status.created.id}")
    private String STATUS_CREATED_ID;
    @Value("${repair.stats.paid.id}")
    private String STATUS_PAID_ID;

    private static final LocalDateTime DEFAULT_DATE = LocalDateTime.of(2000,1,1,1,1);

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

    public RepairOrder findById(Long id) {
        return orderRepo.findById(id).get();
    }

    public RepairOrder createOrder(RepairOrder order) {
        order.setCreationDate(LocalDateTime.now());
        order.setIssueDate(LocalDateTime.of(2000,1,1,1,1));

        RepairStatus statusCreated = statusService.findById(Long.valueOf(STATUS_CREATED_ID));
        order.setStatus(statusCreated);
        return orderRepo.save(order);
    }

    public RepairOrder updateOrder(RepairOrder orderFromDb, RepairOrder order) {
        BeanUtils.copyProperties(order, orderFromDb, "id");

        RepairStatus statusPaid = statusService.findById(Long.valueOf(STATUS_PAID_ID));

        //if issue date doesn't match to default date that gives to order when it has been created, that means
        //that issueDate has been changed and status should be changed too
        if(orderFromDb.getIssueDate().equals(DEFAULT_DATE)) {
            orderFromDb.setStatus(statusPaid);
        }
        orderFromDb.setServicePrice(orderFromDb.getTotalPrice() - orderFromDb.getMarginPrice() - orderFromDb.getComponentPrice());

        orderFromDb.setDirectorProfit(orderFromDb.getServicePrice() * 0.45 + orderFromDb.getMarginPrice()*0.5);
        orderFromDb.setRepManProfit(orderFromDb.getDirectorProfit() + orderFromDb.getComponentPrice());
        order.setManagerProfit(orderFromDb.getServicePrice() * 0.1);

        return orderRepo.save(orderFromDb);
    }

    public void deleteOrder(RepairOrder order) {
        orderRepo.delete(order);
    }


}
