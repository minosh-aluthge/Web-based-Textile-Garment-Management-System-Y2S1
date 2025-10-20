package com.example.myprac.garmentsystem.order.strategy;

import com.example.myprac.garmentsystem.order.dto.PaymentRequest;
import com.example.myprac.garmentsystem.order.dto.PaymentResponse;

public interface PaymentStrategy {
    String getName();
    PaymentResponse process(PaymentRequest request);
}
