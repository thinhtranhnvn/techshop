package com.semidev.techshop.controller.admin.visitor;

import com.semidev.techshop.model.service.VisitorService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminVisitorIndexController {
    
    @GetMapping({
        "/admin/visitor",
        "/admin/visitor/"
    })
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="page", required=false, defaultValue="1") int currentPage
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                model.addAttribute("title", "Visitor Index");
                int visitorPerPage = 10;
                var maxPage = (int) Math.ceil((float) VisitorService.selectCountAllVisitor() / visitorPerPage);
                model.addAttribute("previousPage", (1 < currentPage) ? (currentPage - 1) : null);
                model.addAttribute("nextPage", (currentPage < maxPage) ? (currentPage + 1) : null);
                if (currentPage < 0 || maxPage < currentPage) {
                    model.addAttribute("indexError", "Invalid page number");
                }
                else {
                    var visitorList = VisitorService.selectAllVisitorOrderByIdAscLimitOffset(visitorPerPage, (currentPage - 1) * visitorPerPage);
                    model.addAttribute("visitorList", visitorList);
                }
                return "page/admin/visitor/index.html";
            }
            catch (Exception exc) {
                model.addAttribute("indexError", "Failed connecting to database");
                return "page/admin/visitor/index.html";
            }
        }
    }
    
}
