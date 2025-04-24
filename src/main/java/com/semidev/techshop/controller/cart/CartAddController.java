package com.semidev.techshop.controller.cart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;


@Controller
public class CartAddController {
    
    @GetMapping("/cart/add")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="product-id", required=true) int productId
    ) {
        try {
            if (session.getAttribute("cartProductIdList") == null) {
                session.setAttribute("cartProductIdList", new ArrayList<Integer>());
            }
            else {
                // initialized
            }
            var cartProductIdList = (ArrayList<Integer>) session.getAttribute("cartProductIdList");
            cartProductIdList.add(productId);
            session.setAttribute("cartProductIdList", cartProductIdList);
            return "redirect:" + "/cart/view";
        }
        catch (Exception exc) {
            session.setAttribute("error", "Failed adding to cart");
            return "redirect:" + "/cart/view";
        }
    }
    
}
