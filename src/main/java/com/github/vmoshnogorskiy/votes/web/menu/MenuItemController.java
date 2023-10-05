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
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    @Transactional(readOnly = true)
    public List<MenuItemTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll menu items by user {}", authUser.id());
        List<MenuItem> items = repository.findAll();
        return items.stream()
                .map(MenuItemUtil::createTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<MenuItemTo> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get menu item {} for user {}", id, authUser.id());
        return ResponseEntity.of(MenuItemUtil.createToOptional(repository.getExisted(id)));
    }

    @GetMapping("/filter")
    @Transactional(readOnly = true)
    public List<MenuItemTo> getBetween(@RequestParam @Nullable LocalDate startDate, @RequestParam @Nullable LocalDate endDate) {
        List<MenuItem> items = repository.getBetween(startDate, endDate);
        return items.stream()
                .map(MenuItemUtil::createTo)
                .collect(Collectors.toList());
    }
}
