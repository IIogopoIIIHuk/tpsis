package com.example.ITired.Controllers;

import com.example.ITired.Clinic;
import com.example.ITired.OtherClinic;
import com.example.ITired.ServiceStatistic;
import com.example.ITired.ServiceSummary;
import com.example.ITired.Services.ServiceStatisticService;
import com.example.ITired.repositories.OtherClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class OtherClinicController {

    @Autowired
    private ServiceStatisticService serviceStatisticService;

    @GetMapping("/other-clinics")
    public String getOtherClinics(Model model) {
        // Пример данных о клиниках
        List<Clinic> otherClinics = Arrays.asList(
                new Clinic("Dental4Windows", "13 Main St", "Отбеливание, Профилактика", "$50 - $200"),
                new Clinic("DentalTap", "56 Elm St", "Реставрация, Протезирование", "$60 - $250"),
                new Clinic("Dentist Plus", "09 Oak St", "Отбеливание, Диагностика", "$70 - $300")
        );

        // Получаем данные о завершенных услугах
        List<ServiceStatistic> completedServices = serviceStatisticService.findAll();

        // Группируем услуги по названию и считаем количество и сумму
        Map<String, ServiceSummary> serviceSummaryMap = new HashMap<>();
        double totalEarnings = 0;

        for (ServiceStatistic service : completedServices) {
            ServiceSummary summary = serviceSummaryMap.getOrDefault(service.getServiceTitle(), new ServiceSummary(service.getServiceTitle(), 0, 0.0));
            summary.incrementCount();
            summary.addAmount(service.getAmountPaid());
            serviceSummaryMap.put(service.getServiceTitle(), summary);
            totalEarnings += service.getAmountPaid();
        }

        // Преобразуем Map в List для передачи в шаблон
        List<ServiceSummary> serviceSummaries = new ArrayList<>(serviceSummaryMap.values());

        // Добавляем данные в модель
        model.addAttribute("otherClinics", otherClinics);
        model.addAttribute("serviceSummaries", serviceSummaries);
        model.addAttribute("totalEarnings", totalEarnings);

        return "other-clinics";
    }
}
