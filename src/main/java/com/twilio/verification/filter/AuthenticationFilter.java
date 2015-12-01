package com.twilio.verification.filter;

import com.twilio.verification.lib.SessionManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    private final SessionManager sessionManager;

    @SuppressWarnings("unused")
    public AuthenticationFilter() {
        this(new SessionManager());
    }

    public AuthenticationFilter(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (isAuthorized(request)) {
            chain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect("/");
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isAuthorized(HttpServletRequest request) {
        return sessionManager.isAuthenticated(request) || sessionManager.isPartiallyAuthenticated(request);
    }
}
