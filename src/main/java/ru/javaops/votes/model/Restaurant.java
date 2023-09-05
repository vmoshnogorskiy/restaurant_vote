package ru.javaops.votes.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

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
    @NotNull
    private LocalDateTime created;

    @OneToMany(mappedBy = "restaurant")
    private Set<MenuItem> menu;

    @OneToMany(mappedBy = "restaurant")
    private Set<Vote> votes;

    public Restaurant(Integer id, String name, String address, LocalDateTime created) {
        super(id, name);
        this.address = address;
        this.created = created;
    }

    public Restaurant(Integer id, String name, String address, LocalDateTime created, Collection<MenuItem> menuItems) {
        super(id, name);
        this.address = address;
        this.created = created;
        this.setMenu(menuItems);
    }

    public void setMenu(Collection<MenuItem> menu) {
        this.menu = CollectionUtils.isEmpty(menu) ? Collections.EMPTY_SET : new HashSet<>(menu);
    }
}
