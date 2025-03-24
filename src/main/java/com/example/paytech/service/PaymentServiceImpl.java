package com.example.paytech.service;

import com.example.paytech.dto.PaymentRequest;
import com.example.paytech.dto.PaymentResponse;
import com.example.paytech.entity.Customer;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

  @Value("${paytech.api.url}")
  private String apiUrl;

  @Value("${paytech.api.token}")
  private String apiToken;

  @Value("${payment.currency}")
  private String currency;

  @Value("${payment.paymentType}")
  private String paymentType;

  @Value("${customer.email}")
  private String email;

  @Value("${customer.phone}")
  private String phone;

  @Value("${customer.referenceId}")
  private String referenceId;

  @Value("${customer.firstName}")
  private String firstName;

  @Value("${customer.lastName}")
  private String lastName;

  @Value("${customer.citizenshipCountryCode}")
  private String citizenshipCountryCode;

  @Value("${customer.dateOfBirth}")
  private String dateOfBirth;

  @Value("${customer.locale}")
  private String locale;


  private final RestTemplate restTemplate;

  public PaymentServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public PaymentResponse createPayment(PaymentRequest paymentRequest) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + apiToken);
    headers.set("accept", MediaType.APPLICATION_JSON_VALUE);

    HttpEntity<PaymentRequest> request = new HttpEntity<>(paymentRequest, headers);
    return restTemplate.postForObject(apiUrl + "/payments", request, PaymentResponse.class);
  }

  @Override
  public String processPayment(double amount) {
    PaymentRequest paymentRequest = new PaymentRequest();
    paymentRequest.setAmount(BigDecimal.valueOf(amount));
    paymentRequest.setCurrency(currency);
    paymentRequest.setPaymentType(paymentType);

    Customer customer = new Customer();
    customer.setEmail(email);
    customer.setPhone(phone);
    customer.setReferenceId(referenceId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    customer.setCitizenshipCountryCode(citizenshipCountryCode);
    customer.setDateOfBirth(dateOfBirth);
    customer.setLocale(locale);
    paymentRequest.setCustomer(customer);

    PaymentResponse paymentResponse = createPayment(paymentRequest);
    if (paymentResponse != null) {
      log.info(paymentResponse.toString());
    }

    if (paymentResponse != null && paymentResponse.getResult() != null
        && paymentResponse.getResult().getRedirectUrl() != null) {
      return "redirect:" + paymentResponse.getResult().getRedirectUrl();
    } else {
      return "error";
    }
  }
}