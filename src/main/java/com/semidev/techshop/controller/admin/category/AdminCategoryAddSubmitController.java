package com.semidev.techshop.controller.admin.category;

import com.semidev.techshop.exception.ExceptionInvalidCategoryEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidCategoryId;
import com.semidev.techshop.exception.ExceptionInvalidCategoryName;
import com.semidev.techshop.exception.ExceptionInvalidCategorySlug;
import com.semidev.techshop.exception.ExceptionNullCategoryEditedDate;
import com.semidev.techshop.model.entity.Category;
import com.semidev.techshop.model.service.CategoryService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;

import java.time.LocalDateTime;


@Controller
public class AdminCategoryAddSubmitController {

    @PostMapping("/admin/category/add-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="name", required=true) String name,
        @RequestParam(name="slug", required=true) String slug
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                session.setAttribute("submittedName", name);
                session.setAttribute("submittedSlug", slug);
                var matchedSlugCategory = CategoryService.selectCategoryBySlug(slug);
                if (matchedSlugCategory == null) {
                    var id = CategoryService.selectLatestCategoryId() + 1;
                    var editedDate = LocalDateTime.now();
                    var editedBy = (String) session.getAttribute("adminUsername");
                    var record = Category.createInstance(id, name, slug, editedDate, editedBy);
                    CategoryService.insertIntoCategory(record);
                    session.setAttribute("addInfo", "Successfully added category");
                    session.setAttribute("submittedName", null);
                    session.setAttribute("submittedSlug", null);
                    return "redirect:" + "/admin/category";
                }
                else {
                    session.setAttribute("addError", "Slug already existed");
                    return "redirect:" + "/admin/category/add";
                }
            }
            catch (ExceptionInvalidCategoryEditedBy exc) {
                session.setAttribute("addError", "Invalid category edited-by");
                return "redirect:" + "/admin/category/add";
            }
            catch (ExceptionInvalidCategoryId exc) {
                session.setAttribute("addError", "Invalid category id");
                return "redirect:" + "/admin/category/add";
            }
            catch (ExceptionInvalidCategoryName exc) {
                session.setAttribute("addError", "Invalid category name");
                return "redirect:" + "/admin/category/add";
            }
            catch (ExceptionInvalidCategorySlug exc) {
                session.setAttribute("addError", "Invalid category slug");
                return "redirect:" + "/admin/category/add";
            }
            catch (ExceptionNullCategoryEditedDate exc) {
                session.setAttribute("addError", "Category edited-date cannot be null");
                return "redirect:" + "/admin/category/add";
            }
            catch (SQLException exc) {
                session.setAttribute("addError", "Failed database connection");
                return "redirect:" + "/admin/category/add";
            }
        }
    }
    
}
