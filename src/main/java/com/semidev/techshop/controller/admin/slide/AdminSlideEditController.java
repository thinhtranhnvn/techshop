package com.semidev.techshop.controller.admin.slide;

import com.semidev.techshop.model.service.SlideService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminSlideEditController {
    
    @GetMapping("/admin/slide/edit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int id
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                model.addAttribute("title", "Edit Slide");
                var slide = SlideService.selectSlideById(id);
                model.addAttribute("slide", slide);
            }
            catch (Exception exc) {
                model.addAttribute("editError", "Failed connecting to database");
            }
            return "page/admin/slide/edit.html";
        }
    }
    
}
