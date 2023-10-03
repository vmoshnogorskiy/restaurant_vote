package com.github.vmoshnogorskiy.votes.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.github.vmoshnogorskiy.votes.model.MenuItem;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {

    @Query("SELECT mi FROM MenuItem mi WHERE mi.actualDate >= :startDate AND mi.actualDate <= :endDate")
    List<MenuItem> getBetween(LocalDate startDate, LocalDate endDate);
}