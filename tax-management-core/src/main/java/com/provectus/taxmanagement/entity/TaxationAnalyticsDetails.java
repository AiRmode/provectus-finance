package com.provectus.taxmanagement.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Document(collection = "taxationDescriptions")
public class TaxationAnalyticsDetails implements Serializable, Comparable<TaxationAnalyticsDetails> {
    @Id
    private ObjectId id;
    private String counterparty;
    private String paymentPurpose;
    private int weight;

    @Version
    private long version;

    public String getId() {
        return id == null ? ObjectId.get().toString() : id.toString();
    }

    public void setId(String id) {
        this.id = new ObjectId(id);
    }

    public String getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(TaxationAnalyticsDetails o) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxationAnalyticsDetails that = (TaxationAnalyticsDetails) o;
        return version == that.version &&
                Objects.equals(id, that.id) &&
                Objects.equals(counterparty, that.counterparty) &&
                Objects.equals(paymentPurpose, that.paymentPurpose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, counterparty, paymentPurpose, version);
    }
}
