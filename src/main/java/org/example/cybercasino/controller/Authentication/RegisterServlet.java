package org.example.cybercasino.controller.Authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.cybercasino.controller.Authentication.utils.AuthenticationUtils;
import org.example.cybercasino.controller.utils.ServletUtils;
import org.example.cybercasino.controller.Authentication.utils.SimpleUser;
import org.example.cybercasino.model.DAOs.UserDAO;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.constants.MessageConstants;
import org.example.cybercasino.utils.BCryptHashAlgorithm;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setResponseHeadersForAccessControl(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setResponseHeadersForAccessControl(resp);

        SimpleUser simpleUser = ServletUtils.mapHttpServletRequestToObject(req, SimpleUser.class);

        if(!AuthenticationUtils.checkRegistrationFields(simpleUser))
            throw new RuntimeException(MessageConstants.INVALID_ARGUMENTS.name());

        String email = simpleUser.email;
        String username = simpleUser.username;
        String hashedPassword = BCryptHashAlgorithm.getInstance().getHash(simpleUser.password);
        User user = new User(email, username, hashedPassword, 0, Date.valueOf("1970-01-01"), false);

        //check if user already exists
        if (UserDAO.findByUsername(username) != null || UserDAO.findByEmail(email) != null) {
            resp.getWriter().write(String.valueOf(false));
            throw new RuntimeException(MessageConstants.USER_ALREADY_EXISTS.name());
        }
        else
            resp.getWriter().write(String.valueOf(UserDAO.addUser(user)));
    }
}
