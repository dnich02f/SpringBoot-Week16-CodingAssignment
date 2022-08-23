package com.promineotech.jeep.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.service.JeepSalesService;
import lombok.extern.slf4j.Slf4j;

/*
 * Implementing the JeepSalesController interface
 * You have to tell Spring that this controller is a REST controller. This has to be done in the class
 * not the interface.
 * Now Spring knows that this class is something special and it's a controller that implements JeepSalesController
 * So Spring will go the interface from here and follow that OpenApi Documentation
 */

@RestController
@Slf4j // this is a LOMBOK annotation and created log of type Logger
public class BasicJeepSalesController implements JeepSalesController {
  
  
  @Autowired // we want an objected to be injected here
  private JeepSalesService jeepSalesService;
  
  @Override
  public List<Jeep> fetchJeeps(JeepModel model, String trim) {
    log.debug("model={}, trim={}", model, trim);
    return jeepSalesService.fetchJeeps(model, trim);
  }

}
