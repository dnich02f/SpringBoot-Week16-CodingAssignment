package com.promineotech.jeep.entity;

import lombok.Builder;
import lombok.Data;

/*
 * Step 19: Create a Customer class. Look in the schema and see what the fields of the Customer 
 * table has
 */

@Data
@Builder
public class Customer {
  private Long customerPK;
  private String customerId;
  private String firstName;
  private String lastName;
  private String phone;
}
