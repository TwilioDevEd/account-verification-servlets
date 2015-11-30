package com.twilio.verification.servlet;

import com.twilio.verification.lib.SessionManager;
import com.twilio.verification.model.User;
import com.twilio.verification.repository.UsersRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountServlet extends HttpServlet {

    private final UsersRepository usersRepository;
    private final SessionManager sessionManager;

    @SuppressWarnings("unused")
    public AccountServlet() {
        this(new UsersRepository(), new SessionManager());
    }

    public AccountServlet(UsersRepository usersRepository, SessionManager sessionManager) {
        this.usersRepository = usersRepository;
        this.sessionManager = sessionManager;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = sessionManager.getLoggedUserId(request);
        User user = usersRepository.find(userId);

        request.setAttribute("userName", user.getName());
        request.setAttribute("accountStatus", user.isVerified() ? "Verified" : "Not Verified");
        request.setAttribute("verified", user.isVerified());

        request.getRequestDispatcher("/account.jsp").forward(request, response);
    }
}
