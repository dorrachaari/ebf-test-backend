package com.ebf.test.controllers;

import com.ebf.test.RestServiceApplication;
import com.ebf.test.dtos.EmployeeDTO;
import com.ebf.test.dtos.PageDTO;
import com.ebf.test.entities.Employee;
import com.ebf.test.exceptions.EntityNotFoundException;
import com.ebf.test.exceptions.InvalidRequestException;
import com.ebf.test.services.CompanyService;
import com.ebf.test.services.EmployeeService;
import com.ebf.test.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


@RestController
@RequestMapping(AbstractController.EMPLOYEES_PATH)
public class EmployeeController extends AbstractController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CompanyService companyService;

    private static void verifyAttributes(String attribute, String attributeName, boolean isObligatory) {
        if (isObligatory && StringUtils.isBlank(attribute)) {
            throw new InvalidRequestException(attributeName + " is obligatory");
        }
        if (Utils.isAttributeSizeIncorrect(attribute)) {
            throw new InvalidRequestException(attributeName + " exceeded " + Utils.MAX_ITEM_ATTRIBUTE_LENGTH + " characters");
        }
    }

    private static void verifyAttributes(Float attribute, String attributeName, boolean isObligatory) {
        if (isObligatory && attribute == null) {
            throw new InvalidRequestException(attributeName + " is obligatory");
        }
        if (!Utils.isPositive(attribute)) {
            throw new InvalidRequestException(attributeName + " should not be negative");
        }
    }

    @GetMapping(value = "/list")
    public PageDTO<EmployeeDTO> findEmployeesBy(@RequestParam(value = "filter", required = false) String filter,
                                                @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        page = page == null ? 0 : page - 1;
        pageSize = pageSize == null ? 10 : pageSize;

        PageDTO<EmployeeDTO> employeesListDTO = new PageDTO<>();
        employeesListDTO.setList(employeeService.get(PageRequest.of(page, pageSize), filter));
        employeesListDTO.setPage(++page);
        employeesListDTO.setPageSize(pageSize);
        employeesListDTO.setTotal(employeeService.count(filter));
        return employeesListDTO;
    }

    @GetMapping(value = "/average")
    public Double getAverage() {
        return employeeService.average();
    }

    @Transactional
    @PostMapping("/save")
    public Boolean editEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.findById(Employee.class, employeeDTO.getId());
        if (employee == null) {
            employee = new Employee();
            if (RestServiceApplication.getCurrentUser() == null || RestServiceApplication.getCurrentUser().getCompany() == null) {
                throw new EntityNotFoundException("Current user's company was not found");
            }
            employee.setCompany(RestServiceApplication.getCurrentUser().getCompany());
        }

        String name = employeeDTO.getName();
        verifyAttributes(name, "name", true);
        employee.setName(name);

        String surName = employeeDTO.getSurname();
        verifyAttributes(surName, "surName", true);
        employee.setSurname(surName);

        String address = employeeDTO.getAddress();
        verifyAttributes(address, "address", true);
        employee.setAddress(address);

        Float salary = employeeDTO.getSalary();
        verifyAttributes(salary, "salary", true);
        employee.setSalary(salary);

        String email = employeeDTO.getEmail();
        verifyAttributes(email, "email", true);
        employee.setEmail(email);

        employeeService.save(employee);

        return true;

    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public Boolean deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeService.findById(Employee.class, id);
        if (employee == null) {
            throw new EntityNotFoundException("Employee ID: " + id + " is not found");
        }
        employeeService.remove(employee);
        return true;
    }


}
