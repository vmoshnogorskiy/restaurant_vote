package com.github.vmoshnogorskiy.votes.web.menu;

import com.github.vmoshnogorskiy.votes.repository.MenuItemRepository;
import com.github.vmoshnogorskiy.votes.to.MenuItemTo;
import com.github.vmoshnogorskiy.votes.util.JsonUtil;
import com.github.vmoshnogorskiy.votes.web.AbstractControllerTest;
import com.github.vmoshnogorskiy.votes.web.restaurant.RestaurantTestData;
import com.github.vmoshnogorskiy.votes.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.vmoshnogorskiy.votes.web.restaurant.RestaurantTestData.restaurant1;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.vmoshnogorskiy.votes.util.MenuItemUtil.createFromTo;

class AdminMenuItemControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = AdminMenuItemController.REST_URL + '/';

    @Autowired
    private MenuItemRepository repository;

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + MenuItemTestData.MENU_ITEM1_ID))
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(MenuItemTestData.MENU_ITEM1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void deleteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + MenuItemTestData.MENU_ITEM1_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void add() throws Exception {
        MenuItemTo newMenuItemTo = MenuItemTestData.getNewTo();
        ResultActions action = perform(MockMvcRequestBuilders.post(AdminMenuItemController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenuItemTo)));

        MenuItemTo created = MenuItemTestData.MENU_ITEM_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenuItemTo.setId(newId);
        MenuItemTestData.MENU_ITEM_TO_MATCHER.assertMatch(created, newMenuItemTo);
        MenuItemTestData.MENU_ITEM_MATCHER.assertMatch(repository.getExisted(newId), createFromTo(newMenuItemTo, restaurant1));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void addWithoutRestaurant() throws Exception {
        MenuItemTo newMenuItemTo = MenuItemTestData.getWithoutRestaurant();
        perform(MockMvcRequestBuilders.post(AdminMenuItemController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenuItemTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void addForbidden() throws Exception {
        perform(MockMvcRequestBuilders.post(AdminMenuItemController.REST_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void update() throws Exception {
        MenuItemTo updated = MenuItemTestData.getUpdatedTo();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + MenuItemTestData.MENU_ITEM1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        MenuItemTestData.MENU_ITEM_MATCHER.assertMatch(repository.getExisted(RestaurantTestData.RESTAURANT1_ID), createFromTo(updated, restaurant1));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void updateForbidden() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + MenuItemTestData.MENU_ITEM1_ID))
                .andExpect(status().isForbidden());
    }
}