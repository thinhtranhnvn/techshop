package com.semidev.techshop.controller;

import com.semidev.techshop.model.service.CategoryService;
import com.semidev.techshop.model.service.PageService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String accept(HttpServletRequest request, HttpSession session, Model model) {
        try {
            model.addAttribute("title", "Login");
            var categoryList = CategoryService.selectAllCategoryOrderByNameAsc();
            model.addAttribute("categoryList", categoryList);
            var pageList = PageService.selectAllPageOrderByPriorityDesc();
            model.addAttribute("pageList", pageList);
            model.addAttribute("loginError", session.getAttribute("loginError"));
            session.setAttribute("loginError", null);
            model.addAttribute("submittedUsername", session.getAttribute("submittedUsername"));
            session.setAttribute("submittedUsername", null);
            model.addAttribute("submittedPassword", session.getAttribute("submittedPassword"));
            session.setAttribute("submittedPassword", null);
            
            return "page/login.html";
        }
        catch (Exception exc) {
            model.addAttribute("error", "Failed connecting to database");
            return "page/404.html";
        }
    }
    
}
