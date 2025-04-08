package com.semidev.techshop.controller.admin.collection;

import com.semidev.techshop.model.service.ColProService;
import com.semidev.techshop.model.service.CollectionService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminCollectionDeleteController {
    
    @GetMapping("/admin/collection/delete")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int id
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                var record = CollectionService.selectCollectionById(id);
                ColProService.deleteFromColProByCollectionId(record.getId());
                CollectionService.deleteFromCollection(record);
                model.addAttribute("collection", record);
                return "page/admin/collection/delete.html";
            }
            catch (Exception exc) {
                session.setAttribute("deleteError", "Failed deleting collection");
                return "redirect:" + "/admin/collection";
            }
        }
    }
    
}
