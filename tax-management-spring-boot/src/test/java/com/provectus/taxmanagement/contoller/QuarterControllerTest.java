package com.provectus.taxmanagement.contoller;

import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;

/**
 * Created by alexey on 05.04.17.
 */
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, EmbeddedMongoAutoConfiguration.class, MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class QuarterControllerTest extends ControllerTestParent {

    @Test
    public void testSaveQuarter() {

    }
}
