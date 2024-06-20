package com.example.ITired.Services;

import com.example.ITired.ServiceStatistic;
import com.example.ITired.repositories.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceStatisticService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    public List<ServiceStatistic> findAll() {
        return statisticsRepository.findAll();
    }

    public void save(ServiceStatistic serviceStatistic) {
        statisticsRepository.save(serviceStatistic);
    }
}
