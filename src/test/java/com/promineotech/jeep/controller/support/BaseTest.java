package com.promineotech.jeep.controller.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import lombok.Getter;

/*
 * Step 12 for CreateOrderTest, declare base URI with the http as a string
 * and serverPort as a variable that SpringBoot will fill in because of @LocalServerPort
 */

public class BaseTest {
  
  /*
   * specify random port in base test class as referenced in FetchJeepTest
   * so it's available for all tests
   */
  
  
  @LocalServerPort 
  /*
   * because of the LocalServerPort, SpringBoot will fill in the serverPort variable for us
   */
  private int serverPort;
   
  
  /**
   * Retrieve the uri (Uniform Resource Identifier -> URL is Uniform Resource Locator under URI)
   * %d is the placeholder for the port
   */
  protected String getBaseUriForJeeps() {
    return String.format("http://localhost:%d/jeeps", serverPort);
  }
  
  /**
   * STEP 12: Declare base URI for Orders
   * @return
   */
  protected String getBaseUriForOrders() {
    return String.format("http://localhost:%d/orders", serverPort);
  }
  
  /*
   * Test Rest Template to send http to running application 
   * Inject the copy of TestRestTemplate with Autowired
   */
  @Autowired
  @Getter
  private TestRestTemplate restTemplate;
  
  
  
  public TestRestTemplate getRestTemplate() {
    return restTemplate;
  }



 
 
}
