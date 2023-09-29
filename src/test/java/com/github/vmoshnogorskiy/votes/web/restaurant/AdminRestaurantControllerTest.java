package com.github.vmoshnogorskiy.votes.web.restaurant;

import com.github.vmoshnogorskiy.votes.model.MenuItem;
import com.github.vmoshnogorskiy.votes.model.Restaurant;
import com.github.vmoshnogorskiy.votes.repository.MenuItemRepository;
import com.github.vmoshnogorskiy.votes.repository.RestaurantRepository;
import com.github.vmoshnogorskiy.votes.web.AbstractControllerTest;
import com.github.vmoshnogorskiy.votes.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.github.vmoshnogorskiy.votes.util.JsonUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = AdminRestaurantController.REST_URL + '/';

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + RestaurantTestData.RESTAURANT1_ID))
                .andExpect(status().isNoContent());
        assertFalse(restaurantRepository.findById(RestaurantTestData.RESTAURANT1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void deleteRestaurantForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + RestaurantTestData.RESTAURANT1_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void update() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + RestaurantTestData.RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(restaurantRepository.getExisted(RestaurantTestData.RESTAURANT1_ID), updated);
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void updateRestaurantForbidden() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + RestaurantTestData.RESTAURANT1_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void addRestaurant() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(AdminRestaurantController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)));

        Restaurant created = RestaurantTestData.RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(restaurantRepository.getExisted(newId), newRestaurant);
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void addRestaurantForbidden() throws Exception {
        perform(MockMvcRequestBuilders.post(AdminRestaurantController.REST_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void deleteMenuItem() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + RestaurantTestData.RESTAURANT1_ID + "/menuitems/" + MenuItemTestData.MENU_ITEM1_ID))
                .andExpect(status().isNoContent());
        assertFalse(menuItemRepository.findById(MenuItemTestData.MENU_ITEM1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void deleteMenuItemForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + RestaurantTestData.RESTAURANT1_ID + "/menuitems/" + MenuItemTestData.MENU_ITEM1_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void addMenuItem() throws Exception {
        MenuItem newMenuItem = MenuItemTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_SLASH + RestaurantTestData.RESTAURANT1_ID + "/menuitems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenuItem)));

        MenuItem created = MenuItemTestData.MENU_ITEM_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenuItem.setId(newId);
        MenuItemTestData.MENU_ITEM_MATCHER.assertMatch(created, newMenuItem);
        MenuItemTestData.MENU_ITEM_MATCHER.assertMatch(menuItemRepository.getExisted(newId), newMenuItem);
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void addMenuItemForbidden() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + RestaurantTestData.RESTAURANT1_ID + "/menuitems"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void updateMenuItem() throws Exception {
        MenuItem updated = MenuItemTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + RestaurantTestData.RESTAURANT1_ID + "/menuitems/" + MenuItemTestData.MENU_ITEM1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        MenuItemTestData.MENU_ITEM_MATCHER.assertMatch(menuItemRepository.getExisted(RestaurantTestData.RESTAURANT1_ID), updated);
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void updateMenuItemForbidden() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + RestaurantTestData.RESTAURANT1_ID + "/menuitems/" + MenuItemTestData.MENU_ITEM1_ID))
                .andExpect(status().isForbidden());
    }
}