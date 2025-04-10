package com.semidev.techshop.controller.admin.page;

import com.semidev.techshop.exception.ExceptionInvalidPageContent;
import com.semidev.techshop.exception.ExceptionInvalidPageEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidPageId;
import com.semidev.techshop.exception.ExceptionInvalidPageMenuName;
import com.semidev.techshop.exception.ExceptionInvalidPagePriority;
import com.semidev.techshop.exception.ExceptionInvalidPageSlug;
import com.semidev.techshop.exception.ExceptionInvalidPageTitle;
import com.semidev.techshop.exception.ExceptionNullPageEditedDate;
import com.semidev.techshop.model.entity.Page;
import com.semidev.techshop.model.service.PageService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;
import java.time.LocalDateTime;


@Controller
public class AdminPageAddSubmitController {
    
    @PostMapping("/admin/page/add-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="title", required=true) String title,
        @RequestParam(name="menu-name", required=true) String menuName,
        @RequestParam(name="content", required=true) String content,
        @RequestParam(name="slug", required=true) String slug,
        @RequestParam(name="priority", required=true) int priority
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                session.setAttribute("submittedTitle", title);
                session.setAttribute("submittedMenuName", menuName);
                session.setAttribute("submittedContent", content);
                session.setAttribute("submittedSlug", slug);
                session.setAttribute("submittedPriority", priority);
                var matchedSlugRecord = PageService.selectPageBySlug(slug);
                if (matchedSlugRecord == null) {
                    var id = PageService.selectLatestPageId() + 1;
                    var editedDate = LocalDateTime.now();
                    var editedBy = (String) session.getAttribute("adminUsername");
                    var record = Page.createInstance(id, title, menuName, content, slug, priority, editedDate, editedBy);
                    PageService.insertIntoPage(record);
                    session.setAttribute("addInfo", "Successfully added page");
                    session.setAttribute("submittedTitle", null);
                    session.setAttribute("submittedMenuName", null);
                    session.setAttribute("submittedContent", null);
                    session.setAttribute("submittedSlug", null);
                    session.setAttribute("submittedPriority", null);
                    return "redirect:" + "/admin/page";
                }
                else {
                    session.setAttribute("addError", "Slug already existed");
                    return "redirect:" + "/admin/page/add";
                }
            }
            catch (ExceptionInvalidPageContent exc) {
                session.setAttribute("addError", "Invalid page content");
                return "redirect:" + "/admin/page/add";
            }
            catch (ExceptionInvalidPageEditedBy exc) {
                session.setAttribute("addError", "Invalid page edited-by");
                return "redirect:" + "/admin/page/add";
            }
            catch (ExceptionInvalidPageId exc) {
                session.setAttribute("addError", "Invalid page id");
                return "redirect:" + "/admin/page/add";
            }
            catch (ExceptionInvalidPageMenuName exc) {
                session.setAttribute("addError", "Invalid page menu name");
                return "redirect:" + "/admin/page/add";
            }
            catch (ExceptionInvalidPagePriority exc) {
                session.setAttribute("addError", "Invalid page priority");
                return "redirect:" + "/admin/page/add";
            }
            catch (ExceptionInvalidPageSlug exc) {
                session.setAttribute("addError", "Invalid page slug");
                return "redirect:" + "/admin/page/add";
            }
            catch (ExceptionInvalidPageTitle exc) {
                session.setAttribute("addError", "Invalid page title");
                return "redirect:" + "/admin/page/add";
            }
            catch (ExceptionNullPageEditedDate exc) {
                session.setAttribute("addError", "Page edited-date cannot be null");
                return "redirect:" + "/admin/page/add";
            }
            catch (SQLException exc) {
                session.setAttribute("addError", "Failed connecting database");
                return "redirect:" + "/admin/page/add";
            }
        }
    }
    
}
