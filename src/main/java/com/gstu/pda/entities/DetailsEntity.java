package com.gstu.pda.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

@Entity
@Table(name = "pda_details", schema = "all_dump")
public class DetailsEntity {
    @PositiveOrZero
    private int id;
    @Positive
    private int code;
    @Length(max = 300)
    private String description;
    @Length(max = 50, message = "{error.details.name}")
    @Pattern(regexp = "^[a-zA-Z\\s]{5,}$", message = "{error.details.name.pattern}")
    private String name;
    @Positive
    private int count;

    public DetailsEntity() {
    }

    public DetailsEntity(int code, String description, String name, int count) {
        this.code = code;
        this.description = description;
        this.name = name;
        this.count = count;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    @Basic
    @Column(name = "code", nullable = false)
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 300)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "count", nullable = false)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailsEntity that = (DetailsEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
