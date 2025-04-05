package com.semidev.techshop.controller.admin.category;

import com.semidev.techshop.model.entity.Category;
import com.semidev.techshop.model.service.CategoryService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;


@Controller
public class AdminCategoryEditSubmitController {
    
    @PostMapping("/admin/category/edit-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int id,
        @RequestParam(name="name", required=true) String name,
        @RequestParam(name="slug", required=true) String slug
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                session.setAttribute("submittedId", id);
                session.setAttribute("submittedName", name);
                session.setAttribute("submittedSlug", slug);
                var matchedSlugRecord = CategoryService.selectCategoryBySlug(slug);
                if (matchedSlugRecord == null || matchedSlugRecord.getId() == id) {
                    var editedDate = LocalDateTime.now();
                    var editedBy = (String) session.getAttribute("adminUsername");
                    var record = Category.createInstance(id, name, slug, editedDate, editedBy);
                    CategoryService.updateCategory(record);
                    session.setAttribute("editInfo", "Successfully updated category");
                    session.setAttribute("submittedId", null);
                    session.setAttribute("submittedName", null);
                    session.setAttribute("submittedSlug", null);
                    return "redirect:" + "/admin/category";
                }
                else {
                    session.setAttribute("editError", "Slug already existed");
                    return "redirect:" + "/admin/category/edit?id=" + id;
                }
            }
            catch (Exception exc) {
                session.setAttribute("editError", "Failed to update category");
                return "redirect:" + "/admin/category/edit?id=" + id;
            }
        }
    }
    
}
