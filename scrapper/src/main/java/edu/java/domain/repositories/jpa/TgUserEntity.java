package edu.java.domain.repositories.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tg_users")
public class TgUserEntity {
    @Id
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "user_links",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "link_id")}
    )
    private Set<LinkEntity> links = new HashSet<>();

    public TgUserEntity(Long id) {
        this.id = id;
    }

    public void addLink(LinkEntity link) {
        links.add(link);
        link.getTgChats().add(this);
    }

    public void removeLink(LinkEntity linkEntity) {
        links.removeIf(link -> link.getId().equals(linkEntity.getId()));
        linkEntity.getTgChats().remove(this);
    }
}
