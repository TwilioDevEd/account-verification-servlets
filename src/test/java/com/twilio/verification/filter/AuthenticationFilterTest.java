package com.twilio.verification.filter;

import com.twilio.verification.lib.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthenticationFilterTest {

    @Mock private HttpServletRequest request;

    @Mock private HttpServletResponse response;

    @Mock private RequestDispatcher requestDispatcher;

    @Mock private FilterChain chain;

    @Mock private SessionManager sessionManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenTheRequestIsAuthorizedThenDoFilter() throws IOException, ServletException {

        when(sessionManager.isAuthenticated(any(HttpServletRequest.class))).thenReturn(true);

        AuthenticationFilter filter = new AuthenticationFilter(sessionManager);
        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    @Test
    public void whenTheRequestIsUnauthorizedThenRedirectToHome() throws IOException, ServletException {

        when(sessionManager.isAuthenticated(any(HttpServletRequest.class))).thenReturn(false);
        when(sessionManager.isPartiallyAuthenticated(any(HttpServletRequest.class))).thenReturn(false);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        AuthenticationFilter filter = new AuthenticationFilter(sessionManager);
        filter.doFilter(request, response, chain);

        verify(response).sendRedirect("/");
        verify(chain, never()).doFilter(request, response);
    }
}