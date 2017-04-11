package com.provectus.taxmanagement.contoller;

import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by alexey on 19.03.17.
 */
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, EmbeddedMongoAutoConfiguration.class, MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class RootControllerTest extends ControllerTestParent {
    @Test
    public void test() {
        ResponseEntity<String> response = testRestTemplate.getForEntity(base.toString(), String.class);
        assertEquals(response.getStatusCode().value(), 200);
        assertFalse(response.getBody().isEmpty());
    }
}
