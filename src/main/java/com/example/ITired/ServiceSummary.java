package com.example.ITired;

import lombok.Data;

@Data
public class ServiceSummary {
    private String serviceTitle;
    private int count;
    private double totalAmount;

    public ServiceSummary(String serviceTitle, int count, double totalAmount) {
        this.serviceTitle = serviceTitle;
        this.count = count;
        this.totalAmount = totalAmount;
    }


    public void incrementCount() {
        this.count++;
    }

    public void addAmount(double amount) {
        this.totalAmount += amount;
    }
}