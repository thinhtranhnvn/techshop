package com.semidev.techshop.controller;

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
public class SearchController {
    
    @GetMapping("/search")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="keywords", required=true) String keywords
    ) {
        try {
            model.addAttribute("title", "Search products");
            model.addAttribute("keywords", keywords);
            model.addAttribute("visitorUsername", session.getAttribute("visitorUsername"));
            var categoryList = CategoryService.selectAllCategoryOrderByNameAsc();
            model.addAttribute("categoryList", categoryList);
            var pageList = PageService.selectAllPageOrderByPriorityDesc();
            model.addAttribute("pageList", pageList);
            var joinedProductList = ProductJoinImageService.selectProductJoinImageByProductNameLike(keywords);
            model.addAttribute("joinedProductList", joinedProductList);
            return "page/search.html";
        }
        catch (Exception exc) {
            session.setAttribute("error", "Failed connecting to database");
            return "page/404.html";
        }
    }
    
}
