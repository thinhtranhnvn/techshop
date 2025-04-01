package com.semidev.techshop.controller.admin.brand;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminBrandAddController {

    @GetMapping("/admin/brand/add")
    public String accept(HttpServletRequest request, HttpSession session, Model model) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            model.addAttribute("title", "Add Brand");
            model.addAttribute("submittedName", session.getAttribute("submittedName"));
            session.setAttribute("submittedName", null);
            model.addAttribute("submittedImageURL", session.getAttribute("submittedImageURL"));
            session.setAttribute("submittedImageURL", null);
            model.addAttribute("submittedSlug", session.getAttribute("submittedSlug"));
            session.setAttribute("submittedSlug", null);
            model.addAttribute("addError", session.getAttribute("addError"));
            session.setAttribute("addError", null);
            return "page/admin/brand/add.html";
        }
    }

}
