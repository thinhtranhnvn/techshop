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
public class AdminCollectionProductSearchController {
    
    @GetMapping("/admin/collection/product-search")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="collection-id", required=true) int collectionId,
        @RequestParam(name="page", required=false, defaultValue="1") int currentPage,
        @RequestParam(name="keywords", required=true) String keywords
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                model.addAttribute("title", "Search Collection");
                model.addAttribute("keywords", keywords);
                var collection = CollectionService.selectCollectionById(collectionId);
                model.addAttribute("collection", collection);
                int productPerPage = 10;
                var maxPage = (int) Math.ceil((float) ProductService.selectCountProductByNameLikeInCollection(keywords, collectionId) / productPerPage);
                model.addAttribute("previousPage", (1 < currentPage) ? (currentPage - 1) : null);
                model.addAttribute("nextPage", (currentPage < maxPage) ? (currentPage + 1) : null);
                var productList = ProductService.selectProductByNameLikeInCollectionOrderByEditedDateDescLimitOffset(keywords, collectionId, productPerPage, (currentPage - 1) * productPerPage);
                model.addAttribute("productList", productList);
                model.addAttribute("addProductInfo", session.getAttribute("addProductInfo"));
                session.setAttribute("addProductInfo", null);
                model.addAttribute("addProductError", session.getAttribute("addProductError"));
                session.setAttribute("addProductError", null);
                return "page/admin/collection/product-search.html";
            }
            catch (Exception exc) {
                model.addAttribute("indexProductError", "Failed connecting to database");
                return "page/admin/category/product-search.html";
            }
        }
    }
    
}
