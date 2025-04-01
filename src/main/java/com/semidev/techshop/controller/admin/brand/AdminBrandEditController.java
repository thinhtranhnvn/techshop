package com.semidev.techshop.controller.admin.brand;

import com.semidev.techshop.exception.ExceptionInvalidBrandEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidBrandEditedDate;
import com.semidev.techshop.exception.ExceptionInvalidBrandId;
import com.semidev.techshop.exception.ExceptionInvalidBrandImageURL;
import com.semidev.techshop.exception.ExceptionInvalidBrandName;
import com.semidev.techshop.exception.ExceptionInvalidBrandSlug;
import com.semidev.techshop.model.entity.Brand;
import com.semidev.techshop.model.service.BrandService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;


@Controller
public class AdminBrandEditController {

    @GetMapping("/admin/brand/edit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int id
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            model.addAttribute("title", "Edit Brand");
            if (session.getAttribute("submittedName") == null) {
                try {
                    var brand = BrandService.selectBrandById(id);
                    model.addAttribute("brand", brand);
                }
                catch (ExceptionInvalidBrandEditedBy
                        | ExceptionInvalidBrandEditedDate
                        | ExceptionInvalidBrandId
                        | ExceptionInvalidBrandImageURL
                        | ExceptionInvalidBrandName
                        | ExceptionInvalidBrandSlug
                        | SQLException
                        exc
                ) {
                    model.addAttribute("editError", "Failed database connection");
                }
            }
            else {
                model.addAttribute("editError", session.getAttribute("editError"));
                session.setAttribute("editError", null);
                model.addAttribute("submittedId", session.getAttribute("submittedId"));
                session.setAttribute("submittedId", null);
                model.addAttribute("submittedName", session.getAttribute("submittedName"));
                session.setAttribute("submittedName", null);
                model.addAttribute("submittedImageURL", session.getAttribute("submittedImageURL"));
                session.setAttribute("submittedImageURL", null);
                model.addAttribute("submittedSlug", session.getAttribute("submittedSlug"));
                session.setAttribute("submittedSlug", null);
            }
            return "page/admin/brand/edit.html";
        }
    }

}
