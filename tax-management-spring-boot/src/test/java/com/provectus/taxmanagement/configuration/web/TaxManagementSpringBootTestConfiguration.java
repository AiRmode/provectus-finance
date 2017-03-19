package com.provectus.taxmanagement.configuration.web;

import com.provectus.taxmanagement.boot.configuration.TestTaxManagementSpringBootConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by alexey on 19.03.17.
 */
@Configuration
@Import(TestTaxManagementSpringBootConfiguration.class)
public class TaxManagementSpringBootTestConfiguration {
}
