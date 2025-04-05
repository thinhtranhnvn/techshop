package com.semidev.techshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminLogoutController {

    @GetMapping({
        "/admin/logout",
        "/admin/logout/"
    })
    public String accept(HttpServletRequest request, HttpSession session, Model model) {
        if (session.getAttribute("adminUsername") == null) {
            // do nothing
        }
        else {
            session.setAttribute("adminUsername", null);
        }
        return "redirect:" + "/admin";
    }
    
}
