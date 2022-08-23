package com.promineotech.jeep.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.doThrow;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.jeep.Constants;
import com.promineotech.jeep.controller.support.FetchJeepTestSupport;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.service.JeepSalesService;

/*
 * tell test to run in the webEnvironment
 * when testing, the host name is always local host so the request
 * doesn't go out of the local network
 * 
 * specify random port in base test class so it's available for all test
 * Will create a new profile and override the default and SB will look for application-test.yaml in testresources
 */
class FetchJeepTest extends FetchJeepTestSupport {
  
  @Nested
  @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
  @ActiveProfiles("test")
  @Sql(scripts = {"classpath:flyway/migrations/V1.0__Jeep_Schema.sql", 
       "classpath:flyway/migrations/V1.1__Jeep_Data.sql"},
      config = @SqlConfig(encoding = "utf-8"))
  class TestThatDoNotPolluteTheApplicationContext extends FetchJeepTestSupport{
 
    @Test
    // test should be self describing
     void testThatJeepsAreReturnedWhenAValidModelAndTrimAreSupplied() {
    // Given: A valid model, trim, and URI
       JeepModel model = JeepModel.WRANGLER;
       String trim = "Sport";
       String uri = String.format("%s?model=%s&trim=%s", getBaseUriForJeeps(), model, trim);
       //note: %s is the placeholder for the getBaseUri method from the BaseTest class
       
        
    // When: a connection is made to the URI 
       /*
        * throw http at rest controller, sends http request to service layer, 
        * receives the response and formats it to go back to the client
        */
       ResponseEntity<List<Jeep>> response = getRestTemplate().exchange(uri, HttpMethod.GET, null, 
           new ParameterizedTypeReference<>() {});
      
      
    // Then: a valid status code (OK - 200) is returned 
       assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
       
       
    // And : the actual list returned is the same as the expected list
       List<Jeep> actual = response.getBody();
       List<Jeep> expected = buildExpected();
       
       /*
        * loop through the returned actual value and set the PrimaryKey to null
        */
       
       assertThat(actual).isEqualTo(expected);
       
     }
     /**
      * 
      */
     @Test
    // test should be self describing
     void testThatAnErrorMessageIsReturnedWhenAnUnknownTrimIsSupplied() {
    // Given: A valid model, trim, and URI
       JeepModel model = JeepModel.WRANGLER;
       String trim = "Unknown Value";
       String uri = String.format("%s?model=%s&trim=%s", getBaseUriForJeeps(), model, trim);
       //note: %s is the placeholder for the getBaseUri method from the BaseTest class
       
        
    // When: a connection is made to the URI 
       /*
        * throw http at rest controller, sends http request to service layer, 
        * receives the response and formats it to go back to the client
        */
       ResponseEntity<Map<String, Object>> response = getRestTemplate().exchange(uri, HttpMethod.GET, null, 
           new ParameterizedTypeReference<>() {});
      
      
    // Then: a not found 404 status is returned 
       assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
       
       
    // And : an error message is returned
     Map<String, Object> error = response.getBody();
     assertErrorMessageValid(error, HttpStatus.NOT_FOUND);
     
     }
    

     @ParameterizedTest
     @MethodSource("com.promineotech.jeep.controller.FetchJeepTest#parametersForInvalidInput")
    // test should be self describing
     void testThatAnErrorMessageIsReturnedWhenAnInvalidTrimIsSupplied(String model, String trim, String reason) {
    // Given: A valid model, trim, and URI
       String uri = String.format("%s?model=%s&trim=%s", getBaseUriForJeeps(), model, trim);
       //note: %s is the placeholder for the getBaseUri method from the BaseTest class
       
        
    // When: a connection is made to the URI 
       /*
        * throw http at rest controller, sends http request to service layer, 
        * receives the response and formats it to go back to the client
        */
       ResponseEntity<Map<String, Object>> response = getRestTemplate().exchange(uri, HttpMethod.GET, null, 
           new ParameterizedTypeReference<>() {});
      
      
    // Then: a not found 404 status is returned 
       assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
       
       
    // And : an error message is returned
     Map<String, Object> error = response.getBody();
     // error message is below and has the mentioned keys in them
     assertErrorMessageValid(error, HttpStatus.BAD_REQUEST);
     
     }
    
  }
  

  static Stream<Arguments> parametersForInvalidInput() {
    //@formatter:off
    return Stream.of(
        arguments("WRANGLER", "SDF$%@><^#%KL", "Trim contains non-alphanumeric characters"),
        arguments("WRANGLER", "C".repeat(Constants.TRIM_MAX_LENGTH + 1), "Trim length too long."),
        arguments("INVLAID", "Sport", "Model is not enum value")
        );
    //@formatter:on
  }
  
  @Nested
  @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
  @ActiveProfiles("test")
  @Sql(scripts = {"classpath:flyway/migrations/V1.0__Jeep_Schema.sql", 
       "classpath:flyway/migrations/V1.1__Jeep_Data.sql"},
      config = @SqlConfig(encoding = "utf-8"))
  /**
   * Create a mock bean in the class below. Currently, we have the controller class that injects the service class
   * the service class injects the dao class. choose the service class for MockBean
   * @author Darryl.Nichols
   *
   *Creates bean "jeepSalesService" as a mock object and puts that bean in the bean registry 
   *and replaces any other bean that was there. So this replaces the jeepSalesService bean that was declared
   *in the jeepSalesService
   */
  class TestThatDoPolluteTheApplicationContext extends FetchJeepTestSupport{
    @MockBean
    private JeepSalesService jeepSalesService;
    
    /**
     * 
     */
    @Test
   // test should be self describing
    void testThatAnUnplannedErrorResultsInA500Status() {
   // Given: A valid model, trim, and URI
      JeepModel model = JeepModel.WRANGLER;
      String trim = "Invalid";
      String uri = String.format("%s?model=%s&trim=%s", getBaseUriForJeeps(), model, trim);
      //note: %s is the placeholder for the getBaseUri method from the BaseTest class
      
      //programming the mocked object
      doThrow(new RuntimeException("Ouch!")).when(jeepSalesService).fetchJeeps(model, trim);
       
   // When: a connection is made to the URI 
      /*
       * throw http at rest controller, sends http request to service layer, 
       * receives the response and formats it to go back to the client
       */
      ResponseEntity<Map<String, Object>> response = getRestTemplate().exchange(uri, HttpMethod.GET, null, 
          new ParameterizedTypeReference<>() {});
     
     
   // Then: a not found internal server error (500) is returned 
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
      
      
   // And : an error message is returned
    Map<String, Object> error = response.getBody();
    assertErrorMessageValid(error, HttpStatus.INTERNAL_SERVER_ERROR);
    
    }
  }

  
  

 
  
}
