package com.promineotech.jeep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.promineotech.ComponentScanMarker;

/*
 * @SpringBootApplication starts SpringBoot
 * You can specify what the component scanner scans
 * An interface was created in the com.promineotech package and this interface
 * ComponentScanMarker will negate the possibility of misspelling even if the packages are renamed.
 * If you packages aren't being found, check the ComponentScanner class that you've created.
 */
@SpringBootApplication(scanBasePackageClasses = { ComponentScanMarker.class })
public class JeepSales {

  public static void main(String[] args) {
    SpringApplication.run(JeepSales.class, args);
  }

}
