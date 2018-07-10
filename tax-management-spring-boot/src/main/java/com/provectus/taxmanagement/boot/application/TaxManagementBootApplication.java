package com.provectus.taxmanagement.boot.application;

import com.provectus.taxmanagement.boot.configuration.TaxManagementSpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Created by alexey on 19.03.17.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, EmbeddedMongoAutoConfiguration.class, MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, MultipartAutoConfiguration.class})
@Import(TaxManagementSpringBootConfiguration.class)
public class TaxManagementBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaxManagementBootApplication.class, args);
    }
}