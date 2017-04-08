package com.provectus.taxmanagement.repository;

import com.provectus.taxmanagement.entity.TaxRecord;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by alexey on 10.03.17.
 */
public interface TaxRecordRepository extends MongoRepository<TaxRecord, ObjectId> {

}
