package com.semidev.techshop.controller.admin.collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminCollectionAddController {
    
    @GetMapping("/admin/collection/add")
    public String accept(HttpServletRequest request, HttpSession session, Model model) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            model.addAttribute("title", "Add Collection");
            model.addAttribute("addError", session.getAttribute("addError"));
            session.setAttribute("addError", null);
            return "page/admin/collection/add.html";
        }
    }
    
}
