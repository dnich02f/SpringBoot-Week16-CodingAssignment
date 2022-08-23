package com.promineotech.jeep.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.OrderRequest;
import com.promineotech.jeep.service.JeepOrderService;
import lombok.extern.slf4j.Slf4j;

/*
 * Step 22: implement the createOrder method as declared in the JeepOrderController interface
 * Step 23: add @RestController, and log the line that says the createOrder method is there
 * with Slf4j which is from Lombok
 * Step 24: go to scr/test/jeep.controller.CreateOrderTest
 * Step 28: Create an JeepOrderService interface and put it in the service package
 * Step 29: code createOrder to return createOrder from the JeepOrderService interface
 */





@RestController
@Slf4j
public class BasicJeepOrderController implements JeepOrderController {

  @Autowired
  private JeepOrderService jeepOrderService;

  @Override
  public Order createOrder(OrderRequest orderRequest) {
    log.debug("Order={}", orderRequest);
    return jeepOrderService.createOrder(orderRequest);
  }

}
