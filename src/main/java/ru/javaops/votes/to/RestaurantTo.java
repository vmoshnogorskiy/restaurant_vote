package ru.javaops.votes.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo {

    String address;

    LocalDateTime created;

    int votesCount;

    public RestaurantTo(Integer id, String name, String address, LocalDateTime created, int votesCount) {
        super(id, name);
        this.address = address;
        this.created = created;
        this.votesCount = votesCount;
    }

    @Override
    public String toString() {
        return "RestaurantTo:" + id;
    }
}
