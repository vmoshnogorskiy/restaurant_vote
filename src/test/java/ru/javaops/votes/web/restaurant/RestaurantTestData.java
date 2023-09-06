package ru.javaops.votes.web.restaurant;

import ru.javaops.votes.model.Restaurant;
import ru.javaops.votes.web.MatcherFactory;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "created");

    public static final int RESTAURANT1_ID = 1;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Река",
            "ул. Набережная, д.1", of(2023, Month.JANUARY, 30, 10, 0, 0));
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT1_ID + 1, "Фукусима",
            "ул. Ленина, д.13", of(2022, Month.JUNE, 29, 13, 10, 0));
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT1_ID + 2, "Вкусняшка",
            "ул. Мира, д.15", of(2021, Month.APRIL, 29, 16, 45, 0));

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3);

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "Озеро", "ул. Лесная, д.2", of(2023, Month.JANUARY, 15, 11, 0, 0));
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "Астория", "ул. Луговского, д.1 а", LocalDateTime.now());
    }
}
