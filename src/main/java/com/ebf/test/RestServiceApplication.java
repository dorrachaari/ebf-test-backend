package com.ebf.test;

import com.ebf.test.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@EnableScheduling
public class RestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

    public static User getCurrentUser() {
        Authentication authCtx = SecurityContextHolder.getContext().getAuthentication();
        return (authCtx == null || !(authCtx.getPrincipal() instanceof User)) ? null : (User) authCtx.getPrincipal();
    }

}
