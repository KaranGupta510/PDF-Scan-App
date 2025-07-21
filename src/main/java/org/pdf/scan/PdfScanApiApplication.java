package org.pdf.scan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PdfScanApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(PdfScanApiApplication.class, args);
  }
}
