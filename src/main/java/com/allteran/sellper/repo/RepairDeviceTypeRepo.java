package com.allteran.sellper.repo;

import com.allteran.sellper.domain.RepairDeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairDeviceTypeRepo extends JpaRepository<RepairDeviceType, Long> {

}
