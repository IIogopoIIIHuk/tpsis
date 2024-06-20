package com.example.ITired.Services;

import com.example.ITired.OtherClinic;
import com.example.ITired.repositories.OtherClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtherClinicService {

    @Autowired
    private OtherClinicRepository otherClinicRepository;

    public List<OtherClinic> getAllClinics() {
        return otherClinicRepository.findAll();
    }

    public OtherClinic getClinicById(Long id) {
        return otherClinicRepository.findById(id).orElse(null);
    }

    public OtherClinic saveClinic(OtherClinic clinic) {
        return otherClinicRepository.save(clinic);
    }

    public void deleteClinic(Long id) {
        otherClinicRepository.deleteById(id);
    }
}
