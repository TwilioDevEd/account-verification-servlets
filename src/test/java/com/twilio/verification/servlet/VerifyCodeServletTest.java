package com.twilio.verification.servlet;

import com.authy.AuthyApiClient;
import com.authy.api.Token;
import com.authy.api.Tokens;
import com.twilio.verification.lib.Sender;
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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VerifyCodeServletTest {

    @Mock private HttpServletRequest request;

    @Mock private HttpServletResponse response;

    @Mock private RequestDispatcher requestDispatcher;

    @Mock private UsersRepository usersRepository;

    @Mock private SessionManager sessionManager;

    @Mock private AuthyApiClient authyClient;

    @Mock private Tokens tokens;

    @Mock private Sender sender;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenTheProvidedCodeIsValidThenLogsIn() throws ServletException, IOException {

        final String code = "80001";
        final int userId = 1001;
        when(request.getParameter("code")).thenReturn(code);

        User bob = new User();
        bob.setId(userId);
        bob.setAuthyId(80001);
        bob.setCountryCode("1");
        bob.setPhoneNumber("555-5555");
        when(sessionManager.getLoggedUserId(request)).thenReturn(userId);
        when(usersRepository.find(anyInt())).thenReturn(bob);

        Token token = new Token(200, "", "Token is valid.");
        when(authyClient.getTokens()).thenReturn(tokens);
        when(tokens.verify(bob.getAuthyId(), code)).thenReturn(token);


        VerifyCodeServlet servlet = new VerifyCodeServlet(usersRepository, sessionManager, authyClient, sender);
        servlet.doPost(request, response);

        verify(usersRepository).update(any(User.class));
        verify(sender).send("1555-5555", "You did it! Sign up complete :3");
        verify(sessionManager).logIn(request, bob.getId());
        verify(response).sendRedirect("/account");
    }

    @Test
    public void whenTheProvidedCodeIsInValidThenShowErrors() throws ServletException, IOException {

        final String code = "80001";
        when(request.getParameter("code")).thenReturn(code);

        User bob = new User();
        bob.setAuthyId(80001);
        when(usersRepository.find(anyInt())).thenReturn(bob);

        Token token = new Token(200, "", "");
        when(authyClient.getTokens()).thenReturn(tokens);
        when(tokens.verify(bob.getAuthyId(), code)).thenReturn(token);

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        VerifyCodeServlet servlet = new VerifyCodeServlet(usersRepository, sessionManager, authyClient, sender);
        servlet.doPost(request, response);

        verify(request).setAttribute(anyString(), anyString());
        verify(request).getRequestDispatcher("/verifyCode.jsp");
        verify(usersRepository, never()).update(any(User.class));
        verify(sessionManager, never()).logIn(any(HttpServletRequest.class), anyInt());
    }
}