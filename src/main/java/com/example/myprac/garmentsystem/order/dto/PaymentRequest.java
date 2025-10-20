package com.example.myprac.garmentsystem.order.dto;

import com.example.myprac.garmentsystem.order.model.PaymentType;

public class PaymentRequest {
    private PaymentType type;
    private double amount;
    private String currency;
    private String details;
    private Long orderId;

    public PaymentRequest() {}

    public PaymentType getType() { return type; }
    public void setType(PaymentType type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
}
