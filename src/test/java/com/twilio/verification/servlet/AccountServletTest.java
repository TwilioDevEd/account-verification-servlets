package com.twilio.verification.servlet;

import com.twilio.verification.lib.SessionManager;
import com.twilio.verification.model.User;
import com.twilio.verification.repository.UsersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class AccountServletTest {

    @Mock private HttpServletRequest request;

    @Mock private HttpServletResponse response;

    @Mock private RequestDispatcher requestDispatcher;

    @Mock private UsersRepository usersRepository;

    @Mock private SessionManager sessionManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { true, "Verified" },
                { false, "Not Verified" }
        });
    }

    private boolean verified;

    private String accountStatus;

    public AccountServletTest(boolean verified, String accountStatus) {
        this.verified = verified;
        this.accountStatus = accountStatus;
    }

    @Test
    public void showsLoggedUserAccountInformation() throws ServletException, IOException {

        User bob = new User();
        bob.setName("Bob");
        bob.setVerified(verified);

        when(usersRepository.find(anyInt())).thenReturn(bob);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        AccountServlet servlet = new AccountServlet(usersRepository, sessionManager);
        servlet.doGet(request, response);

        verify(request).setAttribute("userName", bob.getName());
        verify(request).setAttribute("accountStatus", accountStatus);
        verify(request).setAttribute("verified", bob.isVerified());
        verify(request).getRequestDispatcher("/account.jsp");
    }
}