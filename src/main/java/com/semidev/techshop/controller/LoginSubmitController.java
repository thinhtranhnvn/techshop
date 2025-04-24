package com.semidev.techshop.controller;

import com.semidev.techshop.model.service.VisitorService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class LoginSubmitController {
    
    @PostMapping("/login-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="username", required=true) String username,
        @RequestParam(name="password", required=true) String password
    ) {
        session.setAttribute("submittedUsername", username);
        session.setAttribute("submittedPassword", password);
        try {
            var matchedRecord = VisitorService.selectVisitorByUsernameAndPassword(username, password);
            if (matchedRecord == null) {
                session.setAttribute("loginError", "Incorrect login info");
                return "redirect:" + "/login";
            }
            else {
                session.setAttribute("visitorUsername", matchedRecord.getUsername());
                session.setAttribute("submittedUsername", null);
                session.setAttribute("submittedPassword", null);
                if (session.getAttribute("returnURL") != null) {
                    var returnUrl = (String) session.getAttribute("returnURL");
                    session.setAttribute("returnURL", null);
                    return "redirect:" + returnUrl;
                }
                else {
                    return "redirect:" + "/";
                }
            }
        }
        catch (Exception exc) {
            session.setAttribute("loginError", "Failed to login");
            return "redirect:" + "/login";
        }
    }
    
}
