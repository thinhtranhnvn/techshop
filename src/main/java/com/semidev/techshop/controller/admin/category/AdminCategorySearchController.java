package com.semidev.techshop.controller.admin.category;

import com.semidev.techshop.model.service.CategoryService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminCategorySearchController {
    
    @GetMapping("/admin/category/search")
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
                model.addAttribute("title", "Search Category");
                model.addAttribute("keywords", keywords);
                int categoryPerPage = 10;
                var maxPage = (int) Math.ceil((float) CategoryService.selectCountCategoryByNameLike(keywords) / categoryPerPage);
                model.addAttribute("previousPage", (1 < currentPage) ? (currentPage - 1) : null);
                model.addAttribute("nextPage", (currentPage < maxPage) ? (currentPage + 1) : null);
                if (currentPage < 0 || maxPage < currentPage) {
                    model.addAttribute("searchError", "Invalid page number");
                }
                else {
                    var categoryList = CategoryService.selectCategoryByNameLikeOrderByEditedDateDescLimitOffset(keywords, categoryPerPage, (currentPage - 1) * categoryPerPage);
                    model.addAttribute("categoryList", categoryList);
                }
                return "page/admin/category/search.html";
            }
            catch (Exception exc) {
                model.addAttribute("searchError", "Failed connecting to database");
                return "page/admin/category/search.html";
            }
        }
    }
    
}
