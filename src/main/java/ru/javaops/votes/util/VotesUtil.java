package ru.javaops.votes.util;

import lombok.experimental.UtilityClass;
import ru.javaops.votes.model.Vote;
import ru.javaops.votes.to.VoteTo;

@UtilityClass
public class VotesUtil {

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getDate(), vote.getRestaurant().getId(), vote.getUser().getId());
    }
}
