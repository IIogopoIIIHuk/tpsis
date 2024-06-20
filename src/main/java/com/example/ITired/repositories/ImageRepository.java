package com.example.ITired.repositories;

import com.example.ITired.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Images, Long> {

    void deleteByServiceId(Long id);
}
