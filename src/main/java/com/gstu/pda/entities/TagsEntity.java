package com.gstu.pda.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "pda_tags", schema = "all_dump")
public class TagsEntity {
    private int id;
    private String name;
    @JsonIgnore
    private Collection<DetailsEntity> details;

    public TagsEntity() {
    }

    public TagsEntity(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "tags")
    public Collection<DetailsEntity> getDetails() {
        return details;
    }

    public void setDetails(Collection<DetailsEntity> detailsById) {
        this.details = detailsById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagsEntity that = (TagsEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
