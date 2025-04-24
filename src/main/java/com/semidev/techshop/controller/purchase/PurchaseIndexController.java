package com.semidev.techshop.controller.purchase;

import com.semidev.techshop.model.service.CategoryService;
import com.semidev.techshop.model.service.PageService;
import com.semidev.techshop.model.service.PurchaseService;
import com.semidev.techshop.model.service.VisitorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class PurchaseIndexController {
    
    @GetMapping("/purchase/index")
    public String accept(HttpServletRequest request, HttpSession session, Model model) {
        try {
            model.addAttribute("title", "Purchase Index");
            model.addAttribute("visitorUsername", session.getAttribute("visitorUsername"));
            var categoryList = CategoryService.selectAllCategoryOrderByNameAsc();
            model.addAttribute("categoryList", categoryList);
            var pageList = PageService.selectAllPageOrderByPriorityDesc();
            model.addAttribute("pageList", pageList);
            var username = (String) session.getAttribute("visitorUsername");
            var visitor = VisitorService.selectVisitorByUsername(username);
            var purchaseList = PurchaseService.selectPurchaseByVisitorId(visitor.getId());
            model.addAttribute("purchaseList", purchaseList);
            return "page/purchase/index.html";
        }
        catch (Exception exc) {
            session.setAttribute("error", "Failed connecting database");
            return "page/404.html";
        }
    }
    
}
