package com.semidev.techshop.controller.admin.brand;

import com.semidev.techshop.model.service.BrandJoinProductService;
import com.semidev.techshop.model.service.BrandService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminBrandDeleteController {
    
    @GetMapping("/admin/brand/delete")
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
                int relatedProductCounter = BrandJoinProductService.selectCountBrandJoinProductByBrandId(id);
                if (relatedProductCounter != 0) {
                    session.setAttribute("deleteError", "There are some related products");
                    return "redirect:" + "/admin/brand";
                }
                else {
                    var record = BrandService.selectBrandById(id);
                    BrandService.deleteFromBrand(record);
                    model.addAttribute("brand", record);
                    return "page/admin/brand/delete.html";
                }
            }
            catch (Exception exc) {
                session.setAttribute("deleteError", "Failed deleting brand");
                return "redirect:" + "/admin/brand";
            }
        }
    }
    
}
