package com.twilio.verification.servlet;

import com.authy.AuthyApiClient;
import com.twilio.verification.lib.SessionManager;
import com.twilio.verification.model.User;
import com.twilio.verification.repository.UsersRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResendTokenServlet extends HttpServlet {

    private final UsersRepository usersRepository;
    private final SessionManager sessionManager;
    private final AuthyApiClient authyClient;

    @SuppressWarnings("unused")
    public ResendTokenServlet() {
        this(
                new UsersRepository(),
                new SessionManager(),
                new AuthyApiClient(System.getenv("AUTHY_API_KEY"))
        );
    }

    public ResendTokenServlet(
            UsersRepository usersRepository, SessionManager sessionManager, AuthyApiClient authyClient) {
        this.usersRepository = usersRepository;
        this.sessionManager = sessionManager;
        this.authyClient = authyClient;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = sessionManager.getLoggedUserId(request);
        User user = usersRepository.find(userId);

        // Request SMS authentication
        authyClient.getUsers().requestSms(user.getAuthyId());

        response.sendRedirect("/verify-token");
    }
}
