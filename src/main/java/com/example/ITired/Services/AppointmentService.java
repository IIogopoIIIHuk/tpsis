package com.example.ITired.Services;

import com.example.ITired.Appointment;
import com.example.ITired.ServiceStatistic;
import com.example.ITired.repositories.AppointmentRepository;
import com.example.ITired.repositories.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Transactional
    public void markAppointmentCompleted(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new IllegalArgumentException("Invalid appointment Id:" + appointmentId));

        // Перемещение данных об исполненной услуге в таблицу статистики
        ServiceStatistic statistic = new ServiceStatistic();
        statistic.setServiceId(appointment.getService().getId());
        statistic.setServiceTitle(appointment.getService().getTitle());
        statistic.setAmountPaid((double) appointment.getService().getPrice());
        statistic.setCompletedDate(appointment.getAppointmentDate());

        statisticsRepository.save(statistic);

        // Удаление записи об услуге из основной таблицы
        appointmentRepository.delete(appointment);
    }
    public void cancelAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    public List<Appointment> getUserAppointments(Long userId) {
        return appointmentRepository.findByUserId(userId);
    }

    public boolean userHasAppointmentAtTime(Long userId, LocalDateTime dateTime) {
        List<Appointment> appointments = appointmentRepository.findByUserId(userId);
        return appointments.stream().anyMatch(a -> a.getAppointmentDate().isEqual(dateTime));
    }
}