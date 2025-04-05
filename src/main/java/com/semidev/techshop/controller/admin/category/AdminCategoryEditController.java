package com.semidev.techshop.controller.admin.category;

import com.semidev.techshop.model.service.CategoryService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminCategoryEditController {
    
    @GetMapping("/admin/category/edit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int id
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            model.addAttribute("title", "Edit Category");
            if (session.getAttribute("submittedName") == null) {
                try {
                    var category = CategoryService.selectCategoryById(id);
                    model.addAttribute("category", category);
                }
                catch (Exception exc) {
                    model.addAttribute("editError", "Failed connecting to database");
                }
            }
            else {
                model.addAttribute("editError", session.getAttribute("editError"));
                session.setAttribute("editError", null);
                model.addAttribute("submittedId", session.getAttribute("submittedId"));
                session.setAttribute("submittedId", null);
                model.addAttribute("submittedName", session.getAttribute("submittedName"));
                session.setAttribute("submittedName", null);
                model.addAttribute("submittedSlug", session.getAttribute("submittedSlug"));
                session.setAttribute("submittedSlug", null);
            }
            return "page/admin/category/edit.html";
        }
    }
    
}
