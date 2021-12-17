package com.allteran.sellper.repo;

import com.allteran.sellper.domain.POSType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface POSTypeRepo extends JpaRepository<POSType, Long> {
}
