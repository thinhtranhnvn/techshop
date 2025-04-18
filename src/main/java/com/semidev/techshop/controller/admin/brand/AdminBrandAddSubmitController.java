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
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                session.setAttribute("submittedName", name);
                session.setAttribute("submittedImageURL", imageURL);
                session.setAttribute("submittedSlug", slug);
                var matchedSlugRecord = BrandService.selectBrandBySlug(slug);
                if (matchedSlugRecord == null) {
                    var id = BrandService.selectLatestBrandId() + 1;
                    var editedDate = LocalDateTime.now();
                    var editedBy = (String) session.getAttribute("adminUsername");
                    var record = Brand.createInstance(id, name, imageURL, slug, editedDate, editedBy);
                    BrandService.insertIntoBrand(record);
                    session.setAttribute("addInfo", "Successfully added brand");
                    session.setAttribute("submittedName", null);
                    session.setAttribute("submittedImageURL", null);
                    session.setAttribute("submittedSlug", null);
                    session.setAttribute("addError", null);
                    return "redirect:" + "/admin/brand";
                }
                else {
                    session.setAttribute("addError", "Slug already existed");
                    return "redirect:" + "/admin/brand/add";
                }
            }
            catch (SQLException exc) {
                session.setAttribute("addError", "Failed to add brand");
                return "redirect:" + "/admin/brand/add";
            }
            catch (ExceptionInvalidBrandId exc) {
                session.setAttribute("addError", "Invalid brand id");
                return "redirect:" + "/admin/brand/add";
            }
            catch (ExceptionInvalidBrandName exc) {
                session.setAttribute("addError", "Invalid brand name");
                return "redirect:" + "/admin/brand/add";
            }
            catch (ExceptionInvalidBrandImageURL exc) {
                session.setAttribute("addError", "Invalid image URL");
                return "redirect:" + "/admin/brand/add";
            }
            catch (ExceptionInvalidBrandSlug exc) {
                session.setAttribute("addError", "Invalid brand slug");
                return "redirect:" + "/admin/brand/add";
            }
            catch (ExceptionInvalidBrandEditedBy exc) {
                session.setAttribute("addError", "Invalid editor");
                return "redirect:" + "/admin/brand/add";
            }
            catch (ExceptionInvalidBrandEditedDate exc) {
                session.setAttribute("addError", "Invalid edited date");
                return "redirect:" + "/admin/brand/add";
            }
        }
    }

}
