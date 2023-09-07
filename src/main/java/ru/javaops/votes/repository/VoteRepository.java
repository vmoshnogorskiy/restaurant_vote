package ru.javaops.votes.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.votes.error.DataConflictException;
import ru.javaops.votes.model.Meal;
import ru.javaops.votes.model.Vote;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.date = current_date")
    List<Vote> getAllVotes();

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.restaurant.id=?1 AND v.date = current_date")
    Integer getVotesCount(int id);

    @Query("SELECT v FROM Vote v WHERE v.user.id=?1 AND v.date = current_date")
    Vote findVoteByUserToday(int userId);

    @Query("SELECT v FROM Vote v JOIN FETCH v.user WHERE v.id = :id and v.user.id = :userId")
    Optional<Vote> get(int userId, int id);

    default Vote getExistedOrBelonged(int userId, int id) {
        return get(userId, id).orElseThrow(
                () -> new DataConflictException("Vote id=" + id + "   is not exist or doesn't belong to User id=" + userId));
    }
}