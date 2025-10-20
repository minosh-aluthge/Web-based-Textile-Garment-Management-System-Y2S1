package com.example.myprac.garmentsystem.order.strategy.impl;

import com.example.myprac.garmentsystem.order.dto.PaymentRequest;
import com.example.myprac.garmentsystem.order.dto.PaymentResponse;
import com.example.myprac.garmentsystem.order.strategy.PaymentStrategy;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PayPalPaymentStrategy implements PaymentStrategy {
    @Override
    public String getName() {
        return "PAYPAL";
    }

    @Override
    public PaymentResponse process(PaymentRequest request) {
        String txId = "PP-" + UUID.randomUUID();
        String msg = String.format("Processed PayPal payment of %.2f %s", request.getAmount(), request.getCurrency());
        return new PaymentResponse(true, msg, txId);
    }
}
