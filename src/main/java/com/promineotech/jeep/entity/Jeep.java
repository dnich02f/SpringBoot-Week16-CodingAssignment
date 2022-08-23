package com.promineotech.jeep.entity;

import java.math.BigDecimal;
import java.util.Comparator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Getter
//@Setter
//@EqualsAndHashCode
//@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

/*
 * This adds sorting capability so that our test doesn't break
 */
public class Jeep implements Comparable<Jeep>{
  private Long modelPK;
  private JeepModel modelId;
  private String trimLevel;
  private int numDoors;
  private int wheelSize;
  private BigDecimal basePrice;
  
  
  @JsonIgnore
  public Long getModelPK() {
    return modelPK;
  }


  @Override
  public int compareTo(Jeep that) {

    /*
     * :: is a method reference operator
     */
    return Comparator.comparing(Jeep::getModelId).thenComparing(Jeep::getTrimLevel)
        .thenComparing(Jeep::getNumDoors).compare(this, that);
  }
}
