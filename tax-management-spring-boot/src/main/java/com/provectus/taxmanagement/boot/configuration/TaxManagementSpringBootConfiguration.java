package com.provectus.taxmanagement.boot.configuration;

import com.provectus.taxmanagement.configuration.web.WebConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by alexey on 19.03.17.
 */
@Configuration
@Import(WebConfiguration.class)
public class TaxManagementSpringBootConfiguration {
    @Bean
    public WebMvcConfigurer configurer () {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addResourceHandlers (ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/font/**").
                        addResourceLocations("classpath:/font/");
                registry.addResourceHandler("/build/**").
                        addResourceLocations("classpath:/build/");
                registry.addResourceHandler("/index.*/**").
                        addResourceLocations("classpath:/");
            }
        };
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }
}
