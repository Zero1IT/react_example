package com.gstu.pda.beans;

import javax.persistence.*;

@Entity
@Table(name = "details", schema = "detailsstock")
public class DetailsEntity {
    private int id;
    private int code;
    private String description;
    private String name;
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
        if (!(o instanceof DetailsEntity)) return false;

        DetailsEntity that = (DetailsEntity) o;

        if (getId() != that.getId()) return false;
        if (getCode() != that.getCode()) return false;
        if (getCount() != that.getCount()) return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + getCount();
        return result;
    }
}
