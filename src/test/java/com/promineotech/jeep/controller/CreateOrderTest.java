package com.promineotech.jeep.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.jeep.controller.support.CreateOrderTestSupport;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.entity.Order;

/*
 * Objective: Behavior Driven Development for CREATING AN ORDER.
 * 
 * Step 1: in src/test/java under the controller package, create the test "CreateOrderTest"
 * Step 2: in src/test/java.CreateOrderTest, write "extends CreateOrderTestSupport" and have Spring
 * create the Support class for you. The class needs to be in the "controller.support" package
 * Step 3: look in test.controller.support -> CreateOrderTestSupport
 * Step 4: change test name to "testCreateOrderReturnsSuccess201"
 * Step 5: Add the class annotations @SpringBootTest, @ActiveProfiles, @Sql, and @SqlConfig 
 * see week 14 or 15 for more info
 * Step 6: create JSON that will be use as the REST request body, look for data in the files
 * mentioned in @Sql files, and select values from the data rows for the Order Table (since 
 * the test we are creating tests that an order is successfully created). This is the Jeep Order 
 * that we will test.
 * Step 7: The test is getting lengthy with the JSON, so highlight the JSON, and create an go to
 * Refactor -> Extract Method -> name it createOrderBody as a protected class (what does that mean?)
 * Step 8: Make method "createOrderBody" a String called body "String body = createOrderBody", then 
 * change createOrderBody method from void to String, and delete "String body = " and replace it 
 * with "return" so that it returns the JSON String
 * Step 9: take the createOrderBody method and place it in the CreateOrderTestSupport class
 * Step 10: Outline the test (should've done this earlier)
 * Step 11: in the When portion of testCreateOrderReturnsSuccess201 method, create a way to send the 
 * Order. It is like a data-transfer-object, but it will be a Jeep Order object using 
 * ResponseEntity<Order> called response --- ResponseEntity adds an HttpStatus code. getRestTemplate
 * Step 12: Create the URI for Orders operation "/orders" --> In test/jeep.controller.support, 
 * in the getBaseUriForOrders method, 
 * Step 13: Create URI String under "body" and call the method that was just created "getBaseUriForOrders"
 * Step 14: Create HttpEntity of type string that will be used in "response" ResponseEntity 
 * the HTTPEntity represents a response request with a header and a body
 * Step 15: "response" ResponseEntity doesn't know what an <Order>, is. So now, we have to create an Order 
 * Entity class in src/main/java.jeep.entity, and in this class we Refactored the name to OrderRequest
 * Step 16: in the Then portion of the testCreateOrderReturnsSuccess201 method, use assertThat
 * to test if the httpStatus code response is equal to Created (201), a 404 was returned because we 
 * have not created the Controller yet.
 * Step 17: Create the Controller - JeepOrderController interface in src/main/jeep.controller by 
 * copying the JeepSalesController and modifying it
 * Step 24: we need to set the ContentType (headers) for the POST operation createOrder. This,
 * "MediaType.APPLICATION_JSON" will tell the controller that the content coming into the body
 * is JSON. now run test
 * Step 25: change the ResonseEntity from <?> to <Order> and change Object.class to Order.class
 * Step 26: begin creating assertions, assert that the body is not null, the create an Order object
 * called order and get the reponse.getBody (from the JSON), then create assertions on the Order object
 * order.
 * Step 27: run the test, it fails because reponse.getBody was null, now we have to correct that 
 * in src/main.jeep.controller.JeepOrderController interface. After data validation in OrderRequest, 
 * we can now correct the null returning in the BasicJeepOrderController
 */


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
/*
 * @SpringBootTest with webEnvironment creates a RANDOM_PORT (host name is always localhost)
 */
@ActiveProfiles("test")
/*
 * @ActiveProfiles will load up the H2 database
 */
@Sql(scripts = {"classpath:flyway/migrations/V1.0__Jeep_Schema.sql", 
     "classpath:flyway/migrations/V1.1__Jeep_Data.sql"},
    config = @SqlConfig(encoding = "utf-8"))
class CreateOrderTest extends CreateOrderTestSupport {
  
/*
 * we are going to create some JSON to throw at the application with this test - refer to Step 7-9
 */
  @Test
  void testCreateOrderReturnsSuccess201() {
    // Given: an order as JSON
    String body = createOrderBody();
    String uri = getBaseUriForOrders();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> bodyEntity = new HttpEntity<>(body, headers);
    
    // When: the order is sent - Create a JeepOrder object
    ResponseEntity<Order> response = getRestTemplate().exchange(uri, HttpMethod.POST, bodyEntity, Order.class);
        
    // Then: a 201 status is returned
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    
    // And: the returned order is correct
    assertThat(response.getBody()).isNotNull();
    Order order = response.getBody();
    
    assertThat(order.getCustomer().getCustomerId()).isEqualTo("MORISON_LINA");
    assertThat(order.getModel().getModelId()).isEqualTo(JeepModel.WRANGLER);
    assertThat(order.getModel().getTrimLevel()).isEqualTo("Sport Altitude");
    assertThat(order.getModel().getNumDoors()).isEqualTo(4);
    assertThat(order.getColor().getColorId()).isEqualTo("EXT_NACHO");
    assertThat(order.getEngine().getEngineId()).isEqualTo("2_0_TURBO");
    assertThat(order.getTire().getTireId()).isEqualTo("35_TOYO");
    assertThat(order.getOptions()).hasSize(6);

  }
  


}
