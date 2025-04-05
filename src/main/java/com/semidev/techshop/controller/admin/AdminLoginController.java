package com.semidev.techshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminLoginController {

    @GetMapping({
        "/admin/login",
        "/admin/login/"
    })
    public String accept(HttpServletRequest request, HttpSession session, Model model) {
        if (session.getAttribute("adminUsername") != null) {
            session.setAttribute("submittedUsername", null);
            session.setAttribute("submittedPassword", null);
            session.setAttribute("loginError", null);
            if (session.getAttribute("returnURL") != null)
                return "redirect:" + session.getAttribute("returnURL");
            else
                return "redirect:" + "/admin";
        }
        else {
            model.addAttribute("title", "Admin Login");
            model.addAttribute("submittedUsername", session.getAttribute("submittedUsername"));
            session.setAttribute("submittedUsername", null);
            model.addAttribute("submittedPassword", session.getAttribute("submittedPassword"));
            session.setAttribute("submittedPassword", null);
            model.addAttribute("loginError", session.getAttribute("loginError"));
            session.setAttribute("loginError", null);
            return "page/admin/login.html";
        }
    }

}
