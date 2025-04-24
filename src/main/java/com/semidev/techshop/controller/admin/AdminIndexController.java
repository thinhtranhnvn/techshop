package com.semidev.techshop.controller.admin;

import com.semidev.techshop.model.service.BrandService;
import com.semidev.techshop.model.service.CategoryService;
import com.semidev.techshop.model.service.CollectionService;
import com.semidev.techshop.model.service.PageService;
import com.semidev.techshop.model.service.ProductService;
import com.semidev.techshop.model.service.PurchaseService;
import com.semidev.techshop.model.service.SlideService;
import com.semidev.techshop.model.service.VisitorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminIndexController {

    @GetMapping({
        "/admin",
        "/admin/"
    })
    public String accept(HttpServletRequest request, HttpSession session, Model model) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                model.addAttribute("title", "Admin Index");
                var brandCounter = BrandService.selectCountAllBrand();
                model.addAttribute("brandCounter", brandCounter);
                var productCounter = ProductService.selectCountAllProduct();
                model.addAttribute("productCounter", productCounter);
                var categoryCounter = CategoryService.selectCountAllCategory();
                model.addAttribute("categoryCounter", categoryCounter);
                var collectionCounter = CollectionService.selectCountAllCollection();
                model.addAttribute("collectionCounter", collectionCounter);
                var pageCounter = PageService.selectCountAllPage();
                model.addAttribute("pageCounter", pageCounter);
                var slideCounter = SlideService.selectCountAllSlide();
                model.addAttribute("slideCounter", slideCounter);
                var visitorCounter = VisitorService.selectCountAllVisitor();
                model.addAttribute("visitorCounter", visitorCounter);
                var purchaseCounter = PurchaseService.selectCountAllPurchase();
                model.addAttribute("purchaseCounter", purchaseCounter);
                return "page/admin/index.html";
            }
            catch (Exception exc) {
                model.addAttribute("indexError", "Failed connecting to database");
                return "page/admin/index.html";
            }
        }
    }

}
