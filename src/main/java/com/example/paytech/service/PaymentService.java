package com.example.paytech.service;

import com.example.paytech.dto.PaymentRequest;
import com.example.paytech.dto.PaymentResponse;

public interface PaymentService {

  PaymentResponse createPayment(PaymentRequest paymentRequest);

  String processPayment(double amount);
}