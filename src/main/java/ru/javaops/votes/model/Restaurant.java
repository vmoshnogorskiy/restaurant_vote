package ru.javaops.votes.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Entity
@Table(name = "restaurant", schema = "public")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"menu"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Restaurant extends NamedEntity {

    @Column(name = "address", nullable = false)
    @NotBlank
    @Size(min = 5, max = 255)
    private String address;

    @Column(name = "created", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @Schema(accessMode = READ_ONLY)
    private LocalDateTime created;

    @Hidden
    @OneToMany(mappedBy = "restaurant")
    private Set<MenuItem> menu;

    @Hidden
    @OneToMany(mappedBy = "restaurant")
    private Set<Vote> votes;

    public Restaurant(Integer id, String name, String address, LocalDateTime created) {
        super(id, name);
        this.address = address;
        this.created = created;
    }

    public Restaurant(Restaurant restaurant, Vote... votes) {
        super(restaurant.getId(), restaurant.getName());
        this.address = restaurant.getAddress();
        this.created = restaurant.getCreated();
        this.setVotes(Arrays.asList(votes));
    }

    public Restaurant(Restaurant restaurant, Collection<MenuItem> menuItems) {
        super(restaurant.getId(), restaurant.getName());
        this.address = restaurant.getAddress();
        this.created = restaurant.getCreated();
        this.setMenu(menuItems);
    }

    public void setMenu(Collection<MenuItem> menu) {
        this.menu = CollectionUtils.isEmpty(menu) ? Collections.EMPTY_SET : new HashSet<>(menu);
    }

    public void setVotes(Collection<Vote> votes) {
        this.votes = CollectionUtils.isEmpty(votes) ? Collections.EMPTY_SET : new HashSet<>(votes);
    }
}
