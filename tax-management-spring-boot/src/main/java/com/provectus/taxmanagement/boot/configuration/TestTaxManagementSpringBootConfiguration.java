package com.provectus.taxmanagement.boot.configuration;

import com.provectus.taxmanagement.configuration.web.TestWebConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by alexey on 19.03.17.
 */
@Configuration
@Import(TestWebConfiguration.class)
public class TestTaxManagementSpringBootConfiguration {
}
