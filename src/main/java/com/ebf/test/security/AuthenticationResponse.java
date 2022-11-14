package com.ebf.test.security;


import com.ebf.test.entities.User;

public class AuthenticationResponse {

    private String username;

    private String fullName;

    private String token;

    private String refreshToken;

    private Integer role;

    private String companyName;

    public AuthenticationResponse() {

    }

    public AuthenticationResponse(String username, String fullName, User.UserRole userRole, String token, String refreshToken,String companyName) {
        this.username = username;
        this.fullName = fullName;
        this.token = token;
        this.refreshToken = refreshToken;
        this.role = userRole.getValue();
        this.companyName = companyName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

