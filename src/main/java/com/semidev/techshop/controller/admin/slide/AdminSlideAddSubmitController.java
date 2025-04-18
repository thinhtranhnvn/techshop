package com.semidev.techshop.controller.admin.slide;

import com.semidev.techshop.model.entity.Slide;
import com.semidev.techshop.model.service.SlideService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;


@Controller
public class AdminSlideAddSubmitController {
    
    @PostMapping("/admin/slide/add-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
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
                session.setAttribute("submittedImageURL", imageURL);
                session.setAttribute("submittedCaption", caption);
                session.setAttribute("submittedHref", href);
                session.setAttribute("submittedPriority", priority);
                var id = SlideService.selectLatestSlideId() + 1;
                var editedDate = LocalDateTime.now();
                var editedBy = (String) session.getAttribute("adminUsername");
                var record = Slide.createInstance(id, imageURL, caption, href, priority, editedDate, editedBy);
                SlideService.insertIntoSlide(record);
                session.setAttribute("addInfo", "Successfully added slide");
                session.setAttribute("submittedImageURL", null);
                session.setAttribute("submittedCaption", null);
                session.setAttribute("submittedHref", null);
                session.setAttribute("submittedPriority", null);
                return "redirect:" + "/admin/slide";
            }
            catch (Exception exc) {
                session.setAttribute("addError", "Failed adding slide");
                return "redirect:" + "/admin/slide/add";
            }
        }
    }
    
}
