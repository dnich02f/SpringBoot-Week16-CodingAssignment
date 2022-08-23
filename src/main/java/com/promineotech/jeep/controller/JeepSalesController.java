package com.promineotech.jeep.controller;

/*
 * WHAT IS A CONTROLLER LAYER? with OPEN API DOCUMENTATION TO DESCRIBE THE CONTROLLER
 * A Controller is a layer of code that intercepts a http request, sends the request to service layer, 
 * receives response from service layer, formats the response back to the client (browser, js), etc
 * 
 * WHY AN INTERFACE? To write all of the required annotations that implementing classes will implement.
 */

import java.util.List;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotech.jeep.Constants;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
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
@RequestMapping("/jeeps")

/*
 * OpenAPI also accessed through swagger-ui in the browser
 */
@OpenAPIDefinition(info = @Info(title = "Jeep Sales Service"), //Jeep Sales Service will appear on Swagger Documentation
    servers = {@Server(url = "http://localhost:8080", description = "Local server.")})


/*
 * Need to add documentation for FetchJeeps method
 */
public interface JeepSalesController {

  //@formatter:off
  @Operation(
      summary = "Return a list of Jeeps",
      description = "Returns a list of Jeeps given an optional model and/or trim",
      responses = {
          @ApiResponse(responseCode = "200", description = "A list of Jeeps is returned", content = @Content(mediaType = "application/json", 
              schema = @Schema (implementation = Jeep.class))), // okay/success
          @ApiResponse(responseCode = "400", description = "The request parameters are invalid", content = @Content(mediaType = "application/json")), // illegal request
          @ApiResponse(responseCode = "404", description = "No Jeeps were found with the input criteria", content = @Content(mediaType = "application/json")), // not found 
          @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json")) // unplanned exception
      },
      parameters = {
           @Parameter(name = "model", allowEmptyValue = false, required = false, description = "The model name (i.e., 'WRANGLER')"),
           @Parameter(name = "trim", allowEmptyValue = false, required = false, description = "The trim level (i.e., 'Sport')")
      }
      
   )
 
  /*
   * Map get request to /jeeps to fetchJeeps method using GetMapping
   */
  @GetMapping // GET 
  @ResponseStatus(code = HttpStatus.OK)
  List<Jeep> fetchJeeps(
      @RequestParam(required = false) JeepModel model, 
      @Length(max = Constants.TRIM_MAX_LENGTH)
      @Pattern(regexp = "[\\w\\s]*")
      @RequestParam(required = false) String trim);
  //@formatter:on
  
  
  
  /*
   * Spring will map all requests with /jeeps to the JeepSalesController class
   * Spring will map all getRequests with /jeeps to the FetchJeeps method
   */
}
