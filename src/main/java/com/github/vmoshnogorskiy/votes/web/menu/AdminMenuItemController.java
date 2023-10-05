package com.github.vmoshnogorskiy.votes.web.menu;

import com.github.vmoshnogorskiy.votes.model.MenuItem;
import com.github.vmoshnogorskiy.votes.repository.MenuItemRepository;
import com.github.vmoshnogorskiy.votes.repository.RestaurantRepository;
import com.github.vmoshnogorskiy.votes.to.MenuItemTo;
import com.github.vmoshnogorskiy.votes.util.MenuItemUtil;
import com.github.vmoshnogorskiy.votes.web.AuthUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.github.vmoshnogorskiy.votes.util.validation.ValidationUtil.assureIdConsistent;
import static com.github.vmoshnogorskiy.votes.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminMenuItemController {

    static final String REST_URL = "/api/admin/menuitems";

    private final MenuItemRepository repository;

    private final RestaurantRepository restaurantRepository;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("delete menu item {} by user {}", id, authUser.id());
        repository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<MenuItemTo> add(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody MenuItemTo itemTo) {
        int userId = authUser.id();
        log.info("create new menu item by user {}", userId);
        checkNew(itemTo);
        MenuItem item = MenuItemUtil.createFromTo(itemTo, restaurantRepository.getExisted(itemTo.getRestaurantId()));
        MenuItemTo created = MenuItemUtil.createTo(repository.save(item));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody MenuItemTo itemTo,
                       @PathVariable int id) {
        int userId = authUser.id();
        log.info("update menu item {} by user {}", id, userId);
        assureIdConsistent(itemTo, id);
        MenuItem item = MenuItemUtil.createFromTo(itemTo, restaurantRepository.getExisted(itemTo.getRestaurantId()));
        repository.save(item);
    }
}
