package com.example.ITired.Controllers;

import com.example.ITired.Appointment;
import com.example.ITired.Services.AppointmentService;
import com.example.ITired.Services.UserService;
import com.example.ITired.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;
    private final AppointmentService appointmentService;

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("users", userService.list()); //передаем всех пользователей
        return "admin";
    }



    @GetMapping("/listUsers-admin")
    public String listUsers(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "listUsers-admin";
    }


    // Просмотр подробной информации о пользователе
    @GetMapping("/admin/user/{id}")
    public String viewUserDetails(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        if (user != null) {
            List<Appointment> appointments = appointmentService.getUserAppointments(id);
            model.addAttribute("user", user);
            model.addAttribute("appointments", appointments);
            return "userDetails-admin";
        }
        return "redirect:/listUsers-admin";
    }

    // Блокировка пользователя
    @PostMapping("/admin/user/ban/{id}")
    public String banUser(@PathVariable Long id) {
        userService.banUser(id);
        return "redirect:/listUsers-admin";
    }

    // Удаление пользователя
    @PostMapping("/admin/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/listUsers-admin";
    }

}
