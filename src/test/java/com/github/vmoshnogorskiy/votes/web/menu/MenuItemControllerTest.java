package com.github.vmoshnogorskiy.votes.web.menu;

import com.github.vmoshnogorskiy.votes.web.AbstractControllerTest;
import com.github.vmoshnogorskiy.votes.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.vmoshnogorskiy.votes.web.menu.MenuItemTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuItemControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = MenuItemController.REST_URL + '/';

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(MenuItemController.REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_TO_MATCHER.contentJson(menuItemTos));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + MENU_ITEM1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_TO_MATCHER.contentJson(menuItemTo1));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + MENU_ITEM1_ID + 1000))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + MENU_ITEM1_ID))
                .andExpect(status().isUnauthorized());
    }
}