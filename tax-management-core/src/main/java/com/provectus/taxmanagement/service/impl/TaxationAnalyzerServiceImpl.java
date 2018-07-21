package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.entity.TaxationWordAnalyticsDetails;
import com.provectus.taxmanagement.enums.TaxRecordTaxationStatus;
import com.provectus.taxmanagement.repository.TaxationDescriptionsRepository;
import com.provectus.taxmanagement.service.TaxationAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaxationAnalyzerServiceImpl implements TaxationAnalyzerService {

    public static final String SPLIT_BY_SPACES = " ";
    public static final int TRESHOLD = 10;
    public static final int MODIFIER = 1;
    public static final int WEIGHT_LIMIT = 70;

    @Autowired
    private TaxationDescriptionsRepository taxationDescriptionsRepository;

    @Override
    public Quarter classifyTaxStatuses(Quarter quarter) {
        for (TaxRecord taxRecord : quarter.getTaxRecords()) {
            Set<String> words = parseWords(taxRecord);
            saveUnique(words);
            Iterable<TaxationWordAnalyticsDetails> all = getTaxationWordAnalyticsDetails(words);
            int weight = getSumWeight(all);
            setTaxationStatus(taxRecord, weight);
        }
        return quarter;
    }

    private Iterable<TaxationWordAnalyticsDetails> getTaxationWordAnalyticsDetails(Set<String> words) {
        return taxationDescriptionsRepository.findByWordIn(words);
    }

    @Override
    public Quarter trainModelBasedOnUserData(Quarter quarter) {
        Iterator<TaxRecord> taxRecords = quarter.getTaxRecords().iterator();
        while (taxRecords.hasNext()) {
            TaxRecord taxRecord = taxRecords.next();
            if (taxRecord.getTaxationStatus() == TaxRecordTaxationStatus.APPROVED) {
                changeWeight(taxRecord, MODIFIER);
            } else if (taxRecord.getTaxationStatus() == TaxRecordTaxationStatus.REJECTED) {
                changeWeight(taxRecord, -MODIFIER);
                taxRecords.remove();
            }
        }
        return quarter;
    }

    private void changeWeight(TaxRecord taxRecord, int weightValue) {
        Iterable<TaxationWordAnalyticsDetails> taxationWordAnalyticsDetails = getTaxationWordAnalyticsDetails(parseWords(taxRecord));
        taxationWordAnalyticsDetails.forEach(word -> {
            int weight = word.getWeight();
            if (!isReachedWeightLimit(weight)) {
                weight = weight + weightValue;
                word.setWeight(weight);
                taxationDescriptionsRepository.save(word);
            }
        });
    }

    private boolean isReachedWeightLimit(int weight) {
        return weight >= WEIGHT_LIMIT || weight <= -WEIGHT_LIMIT;
    }

    private int getSumWeight(Iterable<TaxationWordAnalyticsDetails> all) {
        int weight = 0;
        for (TaxationWordAnalyticsDetails details : all) {
            weight += details.getWeight();
        }
        return weight;
    }

    private void setTaxationStatus(TaxRecord taxRecord, int weight) {
        if (weight >= -TRESHOLD && weight <= TRESHOLD) {
            taxRecord.setTaxationStatus(TaxRecordTaxationStatus.UNDEFINED);
        } else if (weight < -TRESHOLD) {
            taxRecord.setTaxationStatus(TaxRecordTaxationStatus.REJECTED);
        } else {
            taxRecord.setTaxationStatus(TaxRecordTaxationStatus.APPROVED);
        }
    }

    public void saveUnique(Set<String> result) {
        for (String string : result) {
            TaxationWordAnalyticsDetails wordObject = new TaxationWordAnalyticsDetails();
            wordObject.setWord(string);
            TaxationWordAnalyticsDetails byWord = taxationDescriptionsRepository.findByWord(string);
            if (byWord == null) {
                taxationDescriptionsRepository.save(wordObject);
            }
        }
    }

    private Set<String> parseWords(TaxRecord taxRecord) {
        String counterpartyName = taxRecord.getCounterpartyName();
        String paymentPurpose = taxRecord.getPaymentPurpose();
        List<String> counterparties = Arrays.asList(counterpartyName.split(SPLIT_BY_SPACES));
        List<String> purposes = Arrays.asList(paymentPurpose.split(SPLIT_BY_SPACES));
        Set<String> result = new HashSet<>();
        result.addAll(counterparties);
        result.addAll(purposes);
        return result;
    }

}
