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
public class AdminBrandEditSubmitController {

    @PostMapping("/admin/brand/edit-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int id,
        @RequestParam(name="name", required=true) String name,
        @RequestParam(name="image-url", required=true) String imageURL,
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
                session.setAttribute("submittedImageURL", imageURL);
                session.setAttribute("submittedSlug", slug);
                var matchedSlugRecord = BrandService.selectBrandBySlug(slug);
                if (matchedSlugRecord == null || matchedSlugRecord.getId() == id) {
                    var editedDate = LocalDateTime.now();
                    var editedBy = (String) session.getAttribute("adminUsername");
                    var record = Brand.createInstance(id, name, imageURL, slug, editedDate, editedBy);
                    BrandService.updateBrand(record);
                    session.setAttribute("editInfo", "Successfully updated brand");
                    session.setAttribute("submittedId", null);
                    session.setAttribute("submittedName", null);
                    session.setAttribute("submittedImageURL", null);
                    session.setAttribute("submittedSlug", null);
                    session.setAttribute("editError", null);
                    return "redirect:" + "/admin/brand";
                }
                else {
                    session.setAttribute("editError", "Slug already existed");
                    return "redirect:" + "/admin/brand/edit?id=" + id;
                }
            }
            catch (SQLException exc) {
                session.setAttribute("editError", "Failed to update brand");
                return "redirect:" + "/admin/brand/edit?id=" + id;
            }
            catch (ExceptionInvalidBrandId exc) {
                session.setAttribute("editError", "Invalid brand id");
                return "redirect:" + "/admin/brand/edit?id=" + id;
            }
            catch (ExceptionInvalidBrandName exc) {
                session.setAttribute("editError", "Invalid brand name");
                return "redirect:" + "/admin/brand/edit?id=" + id;
            }
            catch (ExceptionInvalidBrandImageURL exc) {
                session.setAttribute("editError", "Invalid image URL");
                return "redirect:" + "/admin/brand/edit?id=" + id;
            }
            catch (ExceptionInvalidBrandSlug exc) {
                session.setAttribute("editError", "Invalid brand slug");
                return "redirect:" + "/admin/brand/edit?id=" + id;
            }
            catch (ExceptionInvalidBrandEditedDate exc) {
                session.setAttribute("editError", "Invalid edited date");
                return "redirect:" + "/admin/brand/edit?id=" + id;
            }
            catch (ExceptionInvalidBrandEditedBy exc) {
                session.setAttribute("editError", "Invalid editor");
                return "redirect:" + "/admin/brand/edit?id=" + id;
            }
        }
    }

}