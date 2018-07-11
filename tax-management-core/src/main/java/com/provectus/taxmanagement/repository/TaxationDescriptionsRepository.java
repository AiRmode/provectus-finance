package com.provectus.taxmanagement.repository;

import com.provectus.taxmanagement.entity.TaxationAnalyticsDetails;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaxationDescriptionsRepository extends MongoRepository<TaxationAnalyticsDetails, ObjectId> {
}
