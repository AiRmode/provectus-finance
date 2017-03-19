package com.provectus.taxmanagement.contoller;

import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by alexey on 19.03.17.
 */
@EnableAutoConfiguration
public class RootControllerTest extends ControllerTestParent {
    @Test
    public void test() {
        ResponseEntity<String> response = testRestTemplate.getForEntity(base.toString(), String.class);
        assertThat(response.getBody(), equalTo("Hello World"));
    }
}
