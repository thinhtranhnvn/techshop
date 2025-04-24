package com.semidev.techshop.controller;

import com.semidev.techshop.model.entity.Category;
import com.semidev.techshop.model.entity.Page;
import com.semidev.techshop.model.entity.Slide;
import com.semidev.techshop.model.service.CategoryService;
import com.semidev.techshop.model.service.CollectionJoinProductJoinImageService;
import com.semidev.techshop.model.service.PageService;
import com.semidev.techshop.model.service.SlideService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;


@Controller
public class IndexController {

    @GetMapping({
        "",
        "/"
    })
    public String accept(HttpServletRequest request, HttpSession session, Model model) {
        try {
            model.addAttribute("title", "Homepage");
            model.addAttribute("visitorUsername", session.getAttribute("visitorUsername"));
            var categoryList = CategoryService.selectAllCategoryOrderByNameAsc();
            model.addAttribute("categoryList", categoryList);
            var pageList = PageService.selectAllPageOrderByPriorityDesc();
            model.addAttribute("pageList", pageList);
            var slideList = SlideService.selectAllSlideOrderByPriorityDesc();
            model.addAttribute("slideList", slideList);
            var joinedCollectionList = CollectionJoinProductJoinImageService.selectAllCollectionJoinProductJoinImageOrderByCollectionPriorityDesc();
            model.addAttribute("joinedCollectionList", joinedCollectionList);
        }
        catch (Exception exc) {
            model.addAttribute("categoryList", new ArrayList<Category>());
            model.addAttribute("pageList", new ArrayList<Page>());
            model.addAttribute("slideList", new ArrayList<Slide>());
            model.addAttribute("joinedCollectionList", null);
        }
        return "page/index.html";
    }
    
}
