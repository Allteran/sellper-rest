package com.allteran.sellper.service;

import com.allteran.sellper.domain.RepairStatus;
import com.allteran.sellper.repo.RepairStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RepairStatusService {
    private final RepairStatusRepo statusRepo;

    @Autowired
    public RepairStatusService(RepairStatusRepo statusRepo) {
        this.statusRepo = statusRepo;
    }


    public List<RepairStatus> getAll() {
        return statusRepo.findAll();
    }

    public RepairStatus create(RepairStatus status) {
        return statusRepo.save(status);
    }

    public RepairStatus update(RepairStatus statusFromDb, RepairStatus status) {
        statusFromDb.setName(status.getName());
        return statusFromDb;
    }

    public void delete(RepairStatus status) {
        statusRepo.delete(status);
    }

    public RepairStatus findById(Long id) throws NoSuchElementException {
        Optional<RepairStatus> optionalStatus = statusRepo.findById(id);
        return optionalStatus.get();
    }
}
