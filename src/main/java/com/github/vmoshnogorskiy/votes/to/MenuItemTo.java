package com.github.vmoshnogorskiy.votes.to;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class MenuItemTo extends NamedTo {

    @NotNull
    BigDecimal price;

    @NotNull
    LocalDate actualDate;

    @NotNull
    Integer restaurantId;

    public MenuItemTo(Integer id, String name, String price, LocalDate actualDate, Integer restaurantId) {
        super(id, name);
        this.price = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
        this.actualDate = actualDate;
        this.restaurantId = restaurantId;
    }
}
