package com.semidev.techshop.controller.admin.brand;

import com.semidev.techshop.exception.ExceptionInvalidBrandEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidBrandEditedDate;
import com.semidev.techshop.exception.ExceptionInvalidBrandId;
import com.semidev.techshop.exception.ExceptionInvalidBrandImageURL;
import com.semidev.techshop.exception.ExceptionInvalidBrandName;
import com.semidev.techshop.exception.ExceptionInvalidBrandSlug;
import com.semidev.techshop.model.service.BrandService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;


@Controller
public class AdminBrandSearchController {

    @GetMapping("/admin/brand/search")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="keywords", required=false, defaultValue="") String keywords,
        @RequestParam(name="page", required=false, defaultValue="1") int currentPage
    ) {
        if (session.getAttribute("admin_username") == null) {
            session.setAttribute("return_url", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            model.addAttribute("title", "Brand search");
            model.addAttribute("keywords", keywords);
            try {
                int brandPerPage = 10;
                int maxPage = (int) Math.ceil((double) BrandService.selectCountBrandByNameLike(keywords) / brandPerPage);
                if (currentPage < 0 || maxPage < currentPage) {
                    model.addAttribute("search_error", "Invalid page number");
                }
                else {
                    var brandList = BrandService.selectBrandByNameLikeOrderByEditedDateDescLimitOffset(keywords, brandPerPage, (currentPage - 1) * brandPerPage);
                    model.addAttribute("brand_list", brandList);
                    model.addAttribute("previous_page", (1 < currentPage) ? (currentPage - 1) : null);
                    model.addAttribute("next_page", (currentPage < maxPage) ? (currentPage + 1) : null);
                }
                model.addAttribute("search_error", null);
                return "page/admin/brand/search.html";
            }
            catch (ExceptionInvalidBrandEditedBy | ExceptionInvalidBrandEditedDate | ExceptionInvalidBrandId | ExceptionInvalidBrandImageURL | ExceptionInvalidBrandName | ExceptionInvalidBrandSlug | NumberFormatException | SQLException exc) {
                model.addAttribute("search_error", "Failed connecting to database");
                return "page/admin/brand/search.html";
            }
        }
    }

}
