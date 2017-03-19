package com.provectus.taxmanagement.contoller;

import com.provectus.taxmanagement.configuration.web.TaxManagementSpringBootTestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by alexey on 19.03.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TaxManagementSpringBootTestConfiguration.class})
@EnableAutoConfiguration
public class RootControllerTest {

    @LocalServerPort
    private int port;

    protected URL base;

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Before
    public void setUp() throws MalformedURLException {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void test() {
        ResponseEntity<String> response = testRestTemplate.getForEntity(base.toString(), String.class);
        assertThat(response.getBody(), equalTo("Hello World"));
    }
}
