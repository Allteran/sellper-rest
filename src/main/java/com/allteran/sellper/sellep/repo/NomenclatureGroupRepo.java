package com.allteran.sellper.sellep.repo;

import com.allteran.sellper.sellep.domain.NomenclatureGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NomenclatureGroupRepo extends JpaRepository<NomenclatureGroup, Long> {
}