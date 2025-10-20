package com.example.myprac.garmentsystem.order.strategy.impl;

import com.example.myprac.garmentsystem.order.dto.PaymentRequest;
import com.example.myprac.garmentsystem.order.dto.PaymentResponse;
import com.example.myprac.garmentsystem.order.strategy.PaymentStrategy;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CashPaymentStrategy implements PaymentStrategy {
    @Override
    public String getName() {
        return "CASH";
    }

    @Override
    public PaymentResponse process(PaymentRequest request) {
        String txId = "CASH-" + UUID.randomUUID();
        String msg = String.format("Cash payment recorded for %.2f %s", request.getAmount(), request.getCurrency());
        return new PaymentResponse(true, msg, txId);
    }
}
