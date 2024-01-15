package com.odk.template.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.odk"})
public class OdkBootstrapApplication {

    public static void main(String[] args) {
        SpringApplication.run(OdkBootstrapApplication.class, args);
    }

}
