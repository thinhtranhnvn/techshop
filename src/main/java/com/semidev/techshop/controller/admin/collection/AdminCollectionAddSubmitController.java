package com.semidev.techshop.controller.admin.collection;

import com.semidev.techshop.model.entity.Collection;
import com.semidev.techshop.model.service.CollectionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;


@Controller
public class AdminCollectionAddSubmitController {
    
    @PostMapping("/admin/collection/add-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="name", required=true) String name,
        @RequestParam(name="slug", required=true) String slug,
        @RequestParam(name="priority", required=true) int priority
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                session.setAttribute("submittedName", name);
                session.setAttribute("submittedSlug", slug);
                session.setAttribute("submittedPriority", priority);
                var matchedSlugCollection = CollectionService.selectCollectionBySlug(slug);
                if (matchedSlugCollection == null) {
                    var id = CollectionService.selectLatestCollectionId() + 1;
                    var editedDate = LocalDateTime.now();
                    var editedBy = (String) session.getAttribute("adminUsername");
                    var record = Collection.createInstance(id, name, slug, priority, editedDate, editedBy);
                    CollectionService.insertIntoCollection(record);
                    session.setAttribute("addInfo", "Successfully added collection");
                    session.setAttribute("submittedName", null);
                    session.setAttribute("submittedSlug", null);
                    session.setAttribute("submittedPriority", null);
                    return "redirect:" + "/admin/collection";
                }
                else {
                    session.setAttribute("addError", "Slug already existed");
                    return "redirect:" + "/admin/collection/add";
                }
            }
            catch (Exception exc) {
                session.setAttribute("addError", "Failed adding collection");
                return "redirect:" + "/admin/collection/add";
            }
        }
    }
    
}
