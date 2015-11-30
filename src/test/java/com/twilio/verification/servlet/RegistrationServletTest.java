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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RegistrationServletTest {

    @Mock private HttpServletRequest request;

    @Mock private HttpServletResponse response;

    @Mock private RequestDispatcher requestDispatcher;

    @Mock private PrintWriter printWriter;

    @Mock private UsersRepository usersRepository;

    @Mock private SessionManager sessionManager;

    @Mock private AuthyApiClient authyClient;

    @Mock private Users users;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerCreatesAUserLocalAndRemotely() throws IOException, ServletException {
        when(request.getParameter("name")).thenReturn("Bob");
        when(request.getParameter("email")).thenReturn("bob@example.com");
        when(request.getParameter("password")).thenReturn("s3cre7");
        when(request.getParameter("countryCode")).thenReturn("1");
        when(request.getParameter("phoneNumber")).thenReturn("555-5555");

        User bob = new User();
        bob.setId(1101);
        when(usersRepository.create(any(User.class))).thenReturn(bob);

        when(response.getWriter()).thenReturn(printWriter);

        when(authyClient.getUsers()).thenReturn(users);
        com.authy.api.User authyUser = new com.authy.api.User();
        authyUser.setId(80001);
        authyUser.setStatus(HttpServletResponse.SC_OK);
        when(users.createUser(anyString(), anyString(), anyString())).thenReturn(authyUser);

        RegistrationServlet servlet = new RegistrationServlet(usersRepository, sessionManager, authyClient);

        servlet.doPost(request, response);

        verify(users).createUser(anyString(), anyString(), anyString());
        verify(usersRepository).create(any(User.class));
        verify(users).requestSms(authyUser.getId());
        verify(sessionManager).partialLogIn(request, bob.getId());
        verify(response).sendRedirect("/verify-code");
    }

    @Test
    public void doNotRegisterAUserWhenInformationIsIncomplete() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("Bob");
        when(request.getParameter("email")).thenReturn("bob@example.com");
        when(request.getParameter("password")).thenReturn("");
        when(request.getParameter("countryCode")).thenReturn("1");
        when(request.getParameter("phoneNumber")).thenReturn("");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        RegistrationServlet servlet = new RegistrationServlet(usersRepository, sessionManager, authyClient);
        servlet.doPost(request, response);

        verify(usersRepository, never()).create(any(User.class));
        verify(sessionManager, never()).partialLogIn(any(HttpServletRequest.class), anyInt());
        verify(request).getRequestDispatcher("/registration.jsp");
    }
}