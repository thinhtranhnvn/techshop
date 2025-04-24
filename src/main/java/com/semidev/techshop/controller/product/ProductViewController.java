package com.semidev.techshop.controller.product;

import com.semidev.techshop.model.service.CategoryService;
import com.semidev.techshop.model.service.PageService;
import com.semidev.techshop.model.service.ProductJoinImageService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class ProductViewController {
    
    @GetMapping("/product/view")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="slug", required=true) String slug
    ) {
        try {
            model.addAttribute("visitorUsername", session.getAttribute("visitorUsername"));
            var categoryList = CategoryService.selectAllCategoryOrderByNameAsc();
            model.addAttribute("categoryList", categoryList);
            var pageList = PageService.selectAllPageOrderByPriorityDesc();
            model.addAttribute("pageList", pageList);
            var joinedProduct = ProductJoinImageService.selectProductJoinImageByProductSlug(slug);
            model.addAttribute("joinedProduct", joinedProduct);
            model.addAttribute("title", joinedProduct.product.getName());
            return "page/product/view.html";
        }
        catch (Exception exc) {
            session.setAttribute("error", "Failed connecting to database");
            return "page/404.html";
        }
    }
    
}
