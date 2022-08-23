package com.promineotech.jeep.entity;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

/*
 * Step 19: creating Tire class to complete the Order class for an Order object
 */

@Builder
@Data
public class Tire {
  private Long tirePK;
  private String tireId;
  private String tireSize;
  private String manufacturer;
  private BigDecimal price;
  private int warrantyMiles;
  public Tire orElseThrow(Object object) {
    // TODO Auto-generated method stub
    return null;
  }
}
