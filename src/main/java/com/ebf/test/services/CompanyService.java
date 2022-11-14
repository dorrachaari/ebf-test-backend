package com.ebf.test.services;

import com.ebf.test.entities.Company;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyService extends AbstractService<Company> {

    public Long count() {
        return em.createNamedQuery("Company.countAll", Long.class)
                .getSingleResult();
    }

}
