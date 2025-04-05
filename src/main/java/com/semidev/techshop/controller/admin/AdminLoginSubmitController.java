package com.semidev.techshop.controller.admin;

import com.semidev.techshop.exception.ExceptionInvalidAdministratorId;
import com.semidev.techshop.exception.ExceptionInvalidAdministratorPassword;
import com.semidev.techshop.exception.ExceptionInvalidAdministratorUsername;
import com.semidev.techshop.model.service.AdministratorService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;


@Controller
public class AdminLoginSubmitController {

    @PostMapping("/admin/login-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="username", required=true) String username,
        @RequestParam(name="password", required=true) String password
    ) {
        try {
            var admin = AdministratorService.selectAdministratorByLoginInfo(username, password);
            if (admin != null) {
                session.setAttribute("adminUsername", admin.getUsername());
                if (session.getAttribute("returnURL") != null) {
                    var returnUrl = (String) session.getAttribute("returnURL");
                    session.setAttribute("returnURL", null);
                    return "redirect:" + returnUrl;
                }
                else {
                    return "redirect:" + "/admin";
                }
            }
            else {
                session.setAttribute("submittedUsername", username);
                session.setAttribute("submittedPassword", password);
                session.setAttribute("loginError", "Invalid login info");
                return "redirect:" + session.getAttribute("returnURL");
            }
        }
        catch (Exception exc) {
            session.setAttribute("loginError", "Failed login");
            return "redirect:" + session.getAttribute("returnURL");
        }
    }

}
