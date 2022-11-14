package com.ebf.test.services;

import com.ebf.test.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;


@Repository
public class UserService extends AbstractService<User> {

    public User findByUsername(String username) {
        try {
            return em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", username).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public User findByRefreshToken(String refreshToken) {
        try {
            return em.createNamedQuery("User.findByRefreshToken", User.class)
                    .setParameter("refreshToken", refreshToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
