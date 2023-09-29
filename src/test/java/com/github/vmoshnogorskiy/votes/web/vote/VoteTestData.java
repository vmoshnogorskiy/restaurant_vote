package com.github.vmoshnogorskiy.votes.web.vote;

import com.github.vmoshnogorskiy.votes.model.Vote;
import com.github.vmoshnogorskiy.votes.to.VoteTo;
import com.github.vmoshnogorskiy.votes.web.MatcherFactory;
import com.github.vmoshnogorskiy.votes.web.restaurant.RestaurantTestData;
import com.github.vmoshnogorskiy.votes.web.user.UserTestData;

import java.time.LocalDate;
import java.util.List;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");

    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class, "restaurantName");

    public static final int VOTE1_ID = 1;

    public static final Vote vote1 = new Vote(VOTE1_ID, LocalDate.now(), RestaurantTestData.restaurant2, UserTestData.user);

    public static final Vote vote2 = new Vote(VOTE1_ID + 1, LocalDate.now(), RestaurantTestData.restaurant3, UserTestData.admin);

    public static final List<Vote> votes = List.of(vote1, vote2);

    public static VoteTo getUpdatedVoteTo() {
        return new VoteTo(VOTE1_ID, LocalDate.now(), RestaurantTestData.RESTAURANT1_ID, UserTestData.USER_ID, "Река");
    }

    public static VoteTo getNewVoteTo() {
        return new VoteTo(null, LocalDate.now(), RestaurantTestData.RESTAURANT1_ID, UserTestData.GUEST_ID, "Река");
    }
}
