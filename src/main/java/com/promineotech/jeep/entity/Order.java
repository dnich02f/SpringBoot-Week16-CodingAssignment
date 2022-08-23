package com.promineotech.jeep.entity;
/*
 * Step 18: Create an Order class. Add @Data for Getters and Setters. We want all the things in 
 * the Order Request. 
 * Step 19: Add Customer customer, create a Customer class in src/main/jeep.entity,
 * Jeep class, Color class, Engine class, etc
 */

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Order {
  private Long orderPK;
  private Customer customer;
  private Jeep model;
  private Color color;
  private Engine engine;
  private Tire tire;
  private List<Option> options;
  private BigDecimal price;
}
