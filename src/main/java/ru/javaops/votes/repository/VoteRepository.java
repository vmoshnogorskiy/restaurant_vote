package ru.javaops.votes.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.votes.model.Vote;

import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.date = current_date")
    List<Vote> getAllVotes();

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.restaurant.id=?1 AND v.date = current_date")
    Integer getVotesCount(int id);

    @Query("SELECT v FROM Vote v WHERE v.user.id=?1 AND v.date = current_date")
    Vote findVoteByUserToday(int userId);
}