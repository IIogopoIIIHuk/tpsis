package com.example.ITired.repositories;

import com.example.ITired.ServiceStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<ServiceStatistic, Long> {
}