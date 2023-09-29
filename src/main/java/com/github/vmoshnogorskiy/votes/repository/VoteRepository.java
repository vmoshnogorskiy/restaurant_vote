package com.github.vmoshnogorskiy.votes.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.github.vmoshnogorskiy.votes.error.DataConflictException;
import com.github.vmoshnogorskiy.votes.model.Vote;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v JOIN FETCH v.user WHERE v.user.id = ?1")
    List<Vote> getAll(int userId);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.restaurant.id=?1 AND v.actualDate = current_date")
    Integer getVotesCount(int id);

    @Query("SELECT v FROM Vote v WHERE v.user.id=?1 AND v.actualDate = current_date")
    Vote findByUserToday(int userId);

    @Query("SELECT v FROM Vote v JOIN FETCH v.user WHERE v.id = :id and v.user.id = :userId")
    Optional<Vote> get(int userId, int id);

    default Vote getExistedOrBelonged(int userId, int id) {
        return get(userId, id).orElseThrow(
                () -> new DataConflictException("Vote id=" + id + "   is not exist or doesn't belong to User id=" + userId));
    }
}