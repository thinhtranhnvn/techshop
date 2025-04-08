package com.semidev.techshop.controller.admin.category;

import com.semidev.techshop.model.entity.CatPro;
import com.semidev.techshop.model.service.CatProService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminCategoryAddProductSubmitController {
    
    @PostMapping("/admin/category/add-product-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="category-id", required=true) int categoryId,
        @RequestParam(name="product-id", required=true) int productId,
        @RequestParam(name="included", required=true) int included
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                var record = CatPro.createInstance(categoryId, productId);
                if (included == 0) {
                    CatProService.deleteFromCatPro(record);
                    session.setAttribute("addProductInfo", "Successfully removed product from the category");
                }
                else {
                    CatProService.insertIntoCatPro(record);
                    session.setAttribute("addProductInfo", "Successfully added product into the category");
                }
                return "redirect:" + "/admin/category/view?id=" + categoryId;
            }
            catch (Exception exc) {
                session.setAttribute("addProductError", "Failed connecting to database");
                return "redirect:" + "/admin/category/view?id=" + categoryId;
            }
        }
    }
        
}
