package ru.javaops.votes.web.restaurant;

import ru.javaops.votes.model.MenuItem;
import ru.javaops.votes.web.MatcherFactory;
import static ru.javaops.votes.web.restaurant.RestaurantTestData.*;

public class MenuItemTestData {

    public static final MatcherFactory.Matcher<MenuItem> MENU_ITEM_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuItem.class, "updated", "restaurant");

    public static final int MENU_ITEM1_ID = 1;

    public static final MenuItem menuItem1 = new MenuItem(MENU_ITEM1_ID, "Суп сельский", 180.0f, java.time.LocalDateTime.now(), restaurant1);

    public static final MenuItem menuItem2 = new MenuItem(MENU_ITEM1_ID + 1, "Пюре картофельное", 122.50f, java.time.LocalDateTime.now(), restaurant3);

    public static final MenuItem menuItem3 = new MenuItem(MENU_ITEM1_ID + 2, "Пюре гороховое", 92.50f, java.time.LocalDateTime.now(), restaurant2);

    public static final MenuItem menuItem4 = new MenuItem(MENU_ITEM1_ID + 3, "Котлета из свинины", 110, java.time.LocalDateTime.now(), restaurant2);

    public static final MenuItem menuItem5 = new MenuItem(MENU_ITEM1_ID + 4, "Котлета из говядины", 125, java.time.LocalDateTime.now(), restaurant3);

    public static final MenuItem menuItem6 = new MenuItem(MENU_ITEM1_ID + 5, "Чай черный", 70.00f, java.time.LocalDateTime.now(), restaurant2);

    public static final MenuItem menuItem7 = new MenuItem(MENU_ITEM1_ID + 6, "Чай черный", 80.00f, java.time.LocalDateTime.now(), restaurant3);

    public static MenuItem getNew() {
        return new MenuItem(null, "Чай черный", 100.00f, java.time.LocalDateTime.now(), restaurant1);
    }

    public static MenuItem getUpdated() {
        return new MenuItem(MENU_ITEM1_ID, "Чай зеленый", 120.00f, java.time.LocalDateTime.now(), restaurant1);
    }
}
