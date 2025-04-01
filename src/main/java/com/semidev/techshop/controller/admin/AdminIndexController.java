package com.semidev.techshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminIndexController {

    @GetMapping({
        "/admin",
        "/admin/"
    })
    public String accept(HttpServletRequest request, HttpSession session, Model model) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            model.addAttribute("title", "Admin Index");
            return "page/admin/index.html";
        }
    }

}
