package com.ebf.test.security;

import com.ebf.test.controllers.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtTokenFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        Date startTime = null;
        if (logger.isDebugEnabled()) {
            startTime = new Date();
        }

        String requestedUrl = ((HttpServletRequest) req).getRequestURI();
        logger.debug("Requested URL: " + requestedUrl);

        // No token verification for auth controller
        if (!requestedUrl.endsWith(AuthenticationController.LOGOUT_PATH) && requestedUrl.startsWith(AbstractController.AUTHENTICATION_PATH)) {
            filterChain.doFilter(req, res);
            return;
        }

        boolean isTokenNotExpired;
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);

        try {
            isTokenNotExpired = (token != null) && jwtTokenProvider.validateToken(token);
        } catch (AuthenticationException e) {

            //Failed to validate or parse provided token
            HttpServletResponse response = (HttpServletResponse) res;
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid Token");
            return;
        }

        if (!isTokenNotExpired) {
            HttpServletResponse response = (HttpServletResponse) res;
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
            return;
        }

        Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(req, res);

        if (logger.isDebugEnabled() && startTime != null) {
            logger.debug("Execution time (" + requestedUrl + "): " + (new Date().getTime() - startTime.getTime()) + " ms");
        }
    }
}