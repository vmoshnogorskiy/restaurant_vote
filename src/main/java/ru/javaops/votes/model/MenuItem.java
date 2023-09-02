package ru.javaops.votes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "menu_item", schema = "public")
public class MenuItem extends NamedEntity {

    @Column(name = "price", nullable = false, columnDefinition = "NUMERIC(9, 2)")
    @NotNull
    private float price;

    @Column(name = "updated", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime updated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public MenuItem() {
    }

    public MenuItem(MenuItem menuItem) {
        this(menuItem.id, menuItem.name, menuItem.price, menuItem.updated, menuItem.restaurant);
    }

    public MenuItem(Integer id, String name, float price, LocalDateTime updated, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.updated = updated;
        this.restaurant = restaurant;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "price=" + price +
                ", updated=" + updated +
                ", restaurant=" + restaurant +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
