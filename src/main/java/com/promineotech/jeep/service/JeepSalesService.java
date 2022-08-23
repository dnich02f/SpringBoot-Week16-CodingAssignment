package com.promineotech.jeep.service;

import java.util.List;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;

public interface JeepSalesService {

  /**
   * @param model
   * @param trim
   * @return
   * 
   * why create the interface? If one person was working on the Controller layer and another
   * worked on the Service layer, this interface provides definition and structure for each
   * developer to continue working together, interfaces are easy to mock
   * 
   */
  
  List<Jeep> fetchJeeps(JeepModel model, String trim);

}
 