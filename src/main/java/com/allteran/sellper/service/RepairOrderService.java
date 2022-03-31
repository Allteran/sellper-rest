package com.allteran.sellper.service;

import com.allteran.sellper.domain.RepairOrder;
import com.allteran.sellper.domain.RepairStatus;
import com.allteran.sellper.repo.RepairOrderRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RepairOrderService {
    @Value("${repair.status.created.id}")
    private String STATUS_CREATED_ID;
    @Value("${repair.status.paid.id}")
    private String STATUS_PAID_ID;
    @Value("${repair.status.unrepaired.id}")
    private String STATUS_UNREPAIRED_ID;

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

    /**
     * Next method will search for all orders by special params:
     * - all unpaid orders
     * - all done and paid orders in range of current month
     */
    public List<RepairOrder> getAllForRegistry() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.withDayOfMonth(1);
        LocalDateTime end = now.withDayOfMonth(now.getMonth().maxLength());

        List<RepairStatus> inProgress = statusService.getAll();

        RepairStatus paid = statusService.findById(Long.valueOf(STATUS_PAID_ID));
        RepairStatus unrepaired = statusService.findById(Long.valueOf(STATUS_UNREPAIRED_ID));

        List<RepairStatus> done = new ArrayList<>();
        done.add(paid);
        done.add(unrepaired);

        inProgress.removeAll(done);

        //first - lets find all orders that in progress
        List<RepairOrder> searchResult = orderRepo.findAllByStatus(inProgress);
        //then we'll find orders that done (paid or unrepaired) and was given in current month
        List<RepairOrder> doneOrders = orderRepo.findAllByIssueDateAndStatus(start, end, done);

        //merge two collections in one - searchResult
        searchResult.addAll(doneOrders);


        return searchResult;
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

        //for some hell reason frontend gives me not that default time as I expect
        //so we need to check if issueDate is before some far far date, e.g. 01.01.2010 0:00. If it is - that means that we have default issue date
        LocalDateTime dateCorrections = LocalDateTime.of(2010,1,1,0,0);

        boolean isBefore = orderFromDb.getIssueDate().isBefore(dateCorrections);
        if(isBefore) {
            orderFromDb.setIssueDate(DEFAULT_DATE);
        }

        RepairStatus statusPaid = statusService.findById(Long.valueOf(STATUS_PAID_ID));

        //if issue date doesn't match to default date that gives to order when it has been created, that means
        //that issueDate has been changed and status should be changed too
        if(!orderFromDb.getIssueDate().equals(DEFAULT_DATE)) {
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

    public List<RepairOrder> findAllByCreationDateAndStatus(LocalDateTime from, LocalDateTime to, List<RepairStatus> statuses) {
        List<RepairOrder> repairList;
        repairList = orderRepo.findAllByCreationDateAndStatus(from, to, statuses);

        //We will add last row to list that will display sum of all orders for prices and profit
//        int service = 0, component = 0, margin = 0, total = 0;
//        double director = 0, repman = 0, manager = 0;
//
//        for (RepairOrder order : repairList) {
//            service += order.getServicePrice();
//            component += order.getComponentPrice();
//            margin += order.getMarginPrice();
//            total += order.getTotalPrice();
//
//            director += order.getDirectorProfit();
//            repman += order.getRepManProfit();
//            manager += order.getManagerProfit();
//        }
//        RepairOrder lastRow = new RepairOrder();
//
        return repairList;
    }
}
