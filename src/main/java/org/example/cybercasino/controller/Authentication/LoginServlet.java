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
import org.example.cybercasino.model.constants.FrontendConstants;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Aggiungi le seguenti righe per gestire le richieste OPTIONS
        resp.setHeader("Access-Control-Allow-Origin", FrontendConstants.frontendUrl);
        resp.setHeader("Access-Control-Allow-Methods", "POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            requestBody.append(line);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Credentials credentials = objectMapper.readValue(requestBody.toString(), Credentials.class);

        String username = credentials.username;
        String plainPassword = credentials.password;
        String token = AuthenticationUtils.encodeToken(username, plainPassword);
        AuthToken authToken = new AuthToken();
        authToken.token = token;
        if (AuthenticationUtils.userExists(username, plainPassword))
            resp.getWriter().write(objectMapper.writeValueAsString(authToken));
        else
            resp.getWriter().write("null");
    }
}
