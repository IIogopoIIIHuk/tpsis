package com.example.ITired.Controllers;

import com.example.ITired.Appointment;
import com.example.ITired.Services.AppointmentService;
import com.example.ITired.Services.UserService;
import com.example.ITired.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;


    @PostMapping("/markCompleted")
    public String markAppointmentCompleted(@RequestParam("appointmentId") Long appointmentId, Model model) {
        appointmentService.markAppointmentCompleted(appointmentId);
        return "redirect:/userDetails-admin";
    }

    @GetMapping("/cart")
    public String getUserAppointments(@RequestParam(name = "sort", required = false) String sort, Model model) {
        User user = userService.getCurrentUser();
        if (user == null) {
            return "redirect:/login"; // Перенаправление на страницу логина, если пользователь не аутентифицирован
        }

        Long userId = user.getId();
        List<Appointment> appointments = appointmentService.getUserAppointments(userId);

        if ("date".equals(sort)) {
            appointments.sort(Comparator.comparing(Appointment::getAppointmentDate));
        }

        model.addAttribute("appointments", appointments);
        return "cart";
    }

    @PostMapping("/cancel")
    public String cancelAppointment(@RequestParam("id") Long appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
        return "redirect:/cart"; // Перенаправление на страницу записей пользователя
    }
}
