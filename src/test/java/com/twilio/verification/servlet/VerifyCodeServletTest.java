package com.twilio.verification.servlet;

import com.authy.AuthyApiClient;
import com.authy.api.Token;
import com.authy.api.Tokens;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VerifyCodeServletTest {

    @Mock private HttpServletRequest request;

    @Mock private HttpServletResponse response;

    @Mock private RequestDispatcher requestDispatcher;

    @Mock private UsersRepository usersRepository;

    @Mock private AuthyApiClient authyClient;

    @Mock private Tokens tokens;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenTheProvidedCodeIsValidThenLogsIn() throws ServletException, IOException {

        final String code = "80001";
        when(request.getParameter("code")).thenReturn(code);

        User bob = new User();
        bob.setAuthyId(1101);
        when(usersRepository.find(anyInt())).thenReturn(bob);

        Token token = new Token(200, "", "Token is valid.");
        when(authyClient.getTokens()).thenReturn(tokens);
        when(tokens.verify(bob.getAuthyId(), code)).thenReturn(token);

        VerifyCodeServlet servlet = new VerifyCodeServlet(usersRepository, authyClient);
        servlet.doPost(request, response);

        verify(usersRepository).update(any(User.class));
    }

    @Test
    public void whenTheProvidedCodeIsInValidThenShowErrors() throws ServletException, IOException {

        final String code = "80001";
        when(request.getParameter("code")).thenReturn(code);

        User bob = new User();
        bob.setAuthyId(1101);
        when(usersRepository.find(anyInt())).thenReturn(bob);

        Token token = new Token(200, "", "");
        when(authyClient.getTokens()).thenReturn(tokens);
        when(tokens.verify(bob.getAuthyId(), code)).thenReturn(token);

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        VerifyCodeServlet servlet = new VerifyCodeServlet(usersRepository, authyClient);
        servlet.doPost(request, response);

        verify(request).setAttribute(anyString(), anyString());
        verify(request).getRequestDispatcher("/verifyCode.jsp");
    }
}