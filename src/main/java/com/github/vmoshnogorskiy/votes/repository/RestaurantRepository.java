package com.github.vmoshnogorskiy.votes.repository;

import org.springframework.transaction.annotation.Transactional;
import com.github.vmoshnogorskiy.votes.model.Restaurant;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

}