package com.example.ITired;

import com.example.ITired.Service;
import com.example.ITired.repositories.ServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(ServiceRepository repository) {
        return null;
    }
}
