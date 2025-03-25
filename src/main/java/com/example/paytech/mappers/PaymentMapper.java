package com.example.paytech.mappers;

import com.example.paytech.dto.CustomerProperties;
import com.example.paytech.dto.PaymentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PaymentMapper {

  CustomerProperties mapToCustomer(String email, String phone, String referenceId, String firstName, String lastName, String citizenshipCountryCode, String dateOfBirth, String locale);

  @Mapping(target = "amount", expression = "java(java.math.BigDecimal.valueOf(amount))")
  @Mapping(source = "currency", target = "currency")
  @Mapping(source = "paymentType", target = "paymentType")
  PaymentRequest mapToPaymentRequest(double amount, String currency, String paymentType, CustomerProperties customer);
}


