package com.semidev.techshop.controller.admin.product;

import com.semidev.techshop.exception.ExceptionInvalidBrandId;
import com.semidev.techshop.exception.ExceptionInvalidProductDescription;
import com.semidev.techshop.exception.ExceptionInvalidProductDiscount;
import com.semidev.techshop.exception.ExceptionInvalidProductEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidProductEditedDate;
import com.semidev.techshop.exception.ExceptionInvalidProductId;
import com.semidev.techshop.exception.ExceptionInvalidProductName;
import com.semidev.techshop.exception.ExceptionInvalidProductPrice;
import com.semidev.techshop.exception.ExceptionInvalidProductSlug;
import com.semidev.techshop.exception.ExceptionInvalidProductSpecification;
import com.semidev.techshop.exception.ExceptionNullProductPromotion;
import com.semidev.techshop.model.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;


@Controller
public class AdminProductIndexController {
    
    @GetMapping({
        "/admin/product",
        "/admin/product/"
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
                model.addAttribute("title", "Product index");
                int productPerPage = 10;
                int maxPage = (int) Math.ceil((double) ProductService.selectCountAllProduct() / productPerPage);
                model.addAttribute("previousPage", (1 < currentPage) ? (currentPage - 1) : null);
                model.addAttribute("nextPage", (currentPage < maxPage) ? (currentPage + 1) : null);
                if (currentPage < 0 || maxPage < currentPage) {
                    model.addAttribute("indexError", "Invalid page number");
                }
                else {
                    var productList = ProductService.selectAllProductOrderByEditedDateDescLimitOffset(productPerPage, (currentPage - 1) * productPerPage);
                    model.addAttribute("productList", productList);
                }
                model.addAttribute("deleteError", session.getAttribute("deleteError"));
                session.setAttribute("deleteError", null);
                return "page/admin/product/index.html";
            }
            catch (Exception exc) {
                model.addAttribute("indexError", "Failed connecting to database");
                return "page/admin/product/index.html";
            }
        }
    }
    
}
