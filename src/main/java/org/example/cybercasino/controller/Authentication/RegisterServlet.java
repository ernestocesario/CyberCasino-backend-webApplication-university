package org.example.cybercasino.controller.Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.cybercasino.controller.Authentication.utils.SimpleUser;
import org.example.cybercasino.model.DAOs.UserDAO;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.constants.FrontendConstants;
import org.example.cybercasino.utils.BCryptHashAlgorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
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
        SimpleUser simpleUser = objectMapper.readValue(requestBody.toString(), SimpleUser.class);

        String email = simpleUser.email;
        String username = simpleUser.username;
        String hashedPassword = BCryptHashAlgorithm.getInstance().getHash(simpleUser.password);
        User user = new User(email, username, hashedPassword, 10, Date.valueOf("1970-01-01"), false);

        //check if user already exists
        if (UserDAO.getInstance().findUserByUsername(username) != null || UserDAO.getInstance().findByEmail(email) != null)
            resp.getWriter().write("false");
        else
            resp.getWriter().write(String.valueOf(UserDAO.getInstance().addUser(user)));
    }
}
