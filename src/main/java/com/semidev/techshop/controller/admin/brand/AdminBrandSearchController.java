package com.semidev.techshop.controller.admin.brand;

import com.semidev.techshop.model.service.BrandService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminBrandSearchController {

    @GetMapping("/admin/brand/search")
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
                model.addAttribute("title", "Search Brand");
                model.addAttribute("keywords", keywords);
                int brandPerPage = 10;
                var maxPage = (int) Math.ceil((float) BrandService.selectCountBrandByNameLike(keywords) / brandPerPage);
                model.addAttribute("previousPage", (1 < currentPage) ? (currentPage - 1) : null);
                model.addAttribute("nextPage", (currentPage < maxPage) ? (currentPage + 1) : null);
                if (currentPage < 0 || maxPage < currentPage) {
                    model.addAttribute("searchError", "Invalid page number");
                }
                else {
                    var brandList = BrandService.selectBrandByNameLikeOrderByEditedDateDescLimitOffset(keywords, brandPerPage, (currentPage - 1) * brandPerPage);
                    model.addAttribute("brandList", brandList);
                }
                return "page/admin/brand/search.html";
            }
            catch (Exception exc) {
                model.addAttribute("searchError", "Failed connecting to database");
                return "page/admin/brand/search.html";
            }
        }
    }

}
