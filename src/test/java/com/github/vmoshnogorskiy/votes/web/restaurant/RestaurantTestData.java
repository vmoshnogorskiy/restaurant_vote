package com.github.vmoshnogorskiy.votes.web.restaurant;

import com.github.vmoshnogorskiy.votes.model.Restaurant;
import com.github.vmoshnogorskiy.votes.to.RestaurantTo;
import com.github.vmoshnogorskiy.votes.web.MatcherFactory;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static com.github.vmoshnogorskiy.votes.util.RestaurantsUtil.createTo;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "created");

    public static MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER =
            MatcherFactory.usingAssertions(RestaurantTo.class,
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    },
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields().isEqualTo(e)
            );

    public static final int RESTAURANT1_ID = 1;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Река",
            "ул. Набережная, д.1", of(2023, Month.JANUARY, 30, 10, 0, 0));
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT1_ID + 1, "Фукусима",
            "ул. Ленина, д.13", of(2022, Month.JUNE, 29, 13, 10, 0));
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT1_ID + 2, "Вкусняшка",
            "ул. Мира, д.15", of(2021, Month.APRIL, 29, 16, 45, 0));

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3);

    public static final List<RestaurantTo> restaurantTos = List.of(createTo(restaurant1, 0),
            createTo(restaurant2, 1), createTo(restaurant3, 1));


    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "Озеро", "ул. Лесная, д.2", of(2023, Month.JANUARY, 15, 11, 0, 0));
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "Астория", "ул. Луговского, д.1 а", LocalDateTime.now());
    }
}
