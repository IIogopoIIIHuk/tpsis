package com.example.ITired;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class ServiceStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long serviceId;
    private String serviceTitle;
    private Double amountPaid;
    private LocalDateTime completedDate;


}