package com.allteran.sellper.repo;

import com.allteran.sellper.domain.RepairOrder;
import com.allteran.sellper.domain.RepairStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RepairOrderRepo extends JpaRepository<RepairOrder, Long> {

    @Query("select o from RepairOrder o order by o.creationDate desc")
    List<RepairOrder> findAllSortedByCreationDate();

    /**
     * Current HQL query will search all orders in date range and will sort whole list by creationDate in desc
     */
    @Query("select o from RepairOrder o where o.creationDate between :start and :end order by o.creationDate desc")
    List<RepairOrder> findAllByCreationDateRange(@Param("start") LocalDateTime dateStart, @Param("end") LocalDateTime dateEnd);

    @Query("select o from RepairOrder o where o.creationDate between :from and :to and o.status in :statusList order by o.creationDate desc")
    List<RepairOrder> findAllByCreationDateAndStatus(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to, @Param("statusList")List<RepairStatus> statusList);
}
