package com.provectus.taxmanagement.repository;

import com.provectus.taxmanagement.entity.TaxationWordAnalyticsDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaxationDescriptionsRepository extends MongoRepository<TaxationWordAnalyticsDetails, String> {
    TaxationWordAnalyticsDetails findByWord(String word);

    Iterable<TaxationWordAnalyticsDetails> findByWordIn(Iterable<String> name);
}
