package com.example.paytech.config;

import com.example.paytech.mappers.PaymentMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
  @Bean
  public PaymentMapper paymentMapper() {
    return Mappers.getMapper(PaymentMapper.class);
  }
}