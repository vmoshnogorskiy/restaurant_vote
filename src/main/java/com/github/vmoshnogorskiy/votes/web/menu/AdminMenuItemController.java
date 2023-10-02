package com.github.vmoshnogorskiy.votes.web.menu;

import com.github.vmoshnogorskiy.votes.model.MenuItem;
import com.github.vmoshnogorskiy.votes.repository.MenuItemRepository;
import com.github.vmoshnogorskiy.votes.web.AuthUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("delete menu item {} by user {}", id, authUser.id());
        repository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> add(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody MenuItem item) {
        int userId = authUser.id();
        log.info("create new menu item by user {}", userId);
        checkNew(item);
//        item.setActualDate(LocalDate.now());
//        item.setRestaurant(restaurantRepository.getExisted(id));
        MenuItem created = repository.save(item);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody MenuItem item,
                       @PathVariable int id) {
        int userId = authUser.id();
        log.info("update menu item {} by user {}", id, userId);
        assureIdConsistent(item, id);
//        item.setActualDate(LocalDate.now());
//        item.setRestaurant(restaurantRepository.getExisted(id));
        repository.save(item);
    }
}
