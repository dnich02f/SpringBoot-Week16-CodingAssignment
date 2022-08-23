package com.promineotech.jeep.controller;

/*
 * Step 17: Create the JeepOrderController from the JeepSalesController and change "Sales"
 * to "Orders". Change the @Operation summary, description, and responses to match the function 
 * of Ordering a Jeep. Change the implementation in the @Schema to an Order class, instead of Jeep class
 * Step 18: Create the Order class in the src/main/jeep.entity
 * Step 20: In the parameters, change the name to orderRequest
 * Step 21: This is a Create Order, so we now need @POSTMapping for the createOrder method, and we 
 * will pass a parameter in the body using @RequestBody, binding it to a OrderRequest
 * Step 22: The controller is complete, and now we must implement it the controller method in
 * src/main/jeep.controller.BasicJeepOrderController and implement the JeepOrderController interface 
 * we just created here
 * Step 27: response.getBody is null in the CreateOrderTest, we have to change that.
 * Now, we have to validate the incoming data to make sure it is correct. Add @Valid in the createOrder
 * method to validate the OrderRequest object. Go to the OrderRequest in jeep.entity
 */



/*
 * WHAT IS A CONTROLLER LAYER? with OPEN API DOCUMENTATION TO DESCRIBE THE CONTROLLER
 * A Controller is a layer of code that intercepts a http request, sends the request to service layer, 
 * receives response from service layer, formats the response back to the client (browser, js), etc
 * 
 * WHY AN INTERFACE? To write all of the required annotations that implementing classes will implement.
 */

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.OrderRequest;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@Validated 
/*
 * need to add validated in order for Bean validation to be effective
 */

/*
 * Need to tell Spring how to handle method, class, and request parameters
 * In Package com.promineotech.jeep.controller.support, BaseTest class, getBaseUri method,
 * we refer to /jeeps. -> return String.format("http://localhost:%d/jeeps", serverPort);
 * SO, any URI that has /jeeps after the port number will get mapped to this class
 */
@RequestMapping("/orders")

/*
 * OpenAPI also accessed through swagger-ui in the browser
 */
@OpenAPIDefinition(info = @Info(title = "Jeep Order Service"), //Jeep Order Service will appear on Swagger Documentation
    servers = {@Server(url = "http://localhost:8080", description = "Local server.")})


/*
 * Need to add documentation for FetchJeeps method
 */
public interface JeepOrderController {

  //@formatter:off
  @Operation(
      summary = "Create an order for a Jeep",
      description = "Returns the created Jeep",
      responses = {
          @ApiResponse(responseCode = "201", description = "The created Jeeps is returned", content = @Content(mediaType = "application/json", 
              schema = @Schema (implementation = Order.class))), // okay/success
          @ApiResponse(responseCode = "400", description = "The request parameters are invalid", content = @Content(mediaType = "application/json")), // illegal request
          @ApiResponse(responseCode = "404", description = "A Jeep component was not found with the input criteria", content = @Content(mediaType = "application/json")), // not found 
          @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json")) // unplanned exception
      },
      parameters = {
           @Parameter(name = "orderRequest", required = true, description = "The order as JSON")
      }
      
   )
 
  /*
   * Map get request to /order to createOrder method using PostMapping
   */
  @PostMapping // Create 
  @ResponseStatus(code = HttpStatus.CREATED)
  Order createOrder(@Valid @RequestBody OrderRequest orderRequest);
  //@formatter:on
  
  
  
  /*
   * Spring will map all requests with /order to the JeepOrderController class
   * Spring will map all getRequests with /order to the ...  method
   */
}
