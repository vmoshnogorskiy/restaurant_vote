package ru.javaops.votes.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.votes.model.MenuItem;

import java.util.List;

@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {

    @Query("SELECT mi FROM MenuItem mi WHERE mi.restaurant.id=?1 ORDER BY mi.updated DESC")
    List<MenuItem> getAllMenuItems(int id);
}
