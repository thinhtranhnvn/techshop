package com.semidev.techshop.controller.admin.product;

import com.semidev.techshop.exception.ExceptionInvalidBrandEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidBrandEditedDate;
import com.semidev.techshop.exception.ExceptionInvalidBrandId;
import com.semidev.techshop.exception.ExceptionInvalidBrandImageURL;
import com.semidev.techshop.exception.ExceptionInvalidBrandName;
import com.semidev.techshop.exception.ExceptionInvalidBrandSlug;
import com.semidev.techshop.exception.ExceptionInvalidImageURL;
import com.semidev.techshop.exception.ExceptionInvalidProductDescription;
import com.semidev.techshop.exception.ExceptionInvalidProductDiscount;
import com.semidev.techshop.exception.ExceptionInvalidProductEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidProductEditedDate;
import com.semidev.techshop.exception.ExceptionInvalidProductId;
import com.semidev.techshop.exception.ExceptionInvalidProductImageId;
import com.semidev.techshop.exception.ExceptionInvalidProductName;
import com.semidev.techshop.exception.ExceptionInvalidProductPrice;
import com.semidev.techshop.exception.ExceptionInvalidProductSlug;
import com.semidev.techshop.exception.ExceptionInvalidProductSpecification;
import com.semidev.techshop.exception.ExceptionNullProductPromotion;
import com.semidev.techshop.model.service.BrandService;
import com.semidev.techshop.model.service.ProductJoinBrandJoinProductImageService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


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
                    var productJoinBrandJoinProductImage = ProductJoinBrandJoinProductImageService.selectProductJoinBrandJoinProductImageByProductId(id);
                    var brandList = BrandService.selectAllBrandOrderByNameAsc();
                    model.addAttribute("product", productJoinBrandJoinProductImage.product);
                    model.addAttribute("brand", productJoinBrandJoinProductImage.brand);
                    model.addAttribute("brandList", brandList);
                    model.addAttribute("productImageList", productJoinBrandJoinProductImage.productImageList);
                }
                catch (ExceptionInvalidBrandId
                        | ExceptionInvalidImageURL
                        | ExceptionInvalidProductDescription
                        | ExceptionInvalidProductDiscount
                        | ExceptionInvalidProductEditedBy
                        | ExceptionInvalidProductEditedDate
                        | ExceptionInvalidProductId
                        | ExceptionInvalidProductImageId
                        | ExceptionInvalidProductName
                        | ExceptionInvalidProductPrice
                        | ExceptionInvalidProductSlug
                        | ExceptionInvalidProductSpecification
                        | ExceptionNullProductPromotion
                        | SQLException
                        | ExceptionInvalidBrandName
                        | ExceptionInvalidBrandImageURL
                        | ExceptionInvalidBrandSlug
                        | ExceptionInvalidBrandEditedDate
                        | ExceptionInvalidBrandEditedBy
                        exc
                ) {
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
