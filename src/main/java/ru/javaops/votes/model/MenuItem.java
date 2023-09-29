package ru.javaops.votes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "menu_item", schema = "public")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant"})
public class MenuItem extends NamedEntity {

    @Column(name = "price", nullable = false, columnDefinition = "NUMERIC(9, 2)")
    @NotNull
    private float price;

    @Column(name = "actual_date", nullable = false, columnDefinition = "timestamp default now()")
    @JsonIgnore
    private LocalDate actualDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    public MenuItem(MenuItem menuItem) {
        this(menuItem.id, menuItem.name, menuItem.price, menuItem.actualDate, menuItem.restaurant);
    }

    public MenuItem(Integer id, String name, float price, LocalDate actualDate, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.actualDate = actualDate;
        this.restaurant = restaurant;
    }
}
