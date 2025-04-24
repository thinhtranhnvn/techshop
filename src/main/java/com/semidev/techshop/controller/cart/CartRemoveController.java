package com.semidev.techshop.controller.cart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;


@Controller
public class CartRemoveController {
    
    @GetMapping("/cart/remove")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="product-id", required=true) int productId
    ) {
        var cartProductIdList = (ArrayList<Integer>) session.getAttribute("cartProductIdList");
        cartProductIdList.removeIf(pId -> pId == productId);
        session.setAttribute("cartProductIdList", cartProductIdList);
        return "redirect:" + "/cart/view";
    }
    
}
