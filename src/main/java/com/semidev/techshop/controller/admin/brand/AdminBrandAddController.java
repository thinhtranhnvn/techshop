package com.semidev.techshop.controller.admin.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminBrandAddController {

    @GetMapping("/admin/brand/add")
    public String accept(HttpServletRequest request, HttpSession session, Model model) {
        if (session.getAttribute("admin_username") == null) {
            session.setAttribute("return_url", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            model.addAttribute("title", "Add brand");
            model.addAttribute("submitted_name", session.getAttribute("submitted_name"));
            model.addAttribute("submitted_image_url", session.getAttribute("submitted_image_url"));
            model.addAttribute("submitted_slug", session.getAttribute("submitted_slug"));
            model.addAttribute("add_error", session.getAttribute("add_error"));
            return "page/admin/brand/add.html";
        }
    }

}
