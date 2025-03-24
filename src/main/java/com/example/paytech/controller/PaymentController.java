package com.example.paytech.controller;

import com.example.paytech.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {

  private final PaymentService paymentService;


  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping("/payment-form")
  public String paymentForm() {
    return "payment-form";
  }

  @PostMapping("/payment")
  public String createPayment(
      @RequestParam("amount") double amount,
      Model model
  ) {
    String result = paymentService.processPayment(amount);

    if (result.startsWith("redirect:")) {
      return result;
    } else {
      model.addAttribute("error", "Failed payment creation");
      return "error";
    }
  }
}