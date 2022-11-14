package com.ebf.test.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "employee")
@NamedQueries({
        @NamedQuery(name = "Employee.countAll", query = "SELECT count(e) FROM Employee e WHERE e.company=:company AND LOWER(e.name) like LOWER(:filter)"),
        @NamedQuery(name = "Employee.findAll", query = "SELECT new com.ebf.test.dtos.EmployeeDTO(e) from Employee e " +
                "WHERE e.company=:company AND LOWER(e.name) like LOWER(:filter) order by e.name DESC"),
        @NamedQuery(name = "Employee.average", query = "SELECT AVG(e.salary) FROM Employee e WHERE e.company=:company"),

})
public class Employee extends InfoEntity implements Serializable {

    @Column(length = 255)
    @NotNull
    protected String name;

    @Column(length = 255)
    @NotNull
    protected String surname;

    @NotNull
    @ManyToOne
    protected Company company;
    @NotNull
    @Column(length = 255)
    protected String email;

    @NotNull
    @Column(length = 255)
    protected String address;

    @NotNull
    protected Float salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }
}
