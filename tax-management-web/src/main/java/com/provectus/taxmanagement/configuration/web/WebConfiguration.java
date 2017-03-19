package com.provectus.taxmanagement.configuration.web;

import com.provectus.taxmanagement.configuration.TaxManagementConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by alexey on 19.03.17.
 */
@Configuration
@EnableWebMvc
@Import(TaxManagementConfiguration.class)
@ComponentScan(basePackages = {"com.provectus.taxmanagement.controller", "com.provectus.taxmanagement.exception"})
public class WebConfiguration {
}
