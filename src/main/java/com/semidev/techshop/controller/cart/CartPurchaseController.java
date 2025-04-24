package com.semidev.techshop.controller.cart;

import com.semidev.techshop.model.entity.PurPro;
import com.semidev.techshop.model.entity.Purchase;
import com.semidev.techshop.model.service.ProductService;
import com.semidev.techshop.model.service.PurProService;
import com.semidev.techshop.model.service.PurchaseService;
import com.semidev.techshop.model.service.VisitorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Controller
public class CartPurchaseController {
    
    @GetMapping("/cart/purchase")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="product-id", required=true) ArrayList<Integer> productIdList
    ) {
        if (session.getAttribute("visitorUsername") == null) {
            return "redirect:" + "/login";
        }
        else {
            try {
                var username = (String) session.getAttribute("visitorUsername");
                var visitor = VisitorService.selectVisitorByUsername(username);
                
                var purchaseId = PurchaseService.selectLatestPurchaseId() + 1;
                var visitorId  = visitor.getId();
                var placedDate = LocalDateTime.now();
                int status     = 1;
                var purchase = Purchase.createInstance(purchaseId, visitorId, placedDate, status);
                PurchaseService.insertIntoPurchase(purchase);
                
                for (var productId : productIdList) {
                    var product = ProductService.selectProductById(productId);
                    var price     = product.getPrice();
                    var discount  = product.getDiscount();
                    var promotion = product.getPromotion();
                    var purPro = PurPro.createInstance(purchaseId, productId, price, discount, promotion);
                    PurProService.insertIntoPurPro(purPro);
                }
                
                session.setAttribute("cartProductIdList", new ArrayList<Integer>());
                
                return "redirect:" + "/purchase/index";
            }
            catch (Exception exc) {
                session.setAttribute("cartError", "Failed connecting to database");
                return "redirect:" + "/cart/view";
            }
        }
    }
    
}
