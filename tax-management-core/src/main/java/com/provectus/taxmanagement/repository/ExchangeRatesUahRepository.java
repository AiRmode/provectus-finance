package com.provectus.taxmanagement.repository;

import com.provectus.taxmanagement.entity.ExchangeRateUah;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.sql.Date;

/**
 * Created by alexey on 28.03.17.
 */
public interface ExchangeRatesUahRepository extends MongoRepository<ExchangeRateUah, ObjectId> {
    ExchangeRateUah findByCurrencyTypeAndExchangeRateDate(String currencyType, Date exchangeRateDate);
}
