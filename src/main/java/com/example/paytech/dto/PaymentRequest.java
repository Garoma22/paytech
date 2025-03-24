package com.example.paytech.dto;
import com.example.paytech.entity.Customer;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentRequest {

  private String paymentType = "DEPOSIT";
  private BigDecimal amount;
  private String currency = "EUR";
  private Customer customer;


}

