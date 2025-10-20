package com.example.myprac.garmentsystem.order.strategy;

import com.example.myprac.garmentsystem.order.dto.PaymentRequest;
import com.example.myprac.garmentsystem.order.dto.PaymentResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentProcessor {
    private final Map<String, PaymentStrategy> strategies = new HashMap<>();

    public PaymentProcessor(List<PaymentStrategy> strategies) {
        for (PaymentStrategy s : strategies) {
            this.strategies.put(s.getName(), s);
        }
    }

    public PaymentResponse process(PaymentRequest request) {
        PaymentStrategy strategy = strategies.get(request.getType().name());
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported payment type: " + request.getType());
        }
        return strategy.process(request);
    }
}
