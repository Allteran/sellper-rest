package com.allteran.sellper.repo;

import com.allteran.sellper.domain.Nomenclature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NomenclatureRepo extends JpaRepository<Nomenclature, Long> {
}
