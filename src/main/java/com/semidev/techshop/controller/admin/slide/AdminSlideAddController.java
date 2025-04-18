package com.semidev.techshop.controller.admin.slide;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminSlideAddController {
    
    @GetMapping("/admin/slide/add")
    public String accept(HttpServletRequest request, HttpSession session, Model model) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            model.addAttribute("title", "Add Slide");
            model.addAttribute("submittedImageURL", session.getAttribute("submittedImageURL"));
            session.setAttribute("submittedImageURL", null);
            model.addAttribute("submittedCaption", session.getAttribute("submittedCaption"));
            session.setAttribute("submittedCaption", null);
            model.addAttribute("submittedHref", session.getAttribute("submittedHref"));
            session.setAttribute("submittedHref", null);
            model.addAttribute("submittedPriority", session.getAttribute("submittedPriority"));
            session.setAttribute("submittedPriority", null);
            model.addAttribute("addError", session.getAttribute("addError"));
            session.setAttribute("addError", null);
            return "page/admin/slide/add.html";
        }
    }
    
}
