package com.github.vmoshnogorskiy.votes.web.menu;

import com.github.vmoshnogorskiy.votes.model.MenuItem;
import com.github.vmoshnogorskiy.votes.repository.MenuItemRepository;
import com.github.vmoshnogorskiy.votes.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = MenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class MenuItemController {

    static final String REST_URL = "/api/menuitems";

    private final MenuItemRepository repository;

    @GetMapping
    public List<MenuItem> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll menu items by user {}", authUser.id());
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get menu item {} for user {}", id, authUser.id());
        return ResponseEntity.of(repository.findById(id));
    }
}
