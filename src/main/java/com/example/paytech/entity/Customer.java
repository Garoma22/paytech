package com.example.paytech.entity;

import lombok.Data;

@Data
public class Customer {

  private String email;
  private String phone;
  private String referenceId;
  private String firstName;
  private String lastName;
  private String citizenshipCountryCode;
  private String dateOfBirth;
  private String locale;



}
