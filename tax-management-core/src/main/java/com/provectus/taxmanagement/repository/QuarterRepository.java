package com.provectus.taxmanagement.repository;

import com.provectus.taxmanagement.entity.Quarter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by alexey on 12.03.17.
 */
public interface QuarterRepository extends MongoRepository<Quarter, ObjectId> {
    Quarter findByQuarterDefinition(Quarter.QuarterDefinition definition);
}
