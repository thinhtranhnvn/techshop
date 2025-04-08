package com.semidev.techshop.controller.admin.collection;

import com.semidev.techshop.model.service.CollectionService;
import com.semidev.techshop.model.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminCollectionViewController {
    
    @GetMapping("/admin/collection/view")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int id,
        @RequestParam(name="page", required=false, defaultValue="1") int currentPage
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                model.addAttribute("title", "View Collection");
                var collection = CollectionService.selectCollectionById(id);
                model.addAttribute("collection", collection);
                int productPerPage = 10;
                var maxPage = (int) Math.ceil((float) ProductService.selectCountProductInCollection(id) / productPerPage);
                model.addAttribute("previousPage", (1 < currentPage) ? (currentPage - 1) : null);
                model.addAttribute("nextPage", (currentPage < maxPage) ? (currentPage + 1) : null);
                var productList = ProductService.selectProductInCollectionOrderByEditedDateDescLimitOffset(id, productPerPage, (currentPage - 1) * productPerPage);
                model.addAttribute("productList", productList);
                model.addAttribute("addProductInfo", session.getAttribute("addProductInfo"));
                session.setAttribute("addProductInfo", null);
                model.addAttribute("addProductError", session.getAttribute("addProductError"));
                session.setAttribute("addProductError", null);
                return "page/admin/collection/view.html";
            }
            catch (Exception exc) {
                model.addAttribute("indexProductError", "Failed connecting to database");
                return "page/admin/collection/view.html";
            }
        }
    }
    
}
