
package com.example.patientapp.controller;

import com.example.patientapp.model.User;
import com.example.patientapp.service.UserService;  // Make sure to import UserService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @Autowired
    private UserService userService;  // Autowire the UserService

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";  // Make sure you have a login.html template
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());  // Create a new User object
        return "register";  // Return the register.html view
    }

    @PostMapping("/register")
    public String register(User user) {
        userService.save(user);  // Use userService to save the user
        return "redirect:/login";  // Redirect to the login page after registration
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        return "dashboard";  // Return the dashboard view
    }

}

