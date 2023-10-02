package com.github.vmoshnogorskiy.votes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
@Table(name = "menu_item", schema = "public")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class MenuItem extends NamedEntity {

    @Column(name = "price", nullable = false, columnDefinition = "NUMERIC(9, 2)")
    @NotNull
    private BigDecimal price;

    @Column(name = "actual_date", nullable = false, columnDefinition = "timestamp default now()")
    @JsonIgnore
    @NotNull
    private LocalDate actualDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    @NotNull
    private Restaurant restaurant;

    public MenuItem(Integer id, String name, String price, LocalDate actualDate, Restaurant restaurant) {
        super(id, name);
        this.price = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
        this.actualDate = actualDate;
        this.restaurant = restaurant;
    }
}
