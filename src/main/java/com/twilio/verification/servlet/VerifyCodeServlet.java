package com.twilio.verification.servlet;

import com.authy.AuthyApiClient;
import com.authy.api.Token;
import com.twilio.verification.lib.RequestParametersValidator;
import com.twilio.verification.lib.SessionManager;
import com.twilio.verification.model.User;
import com.twilio.verification.repository.UsersRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VerifyCodeServlet extends HttpServlet {

    private final UsersRepository usersRepository;
    private final SessionManager sessionManager;
    private final AuthyApiClient authyClient;

    @SuppressWarnings("unused")
    public VerifyCodeServlet() {
        this(
                new UsersRepository(),
                new SessionManager(),
                new AuthyApiClient(System.getenv("AUTHY_API_KEY"))
        );
    }

    public VerifyCodeServlet(UsersRepository usersRepository, SessionManager sessionManager, AuthyApiClient authyClient) {
        this.usersRepository = usersRepository;
        this.authyClient = authyClient;
        this.sessionManager = sessionManager;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/verifyCode.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");

        if (validateRequest(request)) {
            int userId = sessionManager.getLoggedUserId(request);
            User user = usersRepository.find(userId);

            Token token = authyClient.getTokens().verify(user.getAuthyId(), code);
            if (token.isOk()) {
                user.setVerified(true);
                usersRepository.update(user);

                sessionManager.logIn(request, userId);
                response.sendRedirect("/account");
            } else {
                request.setAttribute("codeError", "Incorrect code, please try again");
                request.getRequestDispatcher("/verifyCode.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/verifyCode.jsp").forward(request, response);
        }
    }


    private boolean validateRequest(HttpServletRequest request) {
        RequestParametersValidator validator = new RequestParametersValidator(request);

        return validator.validatePresence("code");
    }
}
