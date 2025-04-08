package com.semidev.techshop.controller.admin.collection;

import com.semidev.techshop.exception.ExceptionInvalidCollectionEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidCollectionId;
import com.semidev.techshop.exception.ExceptionInvalidCollectionName;
import com.semidev.techshop.exception.ExceptionInvalidCollectionPriority;
import com.semidev.techshop.exception.ExceptionInvalidCollectionSlug;
import com.semidev.techshop.exception.ExceptionNullCollectionEditedDate;
import com.semidev.techshop.model.entity.Collection;
import com.semidev.techshop.model.service.CollectionService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;

import java.time.LocalDateTime;


@Controller
public class AdminCollectionEditSubmitController {

    @PostMapping("/admin/collection/edit-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int id,
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
                session.setAttribute("submittedId", id);
                session.setAttribute("submittedName", name);
                session.setAttribute("submittedSlug", slug);
                session.setAttribute("prioritySlug", priority);
                var matchedSlugRecord = CollectionService.selectCollectionBySlug(slug);
                if (matchedSlugRecord == null || matchedSlugRecord.getId() == id) {
                    var editedDate = LocalDateTime.now();
                    var editedBy = (String) session.getAttribute("adminUsername");
                    var record = Collection.createInstance(id, name, slug, priority, editedDate, editedBy);
                    CollectionService.updateCollection(record);
                    session.setAttribute("editInfo", "Successfully updated collection");
                    session.setAttribute("submittedId", null);
                    session.setAttribute("submittedName", null);
                    session.setAttribute("submittedSlug", null);
                    session.setAttribute("submittedPriority", null);
                    return "redirect:" + "/admin/collection";
                }
                else {
                    session.setAttribute("editError", "Slug already existed");
                    return "redirect:" + "/admin/collection/edit?id=" + id;
                }
            }
            catch (ExceptionInvalidCollectionEditedBy exc) {
                session.setAttribute("editError", "Invalid collection edited-by");
                return "redirect:" + "/admin/collection/edit?id=" + id;
            }
            catch (ExceptionInvalidCollectionId exc) {
                session.setAttribute("editError", "Invalid collection id");
                return "redirect:" + "/admin/collection/edit?id=" + id;
            }
            catch (ExceptionInvalidCollectionName exc) {
                session.setAttribute("editError", "Invalid collection name");
                return "redirect:" + "/admin/collection/edit?id=" + id;
            }
            catch (ExceptionInvalidCollectionPriority exc) {
                session.setAttribute("editError", "Invalid collection priority");
                return "redirect:" + "/admin/collection/edit?id=" + id;
            }
            catch (ExceptionInvalidCollectionSlug exc) {
                session.setAttribute("editError", "Invalid collection slug");
                return "redirect:" + "/admin/collection/edit?id=" + id;
            }
            catch (ExceptionNullCollectionEditedDate exc) {
                session.setAttribute("editError", "Collection edited-date cannot be null");
                return "redirect:" + "/admin/collection/edit?id=" + id;
            }
            catch (SQLException exc) {
                session.setAttribute("editError", "Failed updating collection");
                return "redirect:" + "/admin/collection/edit?id=" + id;
            }
        }
    }
    
}
