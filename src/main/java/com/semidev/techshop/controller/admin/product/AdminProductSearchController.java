package com.semidev.techshop.controller.admin.product;

import com.semidev.techshop.model.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminProductSearchController {

    @GetMapping("/admin/product/search")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="keywords", required=false, defaultValue="") String keywords,
        @RequestParam(name="page", required=false, defaultValue="1") int currentPage
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                model.addAttribute("title", "Search Product");
                model.addAttribute("keywords", keywords);
                int productPerPage = 10;
                var maxPage = (int) Math.ceil((float) ProductService.selectCountProductByNameLike(keywords) / productPerPage);
                model.addAttribute("previousPage", (1 < currentPage) ? (currentPage - 1) : null);
                model.addAttribute("nextPage", (currentPage < maxPage) ? (currentPage + 1) : null);
                if (currentPage < 0 || maxPage < currentPage) {
                    model.addAttribute("searchError", "Invalid page number");
                }
                else {
                    var productList = ProductService.selectProductByNameLikeOrderByEditedDateDescLimitOffset(keywords, productPerPage, (currentPage - 1) * productPerPage);
                    model.addAttribute("productList", productList);
                }
                model.addAttribute("searchError", null);
                return "page/admin/product/search.html";
            }
            catch (Exception exc) {
                model.addAttribute("Failed connecting database");
                return "page/admin/product/search.html";
            }
        }
    }
    
}
