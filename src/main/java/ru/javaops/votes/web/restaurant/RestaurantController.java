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
import ru.javaops.votes.model.Vote;
import ru.javaops.votes.repository.MenuItemRepository;
import ru.javaops.votes.repository.RestaurantRepository;
import ru.javaops.votes.repository.VoteRepository;
import ru.javaops.votes.to.RestaurantTo;
import ru.javaops.votes.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javaops.votes.util.RestaurantsUtil.createTo;
import static ru.javaops.votes.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.votes.util.validation.ValidationUtil.checkNew;

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

    @GetMapping("/{id}/votes")
    public List<Vote> getAllVotes(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("getAll votes for restaurant {} and user {}", id, authUser.id());
        return voteRepository.getAllVotes(id);
    }

    @GetMapping("/with-votes")
    public List<Restaurant> getAllWithVotes(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all restaurants with votes for user {}", authUser.id());
        return restaurantRepository.getAllWithVotes();
    }

    @DeleteMapping("/{id}/menuitems/{vote_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id, @PathVariable("vote_id") int voteId) {
        log.info("delete vote {} for restaurant {} and user {}", voteId, id, authUser.id());
        voteRepository.deleteExisted(voteId);
    }

    @PostMapping(path = "/{id}/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> addVote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id, @Valid @RequestBody Vote vote) {
        int userId = authUser.id();
        log.info("create new vote by restaurant {} for user {}", id, userId);
        checkNew(vote);
        vote.setRestaurant(restaurantRepository.getExisted(id));
        vote.setUser(authUser.getUser());
        vote.setDate(LocalDate.now());
        Vote created = voteRepository.save(vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}/votes/{id}")
                .buildAndExpand(id, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}/votes/{vote_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVote(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Vote vote,
                           @PathVariable int id, @PathVariable("vote_id") int voteId) {
        int userId = authUser.id();
        log.info("update vote {} by restaurant {} for user {}", voteId, id, userId);
        assureIdConsistent(vote, voteId);
        vote.setRestaurant(restaurantRepository.getExisted(id));
        voteRepository.save(vote);
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
