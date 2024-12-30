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
public class AdminBrandAddSubmitController {

    @PostMapping("/admin/brand/add-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="name", required=true) String name,
        @RequestParam(name="image-url", required=true) String imageURL,
        @RequestParam(name="slug", required=true) String slug
    ) {
        if (session.getAttribute("admin_username") == null) {
            session.setAttribute("return_url", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            session.setAttribute("submitted_name", name);
            session.setAttribute("submitted_image_url", imageURL);
            session.setAttribute("submitted_slug", slug);

            Brand record = null;

            try {
                int id = BrandService.selectLatestBrandId(); id += 1;
                LocalDateTime editedDate = LocalDateTime.now();
                String editedBy = (String) session.getAttribute("admin_username");
                record = Brand.createInstance(id, name, imageURL, slug, editedDate, editedBy);
            }
            catch (Exception exc) {
                exc.printStackTrace();
                session.setAttribute("add_error", exc.getMessage());
                return "redirect:" + "/admin/brand/add";
            }

            try {
                Brand matchedSlugRecord = BrandService.selectBrandBySlug(slug);
                if (matchedSlugRecord == null) {
                    /* do nothing */;
                }
                else {
                    session.setAttribute("add_error", "Slug already existed");
                    return "redirect:" + "/admin/brand/add";
                }
            }
            catch (Exception exc) {
                exc.printStackTrace();
                session.setAttribute("add_error", "Failed to add brand");
                return "redirect:" + "/admin/brand/add";
            }

            try {
                BrandService.insertIntoBrand(record);
                session.setAttribute("submitted_name", null);
                session.setAttribute("submitted_image_url", null);
                session.setAttribute("submitted_slug", null);
                session.setAttribute("add_error", null);
                return "redirect:" + "/admin/brand";
            }
            catch (Exception exc) {
                exc.printStackTrace();
                session.setAttribute("add_error", "Failed to add brand");
                return "redirect:" + "/admin/brand/add";
            }
        }
    }

}
