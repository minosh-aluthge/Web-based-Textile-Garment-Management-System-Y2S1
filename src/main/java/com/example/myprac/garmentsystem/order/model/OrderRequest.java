package com.example.myprac.garmentsystem.order.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

public class OrderRequest {
    private Long customerId;
    private String subject;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deliveryDate;
    private String createdBy;
    private List<OrderItem> orderItems;

    // Constructors
    public OrderRequest() {}

    public OrderRequest(Long customerId, String subject, String description, LocalDateTime deliveryDate, String createdBy, List<OrderItem> orderItems) {
        this.customerId = customerId;
        this.subject = subject;
        this.description = description;
        this.deliveryDate = deliveryDate;
        this.createdBy = createdBy;
        this.orderItems = orderItems;
    }

    // Getters and Setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDateTime getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDateTime deliveryDate) { this.deliveryDate = deliveryDate; }
    
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    
    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
}
