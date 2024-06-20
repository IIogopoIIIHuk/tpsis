package com.example.ITired.Controllers;

import com.example.ITired.Service;
import com.example.ITired.Services.ServiceSer;
import com.example.ITired.User;
import com.example.ITired.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    private final UserService userService;
    private final ServiceSer serviceSer;

    public MainController(UserService userService, ServiceSer serviceSer) {
        this.userService = userService;
        this.serviceSer = serviceSer;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Получение пользователя
        User user = userService.getCurrentUser();
        List<Service> services = serviceSer.listServices(null);
        model.addAttribute("services", services);
        // Установка пользователя в модель
        model.addAttribute("user", user);

        return "index";
    }

//    @GetMapping("/about")
//    public String about(Model model) {
//        User user = userService.getCurrentUser();
//        model.addAttribute("user", user);
//        return "about-us";
//    }

    @GetMapping("/contact")
    public String contact(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "contacts";
    }
}
