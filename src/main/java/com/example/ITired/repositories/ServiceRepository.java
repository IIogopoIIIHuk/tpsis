package com.example.ITired.repositories;

import com.example.ITired.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByTitle(String title);
}
