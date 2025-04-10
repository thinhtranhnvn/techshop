package com.semidev.techshop.controller.admin.product;

import com.semidev.techshop.model.service.BrandService;
import com.semidev.techshop.model.service.ProductJoinBrandJoinImageService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminProductEditController {

    @GetMapping("/admin/product/edit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int id
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            model.addAttribute("title", "Edit Product");
            if (session.getAttribute("submittedName") == null) {
                try {
                    var productJoinBrandJoinImage = ProductJoinBrandJoinImageService.selectProductJoinBrandJoinImageByProductId(id);
                    var brandList = BrandService.selectAllBrandOrderByNameAsc();
                    model.addAttribute("product", productJoinBrandJoinImage.product);
                    model.addAttribute("brand", productJoinBrandJoinImage.brand);
                    model.addAttribute("imageList", productJoinBrandJoinImage.imageList);
                    model.addAttribute("brandList", brandList);
                }
                catch (Exception exc) {
                    model.addAttribute("editError", "Failed database connection");
                }
            }
            else {
                model.addAttribute("editError", session.getAttribute("editError"));
                session.setAttribute("editError", null);
                model.addAttribute("submittedId", session.getAttribute("submittedId"));
                session.setAttribute("submittedId", null);
                model.addAttribute("submittedBrandId", session.getAttribute("submittedBrandId"));
                session.setAttribute("submittedBrandId", null);
                model.addAttribute("submittedName", session.getAttribute("submittedName"));
                session.setAttribute("submittedName", null);
                model.addAttribute("submittedImageURL", session.getAttribute("submittedImageURL"));
                session.setAttribute("submittedImageURL", null);
                model.addAttribute("submittedPrice", session.getAttribute("submittedPrice"));
                session.setAttribute("submittedPrice", null);
                model.addAttribute("submittedDiscount", session.getAttribute("submittedDiscount"));
                session.setAttribute("submittedDiscount", null);
                model.addAttribute("submittedPromotion", session.getAttribute("submittedPromotion"));
                session.setAttribute("submittedPromotion", null);
                model.addAttribute("submittedDescription", session.getAttribute("submittedDescription"));
                session.setAttribute("submittedDescription", null);
                model.addAttribute("submittedSpecification", session.getAttribute("submittedSpecification"));
                session.setAttribute("submittedSpecification", null);
                model.addAttribute("submittedSlug", session.getAttribute("submittedSlug"));
                session.setAttribute("submittedSlug", null);
            }
            return "page/admin/product/edit.html";
        }
    }
    
}
