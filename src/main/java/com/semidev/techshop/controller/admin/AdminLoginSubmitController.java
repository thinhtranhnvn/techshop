package com.semidev.techshop.controller.admin;

import com.semidev.techshop.model.entity.Administrator;
import com.semidev.techshop.model.service.AdministratorService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminLoginSubmitController {

    @PostMapping("/admin/login/submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="username", required=true) String username,
        @RequestParam(name="password", required=true) String password
    ) {
        try {
            Administrator admin = AdministratorService.selectAdministratorByLoginInfo(username, password);

            if (admin != null) {
                session.setAttribute("admin_username", admin.getUsername());

                if (session.getAttribute("return_url") != null) {
                    String returnUrl = (String) session.getAttribute("return_url");
                    session.setAttribute("return_url", null);
                    return "redirect:" + returnUrl;
                }
                else {
                    return "redirect:" + "/admin";
                }
            }
            else {
                session.setAttribute("submitted_username", username);
                session.setAttribute("submitted_password", password);
                session.setAttribute("login_error", "Invalid login info");

                return "redirect:" + session.getAttribute("return_url");
            }
        }
        catch (Exception exc) {
            exc.printStackTrace();
            session.setAttribute("login_error", "Failed login");

            return "redirect:" + session.getAttribute("return_url");
        }
    }

}
