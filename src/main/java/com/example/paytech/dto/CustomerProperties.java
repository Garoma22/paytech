package com.example.paytech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Data
@ConfigurationProperties(prefix = "customer")
public class CustomerProperties {

  private String email;
  private String phone;
  private String referenceId;
  private String firstName;
  private String lastName;
  private String citizenshipCountryCode;
  private String dateOfBirth;
  private String locale;

  public CustomerProperties() {}
}
