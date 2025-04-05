package com.semidev.techshop.controller.admin.category;

import com.semidev.techshop.model.service.CatProService;
import com.semidev.techshop.model.service.CategoryService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminCategoryDeleteController {

    @GetMapping("/admin/category/delete")
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
                var record = CategoryService.selectCategoryById(id);
                CatProService.deleteFromCatProByCategoryId(record.getId());
                CategoryService.deleteFromCategory(record);
                model.addAttribute("category", record);
                return "page/admin/category/delete.html";
            }
            catch (Exception exc) {
                session.setAttribute("deleteError", "Failed deleting category");
                return "redirect:" + "/admin/category";
            }
        }
    }
    
}
