package com.semidev.techshop.controller.admin.product;

import com.semidev.techshop.model.service.BrandService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminProductAddController {

    @GetMapping("/admin/product/add")
    public String accept(HttpServletRequest request, HttpSession session, Model model) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                var brandList = BrandService.selectAllBrandOrderByNameAsc();
                model.addAttribute("title", "Add Product");
                model.addAttribute("brandList", brandList);
                model.addAttribute("addError", session.getAttribute("addError"));
                session.setAttribute("addError", null);
                return "page/admin/product/add.html";
            }
            catch (Exception exc) {
                model.addAttribute("addError", "Failed database connection");
                return "page/admin/product/add.html";
            }
        }
    }
    
}
