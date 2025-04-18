package com.semidev.techshop.controller.admin.slide;

import com.semidev.techshop.model.service.SlideService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminSlideIndexController {
    
    @GetMapping({
        "/admin/slide",
        "/admin/slide/"
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
                model.addAttribute("title", "Slide Index");
                int slidePerPage = 10;
                var maxPage = (int) Math.ceil((float) SlideService.selectCountAllSlide() / slidePerPage);
                model.addAttribute("previousPage", (1 < currentPage) ? (currentPage - 1) : null);
                model.addAttribute("nextPage", (currentPage < maxPage) ? (currentPage + 1) : null);
                if (currentPage < 0 || maxPage < currentPage) {
                    model.addAttribute("indexError", "Invalid page number");
                }
                else {
                    var slideList = SlideService.selectAllSlideOrderByEditedDateLimitOffset(slidePerPage, (currentPage - 1) * slidePerPage);
                    model.addAttribute("slideList", slideList);
                }
                model.addAttribute("addInfo", session.getAttribute("addInfo"));
                session.setAttribute("addInfo", null);
                model.addAttribute("editInfo", session.getAttribute("editInfo"));
                session.setAttribute("editInfo", null);
                return "page/admin/slide/index.html";
            }
            catch (Exception exc) {
                model.addAttribute("indexError", "Failed connecting to database");
                return "page/admin/slide/index.html";
            }
        }
    }
    
}
