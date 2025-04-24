package com.semidev.techshop.controller.admin.purchase;

import com.semidev.techshop.model.service.PurchaseJoinVisitorService;
import com.semidev.techshop.model.service.PurchaseService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminPurchaseIndexController {
    
    @GetMapping({
        "/admin/purchase",
        "/admin/purchase/"
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
                model.addAttribute("title", "Purchase Index");
                int purchasePerPage = 10;
                var maxPage = (int) Math.ceil((float) PurchaseService.selectCountAllPurchase() / purchasePerPage);
                model.addAttribute("previousPage", (1 < currentPage) ? (currentPage - 1) : null);
                model.addAttribute("nextPage", (currentPage < maxPage) ? (currentPage + 1) : null);
                if (currentPage < 0 || maxPage < currentPage) {
                    model.addAttribute("indexError", "Invalid page number");
                }
                else {
                    var joinedPurchaseList = PurchaseJoinVisitorService.selectAllPurchaseJoinVisitorOrderByPlacedDateDescLimitOffset(purchasePerPage, (currentPage - 1) * purchasePerPage);
                    model.addAttribute("joinedPurchaseList", joinedPurchaseList);
                }
                return "page/admin/purchase/index.html";
            }
            catch (Exception exc) {
                model.addAttribute("indexError", "Failed connecting to database");
                return "page/admin/purchase/index.html";
            }
        }
    }
    
}
