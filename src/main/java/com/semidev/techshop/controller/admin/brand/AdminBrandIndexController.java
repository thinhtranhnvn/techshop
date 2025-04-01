package com.semidev.techshop.controller.admin.brand;

import com.semidev.techshop.exception.ExceptionInvalidBrandEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidBrandEditedDate;
import com.semidev.techshop.exception.ExceptionInvalidBrandId;
import com.semidev.techshop.exception.ExceptionInvalidBrandImageURL;
import com.semidev.techshop.exception.ExceptionInvalidBrandName;
import com.semidev.techshop.exception.ExceptionInvalidBrandSlug;
import com.semidev.techshop.model.service.BrandService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;


@Controller
public class AdminBrandIndexController {

    @GetMapping({
        "/admin/brand",
        "/admin/brand/"
    })
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="page", required=false, defaultValue="1") int currentPage
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                model.addAttribute("title", "Brand index");
                int brandPerPage = 10;
                var maxPage = (int) Math.ceil((double) BrandService.selectCountAllBrand() / brandPerPage);
                model.addAttribute("previousPage", (1 < currentPage) ? (currentPage - 1) : null);
                model.addAttribute("nextPage", (currentPage < maxPage) ? (currentPage + 1) : null);
                if (currentPage < 0 || maxPage < currentPage) {
                    model.addAttribute("indexError", "Invalid page number");
                }
                else {
                    var brandList = BrandService.selectAllBrandOrderByEditedDateDescLimitOffset(brandPerPage, (currentPage - 1) * brandPerPage);
                    model.addAttribute("brandList", brandList);
                }
                model.addAttribute("addInfo", session.getAttribute("addInfo"));
                session.setAttribute("addInfo", null);
                model.addAttribute("editInfo", session.getAttribute("editInfo"));
                session.setAttribute("editInfo", null);
                model.addAttribute("deleteError", session.getAttribute("deleteError"));
                session.setAttribute("deleteError", null);
                model.addAttribute("indexError", null);
                return "page/admin/brand/index.html";
            }
            catch (Exception exc) {
                model.addAttribute("indexError", "Failed connecting to database");
                return "page/admin/brand/index.html";
            }
        }
    }

}
