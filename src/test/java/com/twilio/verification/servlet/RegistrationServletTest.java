package com.twilio.verification.servlet;

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

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerCreatesAUserLocally() throws IOException, ServletException {
        when(request.getParameter("name")).thenReturn("Bob");
        when(request.getParameter("email")).thenReturn("bob@example.com");
        when(request.getParameter("password")).thenReturn("s3cre7");
        when(request.getParameter("countryCode")).thenReturn("1");
        when(request.getParameter("phoneNumber")).thenReturn("555-5555");

        User bob = new User();
        when(usersRepository.create(any(User.class))).thenReturn(bob);

        when(response.getWriter()).thenReturn(printWriter);

        RegistrationServlet servlet = new RegistrationServlet(usersRepository);

        servlet.doPost(request, response);

        verify(usersRepository).create(any(User.class));
        verify(printWriter).print(anyString());
    }

    @Test
    public void doNotRegisterAUserWhenInformationIsIncomplete() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("Bob");
        when(request.getParameter("email")).thenReturn("bob@example.com");
        when(request.getParameter("password")).thenReturn("");
        when(request.getParameter("countryCode")).thenReturn("1");
        when(request.getParameter("phoneNumber")).thenReturn("");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        RegistrationServlet servlet = new RegistrationServlet(usersRepository);
        servlet.doPost(request, response);

        verify(usersRepository, never()).create(any(User.class));
        verify(request).getRequestDispatcher("/registration.jsp");
    }
}