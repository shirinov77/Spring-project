package org.example.controller;

import org.example.entity.AuthUser;
import org.example.repository.AuthUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthUserDao authUserDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthUserDao authUserDao, PasswordEncoder passwordEncoder) {
        this.authUserDao = authUserDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "auth/logout";
    }

    @GetMapping("/text")
    public String textPage() {
        return "text";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("authUser", new AuthUser());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute AuthUser authUser, Model model) {
        if (authUserDao.existsByUsername(authUser.getUsername())) {
            model.addAttribute("error", "Bu username allaqachon mavjud!");
            return "auth/register";
        }

        String encodedPassword = passwordEncoder.encode(authUser.getPassword());
        authUser.setPassword(encodedPassword);

        authUserDao.save(authUser);
        return "redirect:/auth/login";
    }


}