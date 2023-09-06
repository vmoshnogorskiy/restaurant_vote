package ru.javaops.votes.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.votes.model.Restaurant;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menu m WHERE cast(m.updated as Date) = current_date ORDER BY r.created DESC")
    List<Restaurant> getAllWithMenuItems();

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.votes v WHERE v.date = current_date ORDER BY r.created DESC")
    List<Restaurant> getAllWithVotes();
}
