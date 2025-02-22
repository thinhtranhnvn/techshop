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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
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
            try {
                var matchedSlugRecord = BrandService.selectBrandBySlug(slug);
                if (matchedSlugRecord == null) {
                    /* do nothing */
                }
                else {
                    session.setAttribute("add_error", "Slug already existed");
                    return "redirect:" + "/admin/brand/add";
                }
                var id = BrandService.selectLatestBrandId(); id += 1;
                var editedDate = LocalDateTime.now();
                var editedBy = (String) session.getAttribute("admin_username");
                var record = Brand.createInstance(id, name, imageURL, slug, editedDate, editedBy);
                BrandService.insertIntoBrand(record);
                session.setAttribute("submitted_name", null);
                session.setAttribute("submitted_image_url", null);
                session.setAttribute("submitted_slug", null);
                session.setAttribute("add_error", null);
                return "redirect:" + "/admin/brand";
            }
            catch (SQLException exc) {
                session.setAttribute("add_error", "Failed to add brand");
                return "redirect:" + "/admin/brand/add";
            }
            catch (ExceptionInvalidBrandId exc) {
                session.setAttribute("add_error", "Invalid brand id");
                return "redirect:" + "/admin/brand/add";
            }
            catch (ExceptionInvalidBrandName exc) {
                session.setAttribute("add_error", "Invalid brand name");
                return "redirect:" + "/admin/brand/add";
            }
            catch (ExceptionInvalidBrandImageURL exc) {
                session.setAttribute("add_error", "Invalid image URL");
                return "redirect:" + "/admin/brand/add";
            }
            catch (ExceptionInvalidBrandSlug exc) {
                session.setAttribute("add_error", "Invalid brand slug");
                return "redirect:" + "/admin/brand/add";
            }
            catch (ExceptionInvalidBrandEditedBy exc) {
                session.setAttribute("add_error", "Invalid editor");
                return "redirect:" + "/admin/brand/add";
            }
            catch (ExceptionInvalidBrandEditedDate exc) {
                session.setAttribute("add_error", "Invalid edited date");
                return "redirect:" + "/admin/brand/add";
            }
        }
    }

}
