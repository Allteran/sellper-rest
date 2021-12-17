package com.allteran.sellper.repo;

import com.allteran.sellper.domain.PointOfSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface POSRepo extends JpaRepository<PointOfSales, Long> {
}
