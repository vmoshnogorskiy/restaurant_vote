package ru.javaops.votes.web.restaurant;

import ru.javaops.votes.model.MenuItem;
import ru.javaops.votes.web.MatcherFactory;

import java.util.List;

import static ru.javaops.votes.web.restaurant.RestaurantTestData.*;

public class MenuItemTestData {

    public static final MatcherFactory.Matcher<MenuItem> MENU_ITEM_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuItem.class, "updated", "restaurant");

    public static final int MENU_ITEM1_ID = 3;

    public static final MenuItem menuItem1 = new MenuItem(MENU_ITEM1_ID, "Пюре гороховое", 92.50f, java.time.LocalDateTime.now(), restaurant2);

    public static final MenuItem menuItem2 = new MenuItem(MENU_ITEM1_ID + 1, "Котлета из свинины", 110, java.time.LocalDateTime.now(), restaurant2);

    public static final MenuItem menuItem3 = new MenuItem(MENU_ITEM1_ID + 3, "Чай черный", 70.00f, java.time.LocalDateTime.now(), restaurant2);

    public static final List<MenuItem> menuItems = List.of(menuItem1, menuItem2, menuItem3);

    public static MenuItem getNew() {
        return new MenuItem(null, "Чай черный", 100.00f, java.time.LocalDateTime.now(), restaurant1);
    }

    public static MenuItem getUpdated() {
        return new MenuItem(MENU_ITEM1_ID, "Чай зеленый", 120.00f, java.time.LocalDateTime.now(), restaurant1);
    }
}
