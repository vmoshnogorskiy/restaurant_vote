package com.github.vmoshnogorskiy.votes.util;

import com.github.vmoshnogorskiy.votes.model.Restaurant;
import com.github.vmoshnogorskiy.votes.model.User;
import com.github.vmoshnogorskiy.votes.model.Vote;
import lombok.experimental.UtilityClass;
import com.github.vmoshnogorskiy.votes.to.VoteTo;

import java.time.LocalDate;
import java.util.Optional;

@UtilityClass
public class VotesUtil {

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getActualDate(), vote.getRestaurant().getId(), vote.getUser().getId(), vote.getRestaurant().getName());
    }

    public static Optional<VoteTo> createToOptional(Vote vote) {
        return Optional.of(createTo(vote));
    }

    public static void update(Vote vote, Restaurant restaurant, User user) {
        vote.setRestaurant(restaurant);
        vote.setUser(user);
        vote.setActualDate(LocalDate.now());
    }

    public static Vote create(Restaurant restaurant, User user) {
        Vote vote = new Vote();
        update(vote, restaurant, user);
        return vote;
    }
}
