package ru.javaops.votes.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.votes.repository.VoteRepository;
import ru.javaops.votes.to.VoteTo;
import ru.javaops.votes.util.JsonUtil;
import ru.javaops.votes.web.AbstractControllerTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.votes.util.VotesUtil.createToOptional;
import static ru.javaops.votes.web.user.UserTestData.*;
import static ru.javaops.votes.web.vote.VoteController.REST_URL;
import static ru.javaops.votes.web.vote.VoteController.hourAfterNotChangeVote;
import static ru.javaops.votes.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(votes));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(vote1));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteVote() throws Exception {
        hourAfterNotChangeVote = LocalDateTime.now().getHour() + 1;
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + VOTE1_ID))
                .andExpect(status().isNoContent());
        assertFalse(voteRepository.get(USER_ID, VOTE1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteVoteAfterNotChangeHour() throws Exception {
        hourAfterNotChangeVote = LocalDateTime.now().getHour() - 1;
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + VOTE1_ID))
                .andExpect(status().isConflict());
        assertTrue(voteRepository.get(USER_ID, VOTE1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void deleteVoteOtherUser() throws Exception {
        hourAfterNotChangeVote = LocalDateTime.now().getHour() + 1;
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + VOTE1_ID))
                .andExpect(status().isConflict());
        assertTrue(voteRepository.get(USER_ID, VOTE1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void addVote() throws Exception {
        VoteTo newVoteTo = getNewVoteTo();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)));

        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        newVoteTo.setId(newId);
        VOTE_TO_MATCHER.assertMatch(created, newVoteTo);
        VOTE_TO_MATCHER.assertMatch(createToOptional(voteRepository.getExisted(newId)).get(), newVoteTo);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateVote() throws Exception {
        hourAfterNotChangeVote = LocalDateTime.now().getHour() + 1;
        VoteTo updated = getUpdatedVoteTo();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        VOTE_TO_MATCHER.assertMatch(createToOptional(voteRepository.getExisted(VOTE1_ID)).get(), updated);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateVoteAfterNotChangeHour() throws Exception {
        hourAfterNotChangeVote = LocalDateTime.now().getHour() - 1;
        VoteTo updated = getUpdatedVoteTo();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateVoteOtherUser() throws Exception {
        hourAfterNotChangeVote = LocalDateTime.now().getHour() + 1;
        VoteTo updated = getUpdatedVoteTo();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isConflict());
    }
}