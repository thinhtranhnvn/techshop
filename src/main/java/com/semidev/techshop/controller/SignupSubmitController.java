package com.semidev.techshop.controller;

import com.semidev.techshop.exception.ExceptionInvalidVisitorAddress;
import com.semidev.techshop.exception.ExceptionInvalidVisitorEmail;
import com.semidev.techshop.exception.ExceptionInvalidVisitorFullname;
import com.semidev.techshop.exception.ExceptionInvalidVisitorId;
import com.semidev.techshop.exception.ExceptionInvalidVisitorPassword;
import com.semidev.techshop.exception.ExceptionInvalidVisitorPhone;
import com.semidev.techshop.exception.ExceptionInvalidVisitorUsername;
import com.semidev.techshop.model.entity.Visitor;
import com.semidev.techshop.model.service.VisitorService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;


@Controller
public class SignupSubmitController {
    
    @PostMapping("/signup-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="fullname", required=true) String fullname,
        @RequestParam(name="username", required=true) String username,
        @RequestParam(name="password", required=true) String password,
        @RequestParam(name="phone", required=true) String phone,
        @RequestParam(name="email", required=true) String email,
        @RequestParam(name="address", required=true) String address
    ) {
        session.setAttribute("submittedFullname", fullname);
        session.setAttribute("submittedUsername", username);
        session.setAttribute("submittedPassword", password);
        session.setAttribute("submittedPhone", phone);
        session.setAttribute("submittedEmail", email);
        session.setAttribute("submittedAddress", address);
        try {
            var matchedUsernameRecord = VisitorService.selectVisitorByUsername(username);
            if (matchedUsernameRecord == null) {
                var id = VisitorService.selectLatestVisitorId() + 1;
                var record = Visitor.createInstance(id, fullname, username, password, phone, email, address);
                VisitorService.insertIntoVisitor(record);
                session.setAttribute("submittedFullname", null);
                session.setAttribute("submittedUsername", null);
                session.setAttribute("submittedPassword", null);
                session.setAttribute("submittedPhone", null);
                session.setAttribute("submittedEmail", null);
                session.setAttribute("submittedAddress", null);
                return "redirect:" + "/login";
            }
            else {
                session.setAttribute("signupError", "Username already existed");
                return "redirect:" + "/signup";
            }
        }
        catch (ExceptionInvalidVisitorAddress exc) {
            session.setAttribute("signupError", "Invalid visitor address");
            return "redirect:" + "/signup";
        }
        catch (ExceptionInvalidVisitorEmail exc) {
            session.setAttribute("signupError", "Invalid visitor email");
            return "redirect:" + "/signup";
        }
        catch (ExceptionInvalidVisitorFullname exc) {
            session.setAttribute("signupError", "Invalid visitor fullname");
            return "redirect:" + "/signup";
        }
        catch (ExceptionInvalidVisitorId exc) {
            session.setAttribute("signupError", "Invalid visitor id");
            return "redirect:" + "/signup";
        }
        catch (ExceptionInvalidVisitorPassword exc) {
            session.setAttribute("signupError", "Invalid visitor password");
            return "redirect:" + "/signup";
        }
        catch (ExceptionInvalidVisitorUsername exc) {
            session.setAttribute("signupError", "Invalid visitor username");
            return "redirect:" + "/signup";
        }
        catch (ExceptionInvalidVisitorPhone ex) {
            session.setAttribute("signupError", "Invalid visitor phone");
            return "redirect:" + "/signup";
        }
        catch (SQLException exc) {
            session.setAttribute("signupError", "Failed connecting to database");
            return "redirect:" + "/signup";
        }
    }
    
}
