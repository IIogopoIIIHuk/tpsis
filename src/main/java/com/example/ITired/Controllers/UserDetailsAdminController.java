package com.example.ITired.Controllers;

import com.example.ITired.Appointment;
import com.example.ITired.Services.AppointmentService;
import com.example.ITired.ServiceStatistic;
import com.example.ITired.Services.UserService;
import com.example.ITired.Services.ServiceStatisticService;
import com.example.ITired.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/userDetails-admin")
public class UserDetailsAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ServiceStatisticService serviceStatisticService;

    @GetMapping("/{userId}")
    public String getUserDetailsAdmin(@PathVariable Long userId, Model model) {
        // Получаем данные пользователя
        User user = userService.findById(userId);

        // Получаем записи пользователя
        List<Appointment> appointments = appointmentService.getUserAppointments(userId);

        // Получаем статистику оказанных услуг
        List<ServiceStatistic> serviceStatistics = serviceStatisticService.findAll();

        // Добавляем данные в модель
        model.addAttribute("user", user);
        model.addAttribute("appointments", appointments);
        model.addAttribute("serviceStatistics", serviceStatistics);

        return "userDetails-admin";
    }

    @PostMapping("/markCompleted")
    public String markAppointmentCompleted(@RequestParam("appointmentId") Long appointmentId, @RequestParam("userId") Long userId, Model model) {
        appointmentService.markAppointmentCompleted(appointmentId);
        return "redirect:/userDetails-admin/" + userId;
    }
}
