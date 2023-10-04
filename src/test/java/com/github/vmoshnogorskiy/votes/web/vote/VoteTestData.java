package com.github.vmoshnogorskiy.votes.web.vote;

import com.github.vmoshnogorskiy.votes.model.Vote;
import com.github.vmoshnogorskiy.votes.to.VoteTo;
import com.github.vmoshnogorskiy.votes.web.MatcherFactory;
import com.github.vmoshnogorskiy.votes.web.restaurant.RestaurantTestData;
import com.github.vmoshnogorskiy.votes.web.user.UserTestData;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.github.vmoshnogorskiy.votes.util.VotesUtil.createTo;
import static com.github.vmoshnogorskiy.votes.web.restaurant.RestaurantTestData.RESTAURANT1_ID;

public class VoteTestData {

    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class);

    public static final int VOTE1_ID = 1;

    public static final Vote vote1 = new Vote(VOTE1_ID, LocalDate.now(), RestaurantTestData.restaurant2, UserTestData.user);

    public static final Vote vote2 = new Vote(VOTE1_ID + 1, LocalDate.now(), RestaurantTestData.restaurant3, UserTestData.admin);

    public static final Vote vote3 = new Vote(VOTE1_ID + 2, LocalDate.of(2023, Month.SEPTEMBER, 5), RestaurantTestData.restaurant2, UserTestData.user);

    public static final VoteTo voteTo = createTo(vote2);

    public static final List<VoteTo> voteTos = List.of(createTo(vote1), createTo(vote3));

    public static VoteTo getUpdatedVoteTo() {
        return new VoteTo(VOTE1_ID, LocalDate.now(), RESTAURANT1_ID, UserTestData.USER_ID, "Река");
    }

    public static VoteTo getNewVoteTo() {
        return new VoteTo(null, LocalDate.now(), RESTAURANT1_ID, UserTestData.GUEST_ID, "Река");
    }
}
