    package com.semidev.techshop.controller.admin.collection;

import com.semidev.techshop.model.entity.ColPro;
import com.semidev.techshop.model.service.ColProService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminCollectionAddProductSubmitController {
    
    @PostMapping("/admin/collection/add-product-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="collection-id", required=true) int collectionId,
        @RequestParam(name="product-id", required=true) int productId,
        @RequestParam(name="included", required=true) int included
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                var record = ColPro.createInstance(collectionId, productId);
                if (included == 0) {
                    ColProService.deleteFromColPro(record);
                    session.setAttribute("addProductInfo", "Successfully removed product from the collection");
                }
                else {
                    ColProService.insertIntoColPro(record);
                    session.setAttribute("addProductInfo", "Successfully added product into the collection");
                }
                return "redirect:" + "/admin/collection/view?id=" + collectionId;
            }
            catch (Exception exc) {
                session.setAttribute("addProductError", "Failed connecting to database");
                return "redirect:" + "/admin/collection/view?id=" + collectionId;
            }
        }
    }
    
}
