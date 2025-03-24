import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.paytech.dto.PaymentResponse;
import com.example.paytech.service.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

public class PaymentServiceImplTest {

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private PaymentServiceImpl paymentService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(paymentService, "currency", "EUR");
    ReflectionTestUtils.setField(paymentService, "paymentType", "DEPOSIT");
    ReflectionTestUtils.setField(paymentService, "email", "test@example.com");
    ReflectionTestUtils.setField(paymentService, "phone", "123 4567890");
    ReflectionTestUtils.setField(paymentService, "referenceId", "12345");
    ReflectionTestUtils.setField(paymentService, "firstName", "Roman");
    ReflectionTestUtils.setField(paymentService, "lastName", "Galkin");
    ReflectionTestUtils.setField(paymentService, "citizenshipCountryCode", "GE");
    ReflectionTestUtils.setField(paymentService, "dateOfBirth", "2001-12-03");
    ReflectionTestUtils.setField(paymentService, "locale", "ru");
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
    when(restTemplate.postForObject(anyString(), any(), any())).thenReturn(null);
    String result = paymentService.processPayment(100.0);
    assertEquals("error", result);
  }

  @Test
  public void testProcessPayment_NoRedirectUrl() {
    PaymentResponse paymentResponse = new PaymentResponse();
    PaymentResponse.Result result = new PaymentResponse.Result();
    paymentResponse.setResult(result);

    when(restTemplate.postForObject(anyString(), any(), any())).thenReturn(paymentResponse);
    String resulString = paymentService.processPayment(100.0);
    assertEquals("error", resulString);
  }
}
