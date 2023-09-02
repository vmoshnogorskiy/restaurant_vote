package ru.javaops.votes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(name = "updated", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime updated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public MenuItem(MenuItem menuItem) {
        this(menuItem.id, menuItem.name, menuItem.price, menuItem.updated, menuItem.restaurant);
    }

    public MenuItem(Integer id, String name, float price, LocalDateTime updated, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.updated = updated;
        this.restaurant = restaurant;
    }
}
