package com.example.Coursework.Controllers;

import com.example.Coursework.models.User;
import com.example.Coursework.models.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/index")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/index")
    public String registerUser(@ModelAttribute("user") User user,
                               @RequestParam("confirmPassword") String confirmPassword,
                               Model model) {
//        if (!user.getPassword().equals(confirmPassword)) {
//            model.addAttribute("errorMessage", "Passwords do not match");
//            return null;
//        }



        userService.saveUser(user);
        model.addAttribute("successMessage", "Registration successful");
        return "redirect:/login";

    }



}
