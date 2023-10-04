package com.github.vmoshnogorskiy.votes.web.vote;

import com.github.vmoshnogorskiy.votes.repository.VoteRepository;
import com.github.vmoshnogorskiy.votes.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.github.vmoshnogorskiy.votes.to.VoteTo;
import com.github.vmoshnogorskiy.votes.util.JsonUtil;
import com.github.vmoshnogorskiy.votes.web.AbstractControllerTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.vmoshnogorskiy.votes.util.VotesUtil.createToOptional;
import static com.github.vmoshnogorskiy.votes.web.vote.VoteController.REST_URL;
import static com.github.vmoshnogorskiy.votes.web.vote.VoteController.setTimeAfterNotChangeVote;
import static com.github.vmoshnogorskiy.votes.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(voteTos));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + (VOTE1_ID + 1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(voteTo));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void delete() throws Exception {
        setTimeAfterNotChangeVote(LocalTime.now().getHour() + 1, 0);
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + VOTE1_ID))
                .andExpect(status().isNoContent());
        assertFalse(voteRepository.get(UserTestData.USER_ID, VOTE1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void deleteAfterNotChangeHour() throws Exception {
        setTimeAfterNotChangeVote(LocalTime.now().getHour() - 1, 0);
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + VOTE1_ID))
                .andExpect(status().isConflict());
        assertTrue(voteRepository.get(UserTestData.USER_ID, VOTE1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = UserTestData.GUEST_MAIL)
    void deleteOtherUser() throws Exception {
        setTimeAfterNotChangeVote(LocalTime.now().getHour() + 1, 0);
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + VOTE1_ID))
                .andExpect(status().isConflict());
        assertTrue(voteRepository.get(UserTestData.USER_ID, VOTE1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = UserTestData.GUEST_MAIL)
    void add() throws Exception {
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
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void update() throws Exception {
        setTimeAfterNotChangeVote(LocalTime.now().getHour() + 1, 0);
        VoteTo updated = getUpdatedVoteTo();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        VOTE_TO_MATCHER.assertMatch(createToOptional(voteRepository.getExisted(VOTE1_ID)).get(), updated);
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void updateAfterNotChangeHour() throws Exception {
        setTimeAfterNotChangeVote(LocalTime.now().getHour() - 1, 0);
        VoteTo updated = getUpdatedVoteTo();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void updateForOtherUser() throws Exception {
        setTimeAfterNotChangeVote(LocalTime.now().getHour() + 1, 0);
        VoteTo updated = getUpdatedVoteTo();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isConflict());
    }
}