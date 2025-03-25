import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.paytech.dto.CustomerProperties;
import com.example.paytech.dto.PaymentProperties;
import com.example.paytech.dto.PaymentRequest;
import com.example.paytech.dto.PaymentResponse;
import com.example.paytech.dto.PaytechApiProperties;
import com.example.paytech.mappers.PaymentMapper;
import com.example.paytech.service.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

public class PaymentServiceImplTest {

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private PaymentMapper paymentMapper;

  @InjectMocks
  private PaymentServiceImpl paymentService;

  @Mock
  private PaytechApiProperties paytechApiProperties;

  @Mock
  private PaymentProperties paymentProperties;

  @Mock
  private CustomerProperties customerProperties;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    when(paytechApiProperties.getToken()).thenReturn("testToken");
    when(paytechApiProperties.getUrl()).thenReturn("http://test.api");

    when(paymentProperties.getCurrency()).thenReturn("EUR");
    when(paymentProperties.getPaymentType()).thenReturn("DEPOSIT");

    when(customerProperties.getEmail()).thenReturn("test@example.com");
    when(customerProperties.getPhone()).thenReturn("123 4567890");
    when(customerProperties.getReferenceId()).thenReturn("12345");
    when(customerProperties.getFirstName()).thenReturn("Roman");
    when(customerProperties.getLastName()).thenReturn("Galkin");
    when(customerProperties.getCitizenshipCountryCode()).thenReturn("GE");
    when(customerProperties.getDateOfBirth()).thenReturn("2001-12-03");
    when(customerProperties.getLocale()).thenReturn("ru");

    when(paymentMapper.mapToCustomer(anyString(), anyString(), anyString(), anyString(),
        anyString(), anyString(), anyString(), anyString()))
        .thenReturn(new CustomerProperties());

    when(paymentMapper.mapToPaymentRequest(Mockito.anyDouble(), anyString(), anyString(),
        Mockito.any(CustomerProperties.class))).thenReturn(new PaymentRequest());
  }

  @Test
  public void testProcessPayment_Success() {
    PaymentResponse paymentResponse = new PaymentResponse();
    PaymentResponse.Result result = new PaymentResponse.Result();
    result.setRedirectUrl("https://example.com/redirect");
    paymentResponse.setResult(result);

    when(restTemplate.postForObject(anyString(), any(), any())).thenReturn(paymentResponse);

    String redirectUrl = paymentService.processPayment(100.0);

    assertEquals("redirect:https://example.com/redirect", redirectUrl);
  }

  @Test
  public void testProcessPayment_Failure() {
    PaymentResponse paymentResponse = new PaymentResponse();
    PaymentResponse.Result result = new PaymentResponse.Result();
    paymentResponse.setResult(result);

    when(restTemplate.postForObject(anyString(), any(), any())).thenReturn(paymentResponse);

    String redirectUrl = paymentService.processPayment(100.0);

    assertEquals("redirect:null", redirectUrl);
  }
}