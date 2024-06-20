//package com.example.ITired;
//
//import com.example.ITired.repositories.OtherClinicRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    private final OtherClinicRepository otherClinicRepository;
//
//    public DataInitializer(OtherClinicRepository otherClinicRepository) {
//        this.otherClinicRepository = otherClinicRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        OtherClinic clinic1 = new OtherClinic(null, "Clinic A", "123 Main St", "Teeth Cleaning, Braces, Checkup", "Teeth Cleaning: $50, Braces: $3000, Checkup: $100");
//        OtherClinic clinic2 = new OtherClinic(null, "Clinic B", "456 Elm St", "Whitening, Implants, Fillings", "Whitening: $200, Implants: $2500, Fillings: $150");
//        OtherClinic clinic3 = new OtherClinic(null, "Clinic C", "789 Oak St", "Root Canal, Extractions, Crowns", "Root Canal: $800, Extractions: $200, Crowns: $1000");
//
//        otherClinicRepository.saveAll(Arrays.asList(clinic1, clinic2, clinic3));
//    }
//}
