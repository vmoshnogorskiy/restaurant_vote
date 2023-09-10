package ru.javaops.votes.util;

import lombok.experimental.UtilityClass;
import ru.javaops.votes.model.Restaurant;
import ru.javaops.votes.model.User;
import ru.javaops.votes.model.Vote;
import ru.javaops.votes.to.VoteTo;

import java.time.LocalDate;
import java.util.Optional;

@UtilityClass
public class VotesUtil {

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getDate(), vote.getRestaurant().getId(), vote.getUser().getId(), vote.getRestaurant().getName());
    }

    public static Optional<VoteTo> createToOptional(Vote vote) {
        return Optional.of(new VoteTo(vote.getId(), vote.getDate(), vote.getRestaurant().getId(), vote.getUser().getId(), vote.getRestaurant().getName()));
    }

    public static void updateVote(Vote vote, Restaurant restaurant, User user) {
        vote.setRestaurant(restaurant);
        vote.setUser(user);
        vote.setDate(LocalDate.now());
    }
}
