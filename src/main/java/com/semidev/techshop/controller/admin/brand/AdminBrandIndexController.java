package com.semidev.techshop.controller.admin.brand;

import com.semidev.techshop.model.entity.Brand;
import com.semidev.techshop.model.service.BrandService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;


@Controller
public class AdminBrandIndexController {

    @GetMapping("/admin/brand")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="page", required=false, defaultValue="1") String page
    ) {
        if (session.getAttribute("admin_username") == null) {
            session.setAttribute("return_url", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                int brandPerPage = 10;
                int maxPage = (int) Math.ceil((double) BrandService.selectCountAllBrand() / brandPerPage);
                int currentPage = Integer.parseInt(page);

                if (currentPage < 0 || maxPage < currentPage) {
                    model.addAttribute("index_error", "Invalid page number");
                }
                else {
                    ArrayList<Brand> brandList = BrandService.selectBrandOrderByEditedDateDescLimitOffset(brandPerPage, (currentPage - 1) * brandPerPage);
                    model.addAttribute("brand_list", brandList);
                    model.addAttribute("previous_page", (1 < currentPage) ? (currentPage - 1) : null);
                    model.addAttribute("next_page", (currentPage < maxPage) ? (currentPage + 1) : null);
                }
            }
            catch (Exception exc) {
                exc.printStackTrace();
                model.addAttribute("index_error", "Failed connecting to database");
            }
            finally {
                model.addAttribute("title", "Brand index");
                model.addAttribute("index_error", null);
                return "page/admin/brand/index.html";
            }
        }
    }

}
