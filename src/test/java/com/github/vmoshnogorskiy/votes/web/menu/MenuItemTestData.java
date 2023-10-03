package com.github.vmoshnogorskiy.votes.web.menu;

import com.github.vmoshnogorskiy.votes.model.MenuItem;
import com.github.vmoshnogorskiy.votes.to.MenuItemTo;
import com.github.vmoshnogorskiy.votes.web.MatcherFactory;
import java.util.List;

import static com.github.vmoshnogorskiy.votes.web.restaurant.RestaurantTestData.*;
import static com.github.vmoshnogorskiy.votes.util.MenuItemUtil.createTo;

public class MenuItemTestData {

    public static final MatcherFactory.Matcher<MenuItem> MENU_ITEM_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuItem.class, "actualDate", "restaurant");

    public static final MatcherFactory.Matcher<MenuItemTo> MENU_ITEM_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuItemTo.class);

    public static final int MENU_ITEM1_ID = 1;

    public static final MenuItem menuItem1 = new MenuItem(MENU_ITEM1_ID, "Суп сельский", "180.00", java.time.LocalDate.now(), restaurant1);

    public static final MenuItem menuItem2 = new MenuItem(MENU_ITEM1_ID + 1, "Пюре картофельное", "122.50", java.time.LocalDate.now(), restaurant3);

    public static final MenuItem menuItem3 = new MenuItem(MENU_ITEM1_ID + 2, "Пюре гороховое", "92.50", java.time.LocalDate.now(), restaurant2);

    public static final MenuItem menuItem4 = new MenuItem(MENU_ITEM1_ID + 3, "Котлета из свинины", "110.00", java.time.LocalDate.now(), restaurant2);

    public static final MenuItem menuItem5 = new MenuItem(MENU_ITEM1_ID + 4, "Котлета из говядины", "125", java.time.LocalDate.now(), restaurant3);

    public static final MenuItem menuItem6 = new MenuItem(MENU_ITEM1_ID + 5, "Чай черный", "70.0", java.time.LocalDate.now(), restaurant2);

    public static final MenuItem menuItem7 = new MenuItem(MENU_ITEM1_ID + 6, "Чай черный", "80.00", java.time.LocalDate.now(), restaurant3);

    public static final MenuItemTo menuItemTo1 = createTo(menuItem1);

//    public static final List<MenuItem> menuItems = List.of(menuItem3, menuItem4, menuItem6);
    public static final List<MenuItem> menuItems = List.of(menuItem1, menuItem2, menuItem3, menuItem4, menuItem5, menuItem6, menuItem7);

    public static final List<MenuItemTo> menuItemTos = List.of(createTo(menuItem1), createTo(menuItem2),
            createTo(menuItem3), createTo(menuItem4), createTo(menuItem5), createTo(menuItem6), createTo(menuItem7));

    public static MenuItem getNew() {
        return new MenuItem(null, "Чай черный", "100.00", java.time.LocalDate.now(), restaurant1);
    }

    public static MenuItemTo getNewTo() {
        return new MenuItemTo(null, "Чай черный", "100.00", java.time.LocalDate.now(), RESTAURANT1_ID);
    }

    public static MenuItem getUpdated() {
        return new MenuItem(MENU_ITEM1_ID, "Чай зеленый", "120.00", java.time.LocalDate.now(), restaurant1);
    }

    public static MenuItemTo getUpdatedTo() {
        return new MenuItemTo(MENU_ITEM1_ID, "Чай зеленый", "120.00", java.time.LocalDate.now(), RESTAURANT1_ID);
    }
}
