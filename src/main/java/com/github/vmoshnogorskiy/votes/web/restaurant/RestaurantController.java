package com.github.vmoshnogorskiy.votes.web.restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.github.vmoshnogorskiy.votes.model.Restaurant;
import com.github.vmoshnogorskiy.votes.repository.RestaurantRepository;
import com.github.vmoshnogorskiy.votes.repository.VoteRepository;
import com.github.vmoshnogorskiy.votes.to.RestaurantTo;
import com.github.vmoshnogorskiy.votes.web.AuthUser;


import java.util.List;
import java.util.stream.Collectors;

import static com.github.vmoshnogorskiy.votes.util.RestaurantsUtil.createTo;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";

    private final RestaurantRepository restaurantRepository;

    private final VoteRepository voteRepository;

    @GetMapping
    @Cacheable(cacheManager = "allRestaurantsCacheManager", cacheNames = "all_restaurants")
    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(cacheManager = "restaurantCacheManager", cacheNames = "restaurant", key = "#id")
    public ResponseEntity<Restaurant> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get restaurant {} for user {}", id, authUser.id());
        return ResponseEntity.of(restaurantRepository.findById(id));
    }

    @GetMapping("/with-count-votes")
    public List<RestaurantTo> getWithCountVotes(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get restaurants with countVotes for user {}", authUser.id());
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map(restaurant -> createTo(restaurant, voteRepository.getVotesCount(restaurant.getId())))
                .collect(Collectors.toList());
    }
}
