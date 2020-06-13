package com.bikeshop.demo.controller;

import com.bikeshop.demo.model.User;
import com.bikeshop.demo.service.EmailService;
import com.bikeshop.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SecurityController {

    private UserService userService;
    private EmailService emailService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) { this.emailService = emailService; }

    @RequestMapping("login")
    public String login(){
        return "login";
    }

    // redirect after login depending on role
    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            redirectAttributes.addFlashAttribute("welcome", "Welcome "+ request.getUserPrincipal().getName());
            return "redirect:admin/adminhome";
        }
        redirectAttributes.addFlashAttribute("welcome", "Welcome "+ request.getUserPrincipal().getName());
        return "redirect:/";
    }

    @RequestMapping("register")
    public String register(Model model){
        model.addAttribute("newUser", new User());
        return "login";
    }

    @PostMapping("reg")
    public String registerSubmit(@ModelAttribute User user, Model model, RedirectAttributes r){
        logger.info("New user!");
        logger.debug(user.getUsername());
        logger.debug(user.getPassword());
        String validData = userService.registerUser(user);

        switch (validData) {
            case "ok":
                r.addFlashAttribute("userInfo", "Registration success! Please log in!");
                emailService.sendMessage(user.getUsername(), user.getPassword());
                break;
            case "wrongUsernameAndEmail":
                model.addAttribute("userInfo", "Username already taken! Email address already registered!");
                model.addAttribute("newUser", new User());
                return "login";
            case "wrongUsername":
                model.addAttribute("userInfo", "Username is already taken");
                model.addAttribute("newUser", new User());
                return "login";
            case "wrongEmail":
                model.addAttribute("userInfo", "The provided email address is already registered!");
                model.addAttribute("newUser", new User());
                return "login";
        }
        return "redirect:/login";
    }


}


