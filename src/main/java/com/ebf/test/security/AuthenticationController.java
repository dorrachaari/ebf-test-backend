package com.ebf.test.security;


import com.ebf.test.RestServiceApplication;
import com.ebf.test.controllers.AbstractController;
import com.ebf.test.entities.User;
import com.ebf.test.exceptions.AuthorizationException;
import com.ebf.test.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(AbstractController.AUTHENTICATION_PATH)
public class AuthenticationController extends AbstractController {

    public static final String LOGOUT_PATH = "/logout";
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/signin")
    public AuthenticationResponse signin(@RequestBody AuthenticationRequest data) {
        try {
            return authenticate(data.getUsername(), data.getPassword());
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping(value = "/refresh")
    public AuthenticationResponse refreshToken(@RequestBody TokenRefreshRequest data) {

        boolean isTokenValid = false;
        String refreshToken = data != null ? data.getRefreshToken() : null;

        try {
            //validation of JWT refresh token
            isTokenValid = (refreshToken != null) && jwtTokenProvider.validateToken(refreshToken);
        } catch (AuthenticationException e) {
            isTokenValid = false;
        }

        if (!isTokenValid) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        //check token in database
        User authenticatedUser = userService.findByRefreshToken(refreshToken);

        if (authenticatedUser == null) {
            throw new BadCredentialsException("Refresh token not found in database");
        }

        String token = jwtTokenProvider.createToken(authenticatedUser.getUsername(), authenticatedUser.getRole().toString(), false);
        return new AuthenticationResponse(authenticatedUser.getUsername(), authenticatedUser.getName(), authenticatedUser.getRole(), token, refreshToken,authenticatedUser.getCompany().getName());
    }

    @PostMapping(value = LOGOUT_PATH)
    public boolean logout() {
        String connectedtUserName = RestServiceApplication.getCurrentUser() == null ? null : RestServiceApplication.getCurrentUser().getUsername();
        if (connectedtUserName == null) {
            return false;
        }

        User user = userService.findByUsername(connectedtUserName);
        if (user == null) {
            return false;
        }

        user.setRefreshToken(null);
        userService.save(user);
        return true;
    }


    private AuthenticationResponse authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User authenticatedUser = userService.findByUsername(username);

        if (authenticatedUser == null) {
            throw new UsernameNotFoundException("Username " + username + "not found");
        }


        if (User.Status.Disabled.equals(authenticatedUser.getStatus())) {
            throw new AuthorizationException("user.notAuthenticated");
        }

        String token = jwtTokenProvider.createToken(username, authenticatedUser.getRole().toString(), false);
        String refreshToken = jwtTokenProvider.createToken(username, authenticatedUser.getRole().toString(), true);

        //save refresh token in database
        authenticatedUser.setRefreshToken(refreshToken);
        userService.save(authenticatedUser);

        return new AuthenticationResponse(username, authenticatedUser.getName(), authenticatedUser.getRole(), token, refreshToken,authenticatedUser.getCompany().getName());
    }
}