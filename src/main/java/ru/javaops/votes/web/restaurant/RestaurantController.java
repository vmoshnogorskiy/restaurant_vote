package ru.javaops.votes.web.restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javaops.votes.model.MenuItem;
import ru.javaops.votes.model.Restaurant;
import ru.javaops.votes.repository.MenuItemRepository;
import ru.javaops.votes.repository.RestaurantRepository;
import ru.javaops.votes.repository.VoteRepository;
import ru.javaops.votes.to.RestaurantTo;
import ru.javaops.votes.web.AuthUser;


import java.util.List;
import java.util.stream.Collectors;

import static ru.javaops.votes.util.RestaurantsUtil.createTo;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";

    private final RestaurantRepository restaurantRepository;

    private final MenuItemRepository menuItemRepository;

    private final VoteRepository voteRepository;

    @GetMapping
    public List<Restaurant> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll restaurants for user {}", authUser.id());
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get restaurant {} for user {}", id, authUser.id());
        return ResponseEntity.of(restaurantRepository.findById(id));
    }

    @GetMapping("/with-menuitems")
    public List<Restaurant> getAllWithMenuItems(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all restaurants with MenuItems for user {}", authUser.id());
        return restaurantRepository.getAllWithMenuItems();
    }

    @GetMapping("/{id}/menuitems")
    public List<MenuItem> getAllMenuItems(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("getAll menu items for restaurant {} and user {}", id, authUser.id());
        return menuItemRepository.getAllMenuItems(id);
    }

    @GetMapping("/with-votes")
    public List<Restaurant> getAllWithVotes(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all restaurants with votes for user {}", authUser.id());
        return restaurantRepository.getAllWithVotes();
    }

    @GetMapping("/with-count-votes")
    public List<RestaurantTo> getRestaurantsWithCountVotes(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get restaurants with countVotes for user {}", authUser.id());
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map(restaurant -> createTo(restaurant, voteRepository.getVotesCount(restaurant.getId())))
                .collect(Collectors.toList());
    }
}
