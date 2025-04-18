package com.semidev.techshop.controller.admin.slide;

import com.semidev.techshop.model.service.SlideService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminSlideDeleteController {
    
    @GetMapping("/admin/slide/delete")
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
                var record = SlideService.selectSlideById(id);
                SlideService.deleteFromSlide(record);
                model.addAttribute("slide", record);
                return "page/admin/slide/delete.html";
            }
            catch (Exception exc) {
                session.setAttribute("deleteError", "Failed deleting slide");
                return "redirect:" + "/admin/slide";
            }
        }
    }
    
}
