package com.ebf.test.services;

import com.ebf.test.entities.Company;
import com.ebf.test.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class InitService extends AbstractService<Object> {

    @Autowired
    CompanyService companyService;

    @Autowired
    UserService userService;

    public void init() {

        addCompany();
        addSecondCompany();
    }

    private void addSecondCompany() {
        if (companyService.count() <= 1) {
            Company company = new Company("Test Company_2");
            companyService.save(company);
            addUser("admin2", "admin", company);
        }
    }

    private void addCompany() {
        if (companyService.count() <= 0) {
            Company company = new Company("Test Company_1");
            companyService.save(company);
            addUser("admin", "admin", company);
        }
    }

    private void addUser(String username, String password, Company company) {

        User user = new User();
        user.setUsername(username);
        user.setCompany(company);
        user.setRole(User.UserRole.ADMIN);

        //Encrypt password
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword("{bcrypt}" + hashedPassword);
        userService.save(user);
    }
}
