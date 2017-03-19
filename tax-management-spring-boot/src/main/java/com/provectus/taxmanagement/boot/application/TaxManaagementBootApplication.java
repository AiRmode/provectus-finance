package com.provectus.taxmanagement.boot.application;

import com.provectus.taxmanagement.boot.configuration.TaxManagementSpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by alexey on 19.03.17.
 */
@SpringBootApplication
@Import(TaxManagementSpringBootConfiguration.class)
public class TaxManaagementBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaxManaagementBootApplication.class, args);
    }
}
