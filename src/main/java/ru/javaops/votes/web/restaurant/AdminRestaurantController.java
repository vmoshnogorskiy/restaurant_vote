package ru.javaops.votes.web.restaurant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.votes.model.MenuItem;
import ru.javaops.votes.model.Restaurant;
import ru.javaops.votes.repository.MenuItemRepository;
import ru.javaops.votes.repository.RestaurantRepository;
import ru.javaops.votes.web.AuthUser;

import java.net.URI;
import java.time.LocalDateTime;

import static ru.javaops.votes.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.votes.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminRestaurantController {
    static final String REST_URL = "/api/admin/restaurants";

    private final RestaurantRepository repository;

    private final MenuItemRepository menuItems;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("delete restaurant {} for user {}", id, authUser.id());
        repository.deleteExisted(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        int userId = authUser.id();
        log.info("update {} for user {}", restaurant, userId);
        assureIdConsistent(restaurant, id);
        repository.getExisted(id);
        repository.save(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Restaurant restaurant) {
        int userId = authUser.id();
        log.info("create {} for user {}", restaurant, userId);
        checkNew(restaurant);
        restaurant.setCreated(LocalDateTime.now());
        Restaurant created = repository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}/menuitems/{menu_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuItem(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id, @PathVariable("menu_id") int menuId) {
        log.info("delete menu item {} for restaurant {} and user {}", menuId, id, authUser.id());
        menuItems.deleteExisted(menuId);
    }

    @PostMapping(path = "/{id}/menuitems", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> createMenuItem(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id, @Valid @RequestBody MenuItem item) {
        int userId = authUser.id();
        log.info("create menu item {} by restaurant {} for user {}", item, id, userId);
        checkNew(item);
        item.setUpdated(LocalDateTime.now());
        item.setRestaurant(repository.getExisted(id));
        MenuItem created = menuItems.save(item);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}/menuitems/{id}")
                .buildAndExpand(id, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}/menuitems/{menu_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMenuItem(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody MenuItem item,
                               @PathVariable int id, @PathVariable("menu_id") int menuId) {
        int userId = authUser.id();
        log.info("update menu item {} by restaurant {} for user {}", menuId, id, userId);
        assureIdConsistent(item, menuId);
        item.setUpdated(LocalDateTime.now());
        item.setRestaurant(repository.getExisted(id));
        menuItems.save(item);
    }
}
