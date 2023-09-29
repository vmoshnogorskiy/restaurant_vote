package com.github.vmoshnogorskiy.votes.to;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {

    @Schema(accessMode = READ_ONLY)
    LocalDate actualDate;
    @NotNull
    Integer restaurantId;

    @Schema(accessMode = READ_ONLY)
    Integer userId;

    @Schema(accessMode = READ_ONLY)
    String restaurantName;

    public VoteTo(Integer id, LocalDate actualDate, Integer restaurantId, Integer userId, String restaurantName) {
        super(id);
        this.actualDate = actualDate;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.restaurantName = restaurantName;
    }

    @Override
    public String toString() {
        return "VoteTo:" + id;
    }
}
