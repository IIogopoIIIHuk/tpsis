package com.example.ITired.Services;

import com.example.ITired.Appointment;
import com.example.ITired.OtherClinic;
import com.example.ITired.Service;
import com.example.ITired.repositories.AppointmentRepository;
import com.example.ITired.repositories.OtherClinicRepository;
import com.example.ITired.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@Slf4j
@RequiredArgsConstructor
public class ServiceSer {

    private final ServiceRepository serviceRepository;
    private final AppointmentRepository appointmentRepository;
    private final OtherClinicRepository otherClinicRepository;




    public List<Service> listServices(String title) {
        List<Service> services = serviceRepository.findAll();
        return services;
    }

    public void saveService(Service service){
        log.info("Saving new {}", service);
        serviceRepository.save(service);
    }

    public void deleteService(Long id){
        serviceRepository.findById(id);
    }

    public Service getServiceById(Long id){
        return serviceRepository.findById(id).orElse(null);
    }

    public void saveAppointment(Appointment appointment) {
        log.info("Saving new appointment {}", appointment);
        appointmentRepository.save(appointment);
    }

    public List<Appointment> getUserAppointments(Long userId) {
        return appointmentRepository.findByUserId(userId);
    }


    public List<OtherClinic> getAllClinics() {
        return otherClinicRepository.findAll();
    }

    public OtherClinic getClinicById(Long id) {
        return otherClinicRepository.findById(id).orElse(null);
    }

    public void saveClinic(OtherClinic clinic) {
        log.info("Saving new clinic {}", clinic);
        otherClinicRepository.save(clinic);
    }

    public void deleteClinic(Long id) {
        otherClinicRepository.deleteById(id);
    }

}
