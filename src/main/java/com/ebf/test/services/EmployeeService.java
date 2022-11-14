package com.ebf.test.services;

import com.ebf.test.RestServiceApplication;
import com.ebf.test.dtos.EmployeeDTO;
import com.ebf.test.entities.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class EmployeeService extends AbstractService<Employee> {

    public List<EmployeeDTO> get(Pageable pageable, String filter) {
        if (filter == null) {
            filter = "";
        }
        TypedQuery<EmployeeDTO> query = em.createNamedQuery("Employee.findAll", EmployeeDTO.class);
        if (pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        return query
                .setParameter("filter", '%' + filter.trim().toLowerCase() + '%')
                .setParameter("company", RestServiceApplication.getCurrentUser().getCompany())
                .getResultList();

    }

    public Long count(String filter) {
        if (filter == null) {
            filter = "";
        }
        return em.createNamedQuery("Employee.countAll", Long.class)
                .setParameter("filter", '%' + filter.trim().toLowerCase() + '%')
                .setParameter("company", RestServiceApplication.getCurrentUser().getCompany())
                .getSingleResult();
    }

    public Double average() {
        return  em.createNamedQuery("Employee.average", Double.class)
                .setParameter("company",RestServiceApplication.getCurrentUser().getCompany())
                .getSingleResult();
    }
}
