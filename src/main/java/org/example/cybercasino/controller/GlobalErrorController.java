package org.example.cybercasino.controller;

import org.example.cybercasino.model.constants.FrontendConstants;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "redirect:" + FrontendConstants.frontendUrl + "/";
    }
}