package com.github.vmoshnogorskiy.votes.util;

import com.github.vmoshnogorskiy.votes.model.MenuItem;
import com.github.vmoshnogorskiy.votes.model.Restaurant;
import com.github.vmoshnogorskiy.votes.to.MenuItemTo;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class MenuItemUtil {

    public static MenuItem createFromTo(MenuItemTo itemTo, Restaurant restaurant) {
        MenuItem item = new MenuItem();
        item.setId(itemTo.getId());
        item.setName(itemTo.getName());
        item.setPrice(itemTo.getPrice());
        item.setActualDate(itemTo.getActualDate());
        item.setRestaurant(restaurant);
        return item;
    }

    public static MenuItemTo createTo(MenuItem item) {
        return new MenuItemTo(item.getId(), item.getName(), item.getPrice().toString(), item.getActualDate(), item.getRestaurant().id());
    }

    public static Optional<MenuItemTo> createToOptional(MenuItem item) {
        return Optional.of(createTo(item));
    }
}
