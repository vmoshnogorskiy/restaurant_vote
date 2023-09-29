package com.github.vmoshnogorskiy.votes.util;

import com.github.vmoshnogorskiy.votes.model.Restaurant;
import lombok.experimental.UtilityClass;
import com.github.vmoshnogorskiy.votes.to.RestaurantTo;

@UtilityClass
public class RestaurantsUtil {

    public static RestaurantTo createTo(Restaurant restaurant, int votesCount) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getCreated(), votesCount);
    }
}
