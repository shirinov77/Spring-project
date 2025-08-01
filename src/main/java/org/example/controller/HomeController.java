package org.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/text")
    public ModelAndView text() {
        String username = getLoggedInUsername();
        ModelAndView modelAndView = new ModelAndView("text");
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView admin() {
        String username = getLoggedInUsername();
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    private String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
