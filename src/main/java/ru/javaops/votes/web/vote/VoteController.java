package ru.javaops.votes.web.vote;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.votes.error.DataConflictException;
import ru.javaops.votes.model.User;
import ru.javaops.votes.model.Vote;
import ru.javaops.votes.repository.RestaurantRepository;
import ru.javaops.votes.repository.VoteRepository;
import ru.javaops.votes.to.VoteTo;
import ru.javaops.votes.util.VotesUtil;
import ru.javaops.votes.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javaops.votes.util.VotesUtil.createTo;
import static ru.javaops.votes.util.VotesUtil.createToOptional;
import static ru.javaops.votes.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.votes.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {

    static final String REST_URL = "/api/votes";

    static int hourAfterNotChangeVote = 11;

    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    @GetMapping
    public List<VoteTo> getAllVotes(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll votes for user {}", authUser.id());
        List<Vote> votes = voteRepository.getAllVotes();
        return votes.stream()
                .map(VotesUtil::createTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteTo> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get vote {} for user {}", id, authUser.id());
        return ResponseEntity.of(createToOptional(voteRepository.getExisted(id)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        int userId = authUser.id();
        log.info("delete vote {} for user {}", id, userId);
        validateUpdateConstraint(userId, id);
        voteRepository.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> addVote(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody VoteTo voteTo) {
        log.info("create new vote for user {}", authUser.id());
        checkNew(voteTo);
        validateAddConstraint(authUser.getUser());
        Vote vote = new Vote();
        VotesUtil.updateVote(vote, restaurantRepository.getExisted(voteTo.getRestaurantId()), authUser.getUser());

        VoteTo created = createTo(voteRepository.save(vote));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVote(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody VoteTo voteTo, @PathVariable int id) {
        int userId = authUser.id();
        log.info("update vote {} for user {}", id, userId);
        assureIdConsistent(voteTo, id);
        Vote vote = voteRepository.getExisted(id);
        validateUpdateConstraint(userId, id);
        VotesUtil.updateVote(vote, restaurantRepository.getExisted(voteTo.getRestaurantId()), authUser.getUser());
        voteRepository.save(vote);
    }

    private void validateAddConstraint(User user) {
        Vote vote = voteRepository.findVoteByUserToday(user.id());
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
