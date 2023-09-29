package com.github.vmoshnogorskiy.votes.web.vote;

import com.github.vmoshnogorskiy.votes.error.DataConflictException;
import com.github.vmoshnogorskiy.votes.model.User;
import com.github.vmoshnogorskiy.votes.model.Vote;
import com.github.vmoshnogorskiy.votes.repository.RestaurantRepository;
import com.github.vmoshnogorskiy.votes.repository.VoteRepository;
import com.github.vmoshnogorskiy.votes.util.VotesUtil;
import com.github.vmoshnogorskiy.votes.util.validation.ValidationUtil;
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
import com.github.vmoshnogorskiy.votes.to.VoteTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {

    static final String REST_URL = "/api/votes";

    private static int hourAfterNotChangeVote = 11;

    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll votes for user {}", authUser.id());
        List<Vote> votes = voteRepository.getAll(authUser.id());
        return votes.stream()
                .map(VotesUtil::createTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteTo> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get vote {} for user {}", id, authUser.id());
        return ResponseEntity.of(VotesUtil.createToOptional(voteRepository.getExisted(id)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        int userId = authUser.id();
        log.info("delete vote {} for user {}", id, userId);
        validateUpdateConstraint(userId, id);
        voteRepository.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> add(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody VoteTo voteTo) {
        log.info("create new vote for user {}", authUser.id());
        ValidationUtil.checkNew(voteTo);
        validateAddConstraint(authUser.getUser());
        Vote vote = new Vote();
        VotesUtil.updateVote(vote, restaurantRepository.getExisted(voteTo.getRestaurantId()), authUser.getUser());

        VoteTo created = VotesUtil.createTo(voteRepository.save(vote));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody VoteTo voteTo, @PathVariable int id) {
        int userId = authUser.id();
        log.info("update vote {} for user {}", id, userId);
        ValidationUtil.assureIdConsistent(voteTo, id);
        Vote vote = voteRepository.getExisted(id);
        validateUpdateConstraint(userId, id);
        VotesUtil.updateVote(vote, restaurantRepository.getExisted(voteTo.getRestaurantId()), authUser.getUser());
        voteRepository.save(vote);
    }

    protected static void setHourAfterNotChangeVote(int hour) {
        VoteController.hourAfterNotChangeVote = hour;
    }

    private void validateAddConstraint(User user) {
        Vote vote = voteRepository.findByUserToday(user.id());
        if (vote != null) {
            throw new DataConflictException("The user with login " + user.getEmail() + " has already voted today");
        }
    }

    private void validateUpdateConstraint(int userId, int id) {
        Vote vote = voteRepository.getExistedOrBelonged(userId, id);
        int hour = LocalDateTime.now().getHour();
        if (LocalDate.now().isAfter(vote.getActualDate()) || hour >= hourAfterNotChangeVote) {
            throw new DataConflictException("the vote cannot be changed today after " + hourAfterNotChangeVote + ":00 am or later");
        }
    }
}
