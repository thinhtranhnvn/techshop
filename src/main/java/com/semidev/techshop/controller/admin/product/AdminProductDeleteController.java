package com.semidev.techshop.controller.admin.product;

import com.semidev.techshop.model.service.BrandService;
import com.semidev.techshop.model.service.ProductImageService;
import com.semidev.techshop.model.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminProductDeleteController {
    
    @GetMapping("/admin/product/delete")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int id
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                var product = ProductService.selectProductById(id);
                var brand = BrandService.selectBrandById(product.getBrandId());
                var productImageList = ProductImageService.selectProductImageByProductId(product.getId());
                for (var productImage : productImageList) {
                    ProductImageService.deleteFromProductImage(productImage);
                }
                ProductService.deleteFromProduct(product);
                model.addAttribute("product", product);
                model.addAttribute("brand", brand);
                model.addAttribute("productImage", productImageList.get(0));
                return "page/admin/product/delete.html";
            }
            catch (Exception exc) {
                session.setAttribute("deleteError", "Failed deleting product");
                return "redirect:" + "/admin/product";
            }
        }
    }
    
}
