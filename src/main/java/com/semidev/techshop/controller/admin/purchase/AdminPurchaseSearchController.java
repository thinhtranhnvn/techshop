package com.semidev.techshop.controller.admin.purchase;

import com.semidev.techshop.model.service.PurchaseJoinVisitorService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminPurchaseSearchController {
    
    @GetMapping("/admin/purchase/search")
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
                model.addAttribute("title", "Search Purchase");
                model.addAttribute("id", id);
                var joinedPurchase = PurchaseJoinVisitorService.selectPurchaseJoinVisitorByPurchaseId(id);
                model.addAttribute("joinedPurchase", joinedPurchase);
                return "page/admin/purchase/search.html";
            }
            catch (Exception exc) {
                model.addAttribute("searchError", "Failed connecting to database");
                return "page/admin/purchase/search.html";
            }
        }
    }
    
}
