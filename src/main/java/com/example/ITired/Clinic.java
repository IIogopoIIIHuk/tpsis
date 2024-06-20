package com.example.ITired;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class Clinic {
    private String name;
    private String address;
    private String services;
    private String prices;

    public Clinic(String name, String address, String services, String prices) {
        this.name = name;
        this.address = address;
        this.services = services;
        this.prices = prices;
    }
}