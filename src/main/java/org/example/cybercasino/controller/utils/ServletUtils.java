package org.example.cybercasino.controller.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.cybercasino.model.constants.FrontendConstants;

import java.io.BufferedReader;
import java.io.IOException;

public class ServletUtils {
    private ServletUtils() {}

    public static void setResponseHeadersForAccessControl(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", FrontendConstants.frontendUrl);
        resp.setHeader("Access-Control-Allow-Methods", "POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    public static <T> T mapHttpServletRequestToObject(HttpServletRequest req, Class<T> objType) throws IOException {
        BufferedReader bufferedReader = req.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            requestBody.append(line);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(requestBody.toString(), objType);
    }
}
