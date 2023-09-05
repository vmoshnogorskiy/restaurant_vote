package ru.javaops.votes.to;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {

    LocalDate date;
    @NotNull
    Integer restaurantId;

    Integer userId;

    String restaurantName;

    public VoteTo(Integer id, LocalDate date, Integer restaurantId, Integer userId, String restaurantName) {
        super(id);
        this.date = date;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.restaurantName = restaurantName;
    }

    @Override
    public String toString() {
        return "VoteTo:" + id;
    }
}
