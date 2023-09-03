package ru.javaops.votes.web.restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.votes.model.MenuItem;
import ru.javaops.votes.model.Restaurant;
import ru.javaops.votes.repository.MenuItemRepository;
import ru.javaops.votes.repository.RestaurantRepository;
import ru.javaops.votes.repository.VoteRepository;
import ru.javaops.votes.web.AuthUser;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";

    private final RestaurantRepository restaurant;

    private final MenuItemRepository menuItem;

    private final VoteRepository vote;

    @GetMapping
    public List<Restaurant> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll restaurants for user {}", authUser.id());
        return restaurant.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get restaurant {} for user {}", id, authUser.id());
        return ResponseEntity.of(restaurant.findById(id));
    }

    @GetMapping("/with-menuitems")
    public List<Restaurant> getAllWithMenuItems(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all restaurants with MenuItems for user {}", authUser.id());
        //return null;
        return restaurant.getAllWithMenuItems();
    }

    @GetMapping("/{id}/menuitems")
    public List<MenuItem> getAllMenuItems(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("getAll menu items for restaurant {} and user {}", id, authUser.id());
        return menuItem.getAllMenuItems(id);
    }
}
