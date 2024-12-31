package com.semidev.techshop.controller.admin.brand;

import com.semidev.techshop.model.entity.Brand;
import com.semidev.techshop.model.service.BrandService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminBrandEditController {

    @GetMapping("/admin/brand/edit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="id", required=true) int id
    ) {
        if (session.getAttribute("admin_username") == null) {
            session.setAttribute("return_url", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            model.addAttribute("title", "Edit Brand");

            if (session.getAttribute("submitted_name") == null) {
                Brand brand = null;
                
                try {
                    brand = BrandService.selectBrandById(id);
                }
                catch (Exception exc) {
                    exc.printStackTrace();
                    model.addAttribute("edit_error", "Failed database connection");
                }
                
                model.addAttribute("brand", brand);
            }
            else {
                model.addAttribute("brand", null);
                model.addAttribute("submitted_id", session.getAttribute("submitted_id"));
                model.addAttribute("submitted_name", session.getAttribute("submitted_name"));
                model.addAttribute("submitted_image_url", session.getAttribute("submitted_image_url"));
                model.addAttribute("submitted_slug", session.getAttribute("submitted_slug"));
                model.addAttribute("edit_error", session.getAttribute("edit_error"));
            }

            return "page/admin/brand/edit.html";
        }
    }

}
