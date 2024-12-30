package com.semidev.techshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminLoginController {

    @GetMapping("/admin/login")
    public String accept(HttpServletRequest request, HttpSession session, Model model) {
        if (session.getAttribute("admin_username") != null) {
            session.setAttribute("submitted_username", null);
            session.setAttribute("submitted_password", null);
            session.setAttribute("login_error", null);

            if (session.getAttribute("return_url") != null)
                return "redirect:" + session.getAttribute("return_url");
            else
                return "redirect:" + "/admin";
        }
        else {
            model.addAttribute("title", "Admin Login | TecHland");
            model.addAttribute("submitted_username", session.getAttribute("submitted_username"));
            model.addAttribute("submitted_password", session.getAttribute("submitted_password"));
            model.addAttribute("login_error", session.getAttribute("login_error"));

            return "page/admin/login.html";
        }
    }

}
