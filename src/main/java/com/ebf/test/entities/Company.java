package com.ebf.test.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "company")
@NamedQueries({
        @NamedQuery(name = "Company.countAll", query = "SELECT count(c) FROM Company c"),
})
public class Company extends InfoEntity implements Serializable {

    @Column(length = 255)
    @NotNull
    protected String name;

    public Company() {
    }

    public Company(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
