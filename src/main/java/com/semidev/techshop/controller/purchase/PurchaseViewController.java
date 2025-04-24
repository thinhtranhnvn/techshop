package com.semidev.techshop.controller.purchase;

import com.semidev.techshop.model.service.CategoryService;
import com.semidev.techshop.model.service.PageService;
import com.semidev.techshop.model.service.PurProJoinProductService;
import com.semidev.techshop.model.service.PurchaseService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class PurchaseViewController {
    
    @GetMapping("/purchase/view")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int id
    ) {
        try {
            model.addAttribute("title", "Purchase Detail");
            model.addAttribute("visitorUsername", session.getAttribute("visitorUsername"));
            var categoryList = CategoryService.selectAllCategoryOrderByNameAsc();
            model.addAttribute("categoryList", categoryList);
            var pageList = PageService.selectAllPageOrderByPriorityDesc();
            model.addAttribute("pageList", pageList);
            var purchase = PurchaseService.selectPurchaseById(id);
            model.addAttribute("purchase", purchase);
            var joinedPurProList = PurProJoinProductService.selectPurProJoinProductByPurchaseId(id);
            model.addAttribute("joinedPurProList", joinedPurProList);
            return "page/purchase/view.html";
        }
        catch (Exception exc) {
            session.setAttribute("error", "Failed connecting to database");
            return "page/404.html";
        }
    }
    
}
