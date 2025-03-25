package com.example.paytech.service;

import com.example.paytech.dto.CustomerProperties;
import com.example.paytech.dto.PaymentProperties;
import com.example.paytech.dto.PaymentRequest;
import com.example.paytech.dto.PaymentResponse;
import com.example.paytech.dto.PaytechApiProperties;
import com.example.paytech.mappers.PaymentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

  private final PaytechApiProperties paytechApiProperties;
  private final PaymentProperties paymentProperties;
  private final CustomerProperties customerProperties;
  private final PaymentMapper paymentMapper;
  private final RestTemplate restTemplate;

  public PaymentServiceImpl(PaytechApiProperties paytechApiProperties,
      PaymentProperties paymentProperties, CustomerProperties customerProperties,
      PaymentMapper paymentMapper, RestTemplate restTemplate) {
    this.paytechApiProperties = paytechApiProperties;
    this.paymentProperties = paymentProperties;
    this.customerProperties = customerProperties;
    this.paymentMapper = paymentMapper;
    this.restTemplate = restTemplate;
  }

  @Override
  public PaymentResponse createPayment(PaymentRequest paymentRequest) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + paytechApiProperties.getToken());
    headers.set("accept", MediaType.APPLICATION_JSON_VALUE);

    HttpEntity<PaymentRequest> request = new HttpEntity<>(paymentRequest, headers);
    return restTemplate.postForObject(paytechApiProperties.getUrl() + "/payments", request,
        PaymentResponse.class);
  }

  @Override
  public String processPayment(double amount) {
    CustomerProperties customer = paymentMapper.mapToCustomer(customerProperties.getEmail(),
        customerProperties.getPhone(), customerProperties.getReferenceId(),
        customerProperties.getFirstName(), customerProperties.getLastName(),
        customerProperties.getCitizenshipCountryCode(), customerProperties.getDateOfBirth(),
        customerProperties.getLocale());
    PaymentRequest paymentRequest = paymentMapper.mapToPaymentRequest(amount,
        paymentProperties.getCurrency(), paymentProperties.getPaymentType(), customer);

    PaymentResponse paymentResponse = createPayment(paymentRequest);
    return "redirect:" + paymentResponse.getResult().getRedirectUrl();
  }
}
