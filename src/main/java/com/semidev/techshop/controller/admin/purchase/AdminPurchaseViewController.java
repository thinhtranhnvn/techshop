package com.semidev.techshop.controller.admin.purchase;

import com.semidev.techshop.model.service.PurProJoinProductService;
import com.semidev.techshop.model.service.PurchaseJoinVisitorService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminPurchaseViewController {
    
    @GetMapping("/admin/purchase/view")
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
                model.addAttribute("title", "Purchase Detail");
                var joinedPurchase = PurchaseJoinVisitorService.selectPurchaseJoinVisitorByPurchaseId(id);
                model.addAttribute("joinedPurchase", joinedPurchase);
                var joinedPurProList = PurProJoinProductService.selectPurProJoinProductByPurchaseId(id);
                model.addAttribute("joinedPurProList", joinedPurProList);
                model.addAttribute("viewError", session.getAttribute("viewError"));
                session.setAttribute("viewError", null);
                model.addAttribute("editError", session.getAttribute("editError"));
                session.setAttribute("editError", null);
                model.addAttribute("editInfo", session.getAttribute("editInfo"));
                session.setAttribute("editInfo", null);
                return "page/admin/purchase/view.html";
            }
            catch (Exception exc) {
                model.addAttribute("viewError", "Failed connecting to database");
                return "page/admin/purchase/view.html";
            }
        }
    }
    
}
