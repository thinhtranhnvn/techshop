package com.semidev.techshop.controller.admin.page;

import com.semidev.techshop.model.service.PageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminPageDeleteController {
    
    @GetMapping("/admin/page/delete")
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
                var record = PageService.selectPageById(id);
                PageService.deleteFromPage(record);
                model.addAttribute("page", record);
                return "page/admin/page/delete.html";
            }
            catch (Exception exc) {
                session.setAttribute("deleteError", "Failed deleting page");
                return "redirect:" + "/admin/page";
            }
        }
    }
    
}
