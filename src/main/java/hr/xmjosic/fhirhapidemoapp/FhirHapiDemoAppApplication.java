package hr.xmjosic.fhirhapidemoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "hr.xmjosic")
public class FhirHapiDemoAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(FhirHapiDemoAppApplication.class, args);
  }
}
