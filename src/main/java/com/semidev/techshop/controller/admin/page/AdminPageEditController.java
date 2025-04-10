package com.semidev.techshop.controller.admin.page;

import com.semidev.techshop.model.service.PageService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminPageEditController {
    
    @GetMapping("/admin/page/edit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int id
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            model.addAttribute("title", "Edit Product");
            if (session.getAttribute("submittedTitle") == null) {
                try {
                    var record = PageService.selectPageById(id);
                    model.addAttribute("page", record);
                }
                catch (Exception exc) {
                    model.addAttribute("editError", "Failed database connection");
                }
            }
            else {
                model.addAttribute("editError", session.getAttribute("editError"));
                session.setAttribute("editError", null);
                model.addAttribute("submittedTitle", session.getAttribute("submittedTitle"));
                session.setAttribute("submittedTitle", null);
                model.addAttribute("submittedMenuName", session.getAttribute("submittedMenuName"));
                session.setAttribute("submittedMenuName", null);
                model.addAttribute("submittedContent", session.getAttribute("submittedContent"));
                session.setAttribute("submittedContent", null);
                model.addAttribute("submittedSlug", session.getAttribute("submittedSlug"));
                session.setAttribute("submittedSlug", null);
                model.addAttribute("submittedPriority", session.getAttribute("submittedPriority"));
                session.setAttribute("submittedPriority", null);
            }
            return "page/admin/page/edit.html";
        }
    }
    
}
