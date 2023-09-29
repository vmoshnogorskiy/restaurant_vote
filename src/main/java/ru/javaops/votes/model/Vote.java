package ru.javaops.votes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vote", schema = "public")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(callSuper = true, exclude = {"restaurant"})
public class Vote extends BaseEntity {

    @Column(name = "actual_date", nullable = false, columnDefinition = "Date")
    @NotNull
    private LocalDate actualDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @JsonIgnore
    private Restaurant restaurant;

    public Vote(Vote v) {
        this(v.id, v.actualDate, v.restaurant, v.user);
    }

    public Vote(Integer id, LocalDate actualDate, Restaurant restaurant, User user) {
        super(id);
        this.actualDate = actualDate;
        this.restaurant = restaurant;
        this.user = user;
    }
}
