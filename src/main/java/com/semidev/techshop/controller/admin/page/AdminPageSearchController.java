package com.semidev.techshop.controller.admin.page;

import com.semidev.techshop.model.service.PageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminPageSearchController {
    
    @GetMapping("/admin/page/search")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="keywords", required=false, defaultValue="") String keywords,
        @RequestParam(name="page", required=false, defaultValue="1") int currentPage
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                model.addAttribute("title", "Search Page");
                model.addAttribute("keywords", keywords);
                int recordPerPage = 10;
                var maxPage = (int) Math.ceil((float) PageService.selectCountPageByTitleLike(keywords) / recordPerPage);
                model.addAttribute("previousPage", (1 < currentPage) ? (currentPage - 1) : null);
                model.addAttribute("nextPage", (currentPage < maxPage) ? (currentPage + 1) : null);
                if (currentPage < 0 || maxPage < currentPage) {
                    model.addAttribute("searchError", "Invalid page number");
                }
                else {
                    var pageList = PageService.selectPageByTitleLikeOrderByEditedDateDescLimitOffset(keywords, recordPerPage, (currentPage - 1) * recordPerPage);
                    model.addAttribute("pageList", pageList);
                }
                return "page/admin/page/search.html";
            }
            catch (Exception exc) {
                model.addAttribute("searchError", "Failed connecting database");
                return "page/admin/page/search.html";
            }
        }
    }
    
}
