package org.example.cybercasino.controller.Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.cybercasino.controller.Authentication.utils.AuthToken;
import org.example.cybercasino.controller.Authentication.utils.AuthenticationUtils;
import org.example.cybercasino.controller.Authentication.utils.Credentials;
import org.example.cybercasino.controller.Authentication.utils.ServletUtils;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setResponseHeadersForAccessControl(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setResponseHeadersForAccessControl(resp);

        Credentials credentials = ServletUtils.mapHttpServletRequestToObject(req, Credentials.class);

        String username = credentials.username();
        String plainPassword = credentials.password();
        String token = AuthenticationUtils.encodeToken(username, plainPassword);
        AuthToken authToken = new AuthToken();
        authToken.token = token;
        if (AuthenticationUtils.getUserFromToken(token) != null)
            resp.getWriter().write(new ObjectMapper().writeValueAsString(authToken));
        else
            resp.getWriter().write("null");
    }
}
