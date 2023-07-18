package com.tonykang22.ordinaryboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class OrdinaryBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdinaryBoardApplication.class, args);
    }

}
