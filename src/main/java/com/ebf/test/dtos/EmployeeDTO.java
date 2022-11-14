package com.ebf.test.dtos;

import com.ebf.test.entities.Employee;

import java.io.Serializable;

public class EmployeeDTO extends AbstractDTO implements Serializable {

    private String name;
    private String surname;
    private String email;
    private String address;
    private Float salary;
    private Long companyId;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Employee employee) {
        if (employee == null) {
            return;
        }
        this.id = employee.getId();
        this.address = employee.getAddress();
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.salary = employee.getSalary();
        this.companyId = employee.getCompany().getId();
        this.surname = employee.getSurname();
    }

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
