package ru.javaops.votes.web.vote;

import ru.javaops.votes.model.Vote;
import ru.javaops.votes.to.VoteTo;
import ru.javaops.votes.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static ru.javaops.votes.web.restaurant.RestaurantTestData.*;
import static ru.javaops.votes.web.user.UserTestData.*;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");

    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class, "restaurantName");

    public static final int VOTE1_ID = 1;

    public static final Vote vote1 = new Vote(VOTE1_ID, LocalDate.now(), restaurant2, user);

    public static final Vote vote2 = new Vote(VOTE1_ID + 1, LocalDate.now(), restaurant3, admin);

    public static final List<Vote> votes = List.of(vote1, vote2);

    public static VoteTo getUpdatedVoteTo() {
        return new VoteTo(VOTE1_ID, LocalDate.now(), RESTAURANT1_ID, USER_ID, "Река");
    }

    public static VoteTo getNewVoteTo() {
        return new VoteTo(null, LocalDate.now(), RESTAURANT1_ID, GUEST_ID, "Река");
    }
}
