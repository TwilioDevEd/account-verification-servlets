package com.twilio.verification.servlet;

import com.authy.AuthyApiClient;
import com.authy.api.Users;
import com.twilio.verification.lib.SessionManager;
import com.twilio.verification.model.User;
import com.twilio.verification.repository.UsersRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ResendTokenServletTest {

    @Mock private HttpServletRequest request;

    @Mock private HttpServletResponse response;

    @Mock private UsersRepository usersRepository;

    @Mock private SessionManager sessionManager;

    @Mock private AuthyApiClient authyClient;

    @Mock private Users users;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public  void requestSmsAuthentication() throws ServletException, IOException {

        User bob = new User();
        bob.setAuthyId(80001);

        when(usersRepository.find(anyInt())).thenReturn(bob);
        when(authyClient.getUsers()).thenReturn(users);

        ResendTokenServlet servlet = new ResendTokenServlet(usersRepository, sessionManager, authyClient);
        servlet.doPost(request, response);

        verify(users).requestSms(bob.getAuthyId());
        verify(response).sendRedirect("/verify-token");
    }
}