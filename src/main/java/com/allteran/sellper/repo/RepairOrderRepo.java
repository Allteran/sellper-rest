package com.allteran.sellper.repo;

import com.allteran.sellper.domain.RepairOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairOrderRepo extends JpaRepository<RepairOrder, Long> {

}
