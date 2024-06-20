package com.example.ITired.Controllers;


import com.example.ITired.User;
import com.example.ITired.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("name", new User());
        return "registration";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/index";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/basket")
    public String basketPage() {
        return "basket";
    }

    @GetMapping("/service")
    public String servicePage() {
        return "service";
    }

    @GetMapping("/contacts")
    public String contactsPage() {
        return "contacts";
    }

    @GetMapping("/profile")
    public String userProfile(Model model, Principal principal) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @PostMapping("/editProfile")
    public String editProfile(User user, @RequestParam(required = false) String password) {
        User currentUser = userService.getCurrentUser();

        currentUser.setUsername(user.getUsername());
        currentUser.setName(user.getName());

        if (password != null && !password.isEmpty()) {
            currentUser.setPassword(passwordEncoder.encode(password));
        }

        userService.saveUser(currentUser);
        return "redirect:/profile";
    }


    @PostMapping("/uploadPhoto")
    public String uploadPhoto(@RequestParam("photo") MultipartFile file) {
        User currentUser = userService.getCurrentUser();
        try {
            currentUser.setPhoto(file.getBytes());
            userService.saveUser(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            // Обработка ошибки загрузки фотографии
        }
        return "redirect:/profile";
    }


//    @GetMapping("/users")
//    public String listUsers(Model model) {
//        List<User> allUsers = userService.findAllUsers();
//        List<User> usersWithUserRole = allUsers.stream()
//                .filter(user -> user.getRoles().contains("USER"))
//                .collect(Collectors.toList());
//        model.addAttribute("users", usersWithUserRole);
//        return "other-clinics";
//    }
}
