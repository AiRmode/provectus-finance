package com.provectus.taxmanagement.configuration;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Set;

/**
 * Created by alexey on 11.03.17.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.provectus.taxmanagement.repository")
@ComponentScan(basePackages = {"com.provectus.taxmanagement.service"})
public class TestTaxManagementConfiguration {

    @Bean
    public MongoOperations mongoTemplate() {
        MongoClient mongoClient = new MongoClient();
        MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoDbFactory(mongoClient, "test"));
        dropCollections(mongoTemplate);
        return mongoTemplate;
    }

    private void dropCollections(MongoTemplate mongoTemplate) {
        Set<String> collectionNames = mongoTemplate.getDb().getCollectionNames();
        for (String name : collectionNames) {
            if (name.contains("system")) {
                continue;
            }
            mongoTemplate.dropCollection(name);
        }
    }

    @Bean
    public MongoMappingContext mappingContext() {
        return new MongoMappingContext();
    }
}
