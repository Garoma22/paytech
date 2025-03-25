package com.example.paytech.dto;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "paytech.api")
public class PaytechApiProperties {
  private String url;
  private String token;
}