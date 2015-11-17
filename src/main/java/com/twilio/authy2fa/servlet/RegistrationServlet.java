package com.twilio.authy2fa.servlet;

import com.twilio.authy2fa.models.User;
import com.twilio.authy2fa.models.UserService;
import com.twilio.authy2fa.lib.EmailValidator;
import com.twilio.authy2fa.lib.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet{

    private static final SessionManager sessionManager = new SessionManager();
    private static final UserService service = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/registration.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String countryCode = request.getParameter("countryCode");
        String phoneNumber = request.getParameter("phoneNumber");

        if (validateRequest(request, name, email, password, countryCode, phoneNumber)) {

            User user = service.create(new User(name, email, password, countryCode, phoneNumber));

            sessionManager.LogIn(request, user.getId());
            response.sendRedirect("/account");
        } else {
            preserveStatusRequest(request, name, email, countryCode, phoneNumber);
            request.getRequestDispatcher("/registration.jsp").forward(request, response);
        }
    }

    private boolean validateRequest(
            HttpServletRequest request,
            String name,
            String email,
            String password,
            String countryCode,
            String phoneNumber) {

        boolean isValid = true;

        if (name.isEmpty()) {
            request.setAttribute("nameError", "Name can't be blank");
            isValid = false;
        }

        boolean isValidEmail = new EmailValidator().validate(email);
        if (email.isEmpty()) {
            request.setAttribute("emailError", "Email can't be blank");
            isValid = false;
        } else if (!isValidEmail) {
            request.setAttribute("emailInvalidError", "Email is invalid");
            isValid = false;
        }

        if (password.isEmpty()) {
            request.setAttribute("passwordError", "Password can't be blank");
            isValid = false;
        }

        if (countryCode.isEmpty()) {
            request.setAttribute("countryCodeError", "Country code can't be blank");
            isValid = false;
        }

        if (phoneNumber.isEmpty()) {
            request.setAttribute("phoneNumberError", "Phone number can't be blank");
            isValid = false;
        }

        return isValid;
    }

    private void preserveStatusRequest(
            HttpServletRequest request,
            String name,
            String email,
            String countryCode,
            String phoneNumber) {
        request.setAttribute("name", name);
        request.setAttribute("email", email);
        request.setAttribute("countryCode", countryCode);
        request.setAttribute("phoneNumber", phoneNumber);
    }
}