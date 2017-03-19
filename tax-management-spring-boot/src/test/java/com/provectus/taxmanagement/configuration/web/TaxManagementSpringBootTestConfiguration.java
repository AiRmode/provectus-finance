package com.provectus.taxmanagement.configuration.web;

import com.provectus.taxmanagement.boot.configuration.TaxManagementSpringBootConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by alexey on 19.03.17.
 */
@Configuration
@Import(TaxManagementSpringBootConfiguration.class)
public class TaxManagementSpringBootTestConfiguration {
}
