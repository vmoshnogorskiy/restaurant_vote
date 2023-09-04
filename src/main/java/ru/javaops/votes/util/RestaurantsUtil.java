package ru.javaops.votes.util;

import lombok.experimental.UtilityClass;
import ru.javaops.votes.model.Restaurant;
import ru.javaops.votes.to.RestaurantTo;

@UtilityClass
public class RestaurantsUtil {

    public static RestaurantTo createTo(Restaurant restaurant, int votesCount) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getCreated(), votesCount);
    }
}
