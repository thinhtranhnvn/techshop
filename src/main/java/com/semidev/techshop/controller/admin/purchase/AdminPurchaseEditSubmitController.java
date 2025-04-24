package com.semidev.techshop.controller.admin.purchase;

import com.semidev.techshop.model.service.PurchaseService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminPurchaseEditSubmitController {
    
    @GetMapping("/admin/purchase/edit-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int id,
        @RequestParam(name="status", required=true) int status
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                var purchase = PurchaseService.selectPurchaseById(id);
                purchase.setStatus(status);
                PurchaseService.updatePurchase(purchase);
                session.setAttribute("editInfo", "Successfully updating purchase");
                return "redirect:" + "/admin/purchase/view?id=" + id;
            }
            catch (Exception exc) {
                session.setAttribute("editError", "Failed updating purchase");
                return "redirect:" + "/admin/purchase/view?id=" + id;
            }
        }
    }
    
}
