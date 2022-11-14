package com.ebf.test.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.count", query = "SELECT count(u) FROM User u"),
        @NamedQuery(name = "User.findByUsername", query = "select u from User u where LOWER(trim(u.username)) = Lower(trim(:username))"),
        @NamedQuery(name = "User.findByRefreshToken", query = "select u from User u where u.refreshToken = :refreshToken"),
})
public class User extends InfoEntity implements UserDetails {

    private static final long serialVersionUID = 7979664585926176217L;
    protected Status status;
    @NotNull
    protected UserRole role;
    @ManyToOne
    @NotNull
    protected Company company;
    @NotEmpty
    @Column(unique = true)
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
    private String refreshToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public enum Status {
        Disabled(0), Active(1);
        private int value;

        Status(int value) {
            this.value = value;
        }

        public static Status valueOf(int value) {
            switch (value) {
                case 0:
                    return Disabled;
                default:
                    return Active;
            }
        }

        public int getValue() {
            return value;
        }
    }

    public enum UserRole {
        SIMPLE(0), ADMIN(1);

        private int value;

        UserRole(int value) {
            this.value = value;
        }

        public static UserRole valueOf(int value) {
            switch (value) {
                case 0:
                    return SIMPLE;
                default:
                    return ADMIN;
            }
        }

        public int getValue() {
            return value;
        }
    }
}