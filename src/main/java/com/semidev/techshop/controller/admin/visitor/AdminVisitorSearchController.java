package com.semidev.techshop.controller.admin.visitor;

import com.semidev.techshop.model.service.VisitorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminVisitorSearchController {
    
    @GetMapping("/admin/visitor/search")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=false, defaultValue="0") int id
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                model.addAttribute("title", "Search Visitor");
                model.addAttribute("id", id);
                var visitor = VisitorService.selectVisitorById(id);
                model.addAttribute("visitor", visitor);
                return "page/admin/visitor/search.html";
            }
            catch (Exception exc) {
                model.addAttribute("searchError", "Failed connecting to database");
                return "page/admin/visitor/search.html";
            }
        }
    }
    
}
