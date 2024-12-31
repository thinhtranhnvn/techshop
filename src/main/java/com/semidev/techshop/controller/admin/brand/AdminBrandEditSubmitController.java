package com.semidev.techshop.controller.admin.brand;

import com.semidev.techshop.model.entity.Brand;
import com.semidev.techshop.model.service.BrandService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;


@Controller
public class AdminBrandEditSubmitController {

    @PostMapping("/admin/brand/edit/submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int id,
        @RequestParam(name="name", required=true) String name,
        @RequestParam(name="image-url", required=true) String imageURL,
        @RequestParam(name="slug", required=true) String slug
    ) {
        if (session.getAttribute("admin_username") == null) {
            session.setAttribute("return_url", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            session.setAttribute("submitted_id", id);
            session.setAttribute("submitted_name", name);
            session.setAttribute("submitted_image_url", imageURL);
            session.setAttribute("submitted_slug", slug);

            try {
                Brand matchedSlugRecord = BrandService.selectBrandBySlug(slug);
                
                if (matchedSlugRecord == null || matchedSlugRecord.getId() == id) {
                    /* do nothing */;
                }
                else {
                    session.setAttribute("edit_error", "Slug already existed");
                    return "redirect:" + "/admin/brand/edit?id=" + id;
                }
            }
            catch (Exception exc) {
                exc.printStackTrace();
                session.setAttribute("edit_error", "Failed to update brand");
                return "redirect:" + "/admin/brand/edit?id=" + id;
            }

            Brand record = null;

            try {
                LocalDateTime editedDate = LocalDateTime.now();
                String editedBy = (String) session.getAttribute("admin_username");
                record = Brand.createInstance(id, name, imageURL, slug, editedDate, editedBy);
            }
            catch (Exception exc) {
                exc.printStackTrace();
                session.setAttribute("edit_error", exc.getMessage());
                return "redirect:" + "/admin/brand/edit?id=" + id;
            }

            try {
                BrandService.updateBrand(record);
                session.setAttribute("submitted_id", null);
                session.setAttribute("submitted_name", null);
                session.setAttribute("submitted_image_url", null);
                session.setAttribute("submitted_slug", null);
                session.setAttribute("edit_error", null);
                return "redirect:" + "/admin/brand";
            }
            catch (Exception exc) {
                exc.printStackTrace();
                session.setAttribute("edit_error", "Failed to update brand");
                return "redirect:" + "/admin/brand/edit?id=" + id;
            }
        }
    }

}