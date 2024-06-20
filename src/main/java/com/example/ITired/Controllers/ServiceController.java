package com.example.ITired.Controllers;


import com.example.ITired.Appointment;
import com.example.ITired.Service;
import com.example.ITired.Services.AppointmentService;
import com.example.ITired.Services.ServiceSer;
import com.example.ITired.Services.UserService;
import com.example.ITired.User;
import com.example.ITired.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceSer serviceSer;
    private final UserService userService;

    @Autowired
    private AppointmentService appointmentService;

//    @GetMapping("/services")
//    public String services(Model model) {
//        List<Service> services = serviceSer.listServices(null);
//        model.addAttribute("services", services);
//        return "index";
//    }

    @GetMapping("/service/{id}")
    public String servicePage(@PathVariable Long id, Model model) {
        Service service = serviceSer.getServiceById(id);
        model.addAttribute("service", service);
        return "service";
    }

    @PostMapping("/service/{id}/appointment")
    public String makeAppointment(@PathVariable Long id,
                                  @RequestParam("date") String date,
                                  @RequestParam("time") String time,
                                  HttpSession session,
                                  Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : null;

            if(username == null){
                return "redirect:/login";
            }


            User user = userService.findByUsername(username);

            if (user == null){
                return "redirect:/login";
            }

            LocalDate appointmentDate = LocalDate.parse(date);
            LocalTime appointmentTime = LocalTime.parse(time);
            LocalDateTime appointmentDateTime = LocalDateTime.of(appointmentDate, appointmentTime);

        if (appointmentService.userHasAppointmentAtTime(user.getId(), appointmentDateTime)) {
            model.addAttribute("error", "Вы уже записаны на это время.");
            return "redirect:/";
        }

            Service service = serviceSer.getServiceById(id);
            Appointment appointment = new Appointment();
            appointment.setService(service);
            appointment.setUser(user);
            appointment.setAppointmentDate(appointmentDateTime);
            serviceSer.saveAppointment(appointment);

            List<Appointment> cart = (List<Appointment>) session.getAttribute("cart");
            if (cart == null) {
                cart = new ArrayList<>();
            }
            cart.add(appointment);
            session.setAttribute("cart", cart);

            return "redirect:/cart";



    }



//    @GetMapping("/cart")
//    public String cart(@RequestParam("userId") Long userId, Model model) {
//        List<Appointment> appointments = serviceSer.getUserAppointments(userId);
//        model.addAttribute("appointments", appointments);
//        return "cart";
//    }


    @GetMapping("/cart")
    public String cart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : null;

        if (username != null) {
            User user = userService.findByUsername(username);
            List<Appointment> appointments = serviceSer.getUserAppointments(user.getId());
            model.addAttribute("appointments", appointments);
        }

        return "cart";
    }

    @PostMapping("/cancel")
    public String cancelAppointment(@RequestParam("id") Long appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
        return "redirect:/user/appointments";
    }


}
