package ru.javaops.votes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vote", schema = "public")
public class Vote extends BaseEntity {

    public static final String DELETE = "Vote.delete";
    public static final String ALL_SORTED = "Vote.getAllSorted";
    public static final String GET_FOR_RESTAURANT = "Vote.getForRestaurant";

    @Column(name = "date", nullable = false, columnDefinition = "Date")
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(Vote v) {
        this(v.id, v.date, v.restaurant, v.user);
    }

    public Vote(Integer id, LocalDate date, Restaurant restaurant, User user) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "datetime=" + date +
                ", user=" + user +
                ", restaurant=" + restaurant +
                ", id=" + id +
                '}';
    }
}
