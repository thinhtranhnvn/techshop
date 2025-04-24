package com.semidev.techshop.controller.cart;

import com.semidev.techshop.model.entity.ProductJoinImage;
import com.semidev.techshop.model.service.CategoryService;
import com.semidev.techshop.model.service.PageService;
import com.semidev.techshop.model.service.ProductJoinImageService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;


@Controller
public class CartViewController {
    
    @GetMapping("/cart/view")
    public String accept(HttpServletRequest request, HttpSession session, Model model) {
        try {
            model.addAttribute("title", "Cart");
            model.addAttribute("visitorUsername", session.getAttribute("visitorUsername"));
            var categoryList = CategoryService.selectAllCategoryOrderByNameAsc();
            model.addAttribute("categoryList", categoryList);
            var pageList = PageService.selectAllPageOrderByPriorityDesc();
            model.addAttribute("pageList", pageList);
            var productIdList = (ArrayList<Integer>) session.getAttribute("cartProductIdList");
            productIdList = (productIdList == null) ? new ArrayList<>() : productIdList;
            var joinedProductList = new ArrayList<ProductJoinImage>();
            for (var productId : productIdList) {
                var joinedProduct = ProductJoinImageService.selectProductJoinImageByProductId(productId);
                joinedProductList.add(joinedProduct);
            }
            model.addAttribute("joinedProductList", joinedProductList);
            return "page/cart/view.html";
        }
        catch (Exception exc) {
            model.addAttribute("error", "Failed connecting to database");
            return "page/404.html";
        }
    }
    
}
