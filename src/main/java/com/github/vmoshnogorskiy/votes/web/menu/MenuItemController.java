package com.github.vmoshnogorskiy.votes.web.menu;

import com.github.vmoshnogorskiy.votes.model.MenuItem;
import com.github.vmoshnogorskiy.votes.repository.MenuItemRepository;
import com.github.vmoshnogorskiy.votes.to.MenuItemTo;
import com.github.vmoshnogorskiy.votes.util.MenuItemUtil;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = MenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class MenuItemController {

    static final String REST_URL = "/api/menuitems";

    private final MenuItemRepository repository;

    @GetMapping
    public List<MenuItemTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll menu items by user {}", authUser.id());
        List<MenuItem> items = repository.findAll();
        return items.stream()
                .map(MenuItemUtil::createTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemTo> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get menu item {} for user {}", id, authUser.id());
        return ResponseEntity.of(MenuItemUtil.createToOptional(repository.getExisted(id)));
    }
}
