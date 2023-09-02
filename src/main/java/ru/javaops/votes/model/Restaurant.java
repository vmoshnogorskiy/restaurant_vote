package ru.javaops.votes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.util.CollectionUtils;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "restaurant", schema = "public")
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

    public Restaurant() {

    }

    public Restaurant(Restaurant r) {
        this(r.id, r.name, r.address, r.created, r.menu);
    }

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


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Set<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(Collection<MenuItem> menu) {
        this.menu = CollectionUtils.isEmpty(menu) ? Collections.EMPTY_SET : new HashSet<>(menu);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "menu=" + menu +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
