package com.example.paytech.dto;

import lombok.Data;

@Data
public class PaymentResponse {
  private Result result;

  @Data
  public static class Result {
    private String id;
    private String created;
    private String paymentType;
    private String state;
    private int amount;
    private String currency;
    private String redirectUrl;
    private CustomerProperties customer;
  }
}