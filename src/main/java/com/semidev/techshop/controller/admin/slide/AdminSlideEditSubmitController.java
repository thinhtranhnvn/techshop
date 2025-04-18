package com.semidev.techshop.controller.admin.slide;

import com.semidev.techshop.exception.ExceptionInvalidSlideCaption;
import com.semidev.techshop.exception.ExceptionInvalidSlideEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidSlideId;
import com.semidev.techshop.exception.ExceptionInvalidSlideImageURL;
import com.semidev.techshop.exception.ExceptionInvalidSlidePriority;
import com.semidev.techshop.exception.ExceptionNullSlideEditedDate;
import com.semidev.techshop.exception.ExceptionNullSlideHref;
import com.semidev.techshop.model.entity.Slide;
import com.semidev.techshop.model.service.SlideService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;
import java.time.LocalDateTime;


@Controller
public class AdminSlideEditSubmitController {
    
    @PostMapping("/admin/slide/edit-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int id,
        @RequestParam(name="image-url", required=true) String imageURL,
        @RequestParam(name="caption", required=true) String caption,
        @RequestParam(name="href", required=true) String href,
        @RequestParam(name="priority", required=true) int priority
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                var editedDate = LocalDateTime.now();
                var editedBy = (String) session.getAttribute("adminUsername");
                var record = Slide.createInstance(id, imageURL, caption, href, priority, editedDate, editedBy);
                SlideService.updateSlide(record);
                session.setAttribute("editInfo", "Successfully updated slide");
                return "redirect:" + "/admin/slide";
            }
            catch (ExceptionInvalidSlideCaption exc) {
                session.setAttribute("editError", "Invalid slide caption");
                return "redirect:" + "/admin/slide/edit?id=" + id;
            }
            catch (ExceptionInvalidSlideEditedBy exc) {
                session.setAttribute("editError", "Invalid slide edited-by");
                return "redirect:" + "/admin/slide/edit?id=" + id;
            }
            catch (ExceptionInvalidSlideId exc) {
                session.setAttribute("editError", "Invalid slide id");
                return "redirect:" + "/admin/slide/edit?id=" + id;
            }
            catch (ExceptionInvalidSlideImageURL exc) {
                session.setAttribute("editError", "Invalid slide image URL");
                return "redirect:" + "/admin/slide/edit?id=" + id;
            }
            catch (ExceptionInvalidSlidePriority exc) {
                session.setAttribute("editError", "Invalid slide priority");
                return "redirect:" + "/admin/slide/edit?id=" + id;
            }
            catch (ExceptionNullSlideEditedDate exc) {
                session.setAttribute("editError", "Slide edited-date cannot be null");
                return "redirect:" + "/admin/slide/edit?id=" + id;
            }
            catch (ExceptionNullSlideHref exc) {
                session.setAttribute("editError", "Slide href cannot be null");
                return "redirect:" + "/admin/slide/edit?id=" + id;
            }
            catch (SQLException exc) {
                session.setAttribute("editError", "Failed updating slide");
                return "redirect:" + "/admin/slide/edit?id=" + id;
            }
        }
    }
    
}
