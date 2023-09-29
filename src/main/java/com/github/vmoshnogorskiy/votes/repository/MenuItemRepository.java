package com.github.vmoshnogorskiy.votes.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.github.vmoshnogorskiy.votes.model.MenuItem;

import java.util.List;

@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {

    @Query("SELECT mi FROM MenuItem mi WHERE mi.restaurant.id=?1 AND mi.actualDate = current_date")
    List<MenuItem> getAllMenuItems(int id);
}