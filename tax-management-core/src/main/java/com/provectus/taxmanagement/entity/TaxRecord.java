package com.provectus.taxmanagement.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by alexey on 10.03.17.
 */
@Document(collection = "taxRecords")
public class TaxRecord implements Serializable {
    @Id
    private ObjectId id;
    @Version
    private Long version;
    private String counterpartyName;
    @Indexed
    private Date receivingDate;
    private Double uahRevenue = 0d;
    private Double usdRevenue = 0d;
    private Double exchRateUsdUahNBUatReceivingDate = 0d;
    private Integer taxPercentage = Employee.TAX_PERCENTAGE_EMPLOYEE_3th_CATEGORY;//default current value

    private Date createdDate;
    private Date modifiedDate;

    public String getId() {
        return id == null ? ObjectId.get().toString() : id.toString();
    }

    public void setId(String id) {
        this.id = new ObjectId(id);
    }

    public String getCounterpartyName() {
        return counterpartyName;
    }

    public void setCounterpartyName(String counterpartyName) {
        this.counterpartyName = counterpartyName;
    }

    public Date getReceivingDate() {
        return receivingDate;
    }

    public void setReceivingDate(Date receivingDate) {
        this.receivingDate = receivingDate;
    }

    public Double getUahRevenue() {
        return uahRevenue;
    }

    public void setUahRevenue(Double uahRevenue) {
        this.uahRevenue = uahRevenue;
    }

    public Double getUsdRevenue() {
        return usdRevenue;
    }

    public void setUsdRevenue(Double usdRevenue) {
        this.usdRevenue = usdRevenue;
    }

    public Double getExchRateUsdUahNBUatReceivingDate() {
        return exchRateUsdUahNBUatReceivingDate;
    }

    public void setExchRateUsdUahNBUatReceivingDate(Double exchRateUsdUahNBUatReceivingDate) {
        this.exchRateUsdUahNBUatReceivingDate = exchRateUsdUahNBUatReceivingDate;
    }

    public Integer getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(Integer taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaxRecord taxRecord = (TaxRecord) o;

        if (id != null ? !id.equals(taxRecord.id) : taxRecord.id != null) return false;
        if (version != null ? !version.equals(taxRecord.version) : taxRecord.version != null) return false;
        if (counterpartyName != null ? !counterpartyName.equals(taxRecord.counterpartyName) : taxRecord.counterpartyName != null)
            return false;
        if (receivingDate != null ? !receivingDate.equals(taxRecord.receivingDate) : taxRecord.receivingDate != null)
            return false;
        if (uahRevenue != null ? !uahRevenue.equals(taxRecord.uahRevenue) : taxRecord.uahRevenue != null) return false;
        if (usdRevenue != null ? !usdRevenue.equals(taxRecord.usdRevenue) : taxRecord.usdRevenue != null) return false;
        if (exchRateUsdUahNBUatReceivingDate != null ? !exchRateUsdUahNBUatReceivingDate.equals(taxRecord.exchRateUsdUahNBUatReceivingDate) : taxRecord.exchRateUsdUahNBUatReceivingDate != null)
            return false;
        if (taxPercentage != null ? !taxPercentage.equals(taxRecord.taxPercentage) : taxRecord.taxPercentage != null)
            return false;
        if (createdDate != null ? !createdDate.equals(taxRecord.createdDate) : taxRecord.createdDate != null)
            return false;
        return !(modifiedDate != null ? !modifiedDate.equals(taxRecord.modifiedDate) : taxRecord.modifiedDate != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (counterpartyName != null ? counterpartyName.hashCode() : 0);
        result = 31 * result + (receivingDate != null ? receivingDate.hashCode() : 0);
        result = 31 * result + (uahRevenue != null ? uahRevenue.hashCode() : 0);
        result = 31 * result + (usdRevenue != null ? usdRevenue.hashCode() : 0);
        result = 31 * result + (exchRateUsdUahNBUatReceivingDate != null ? exchRateUsdUahNBUatReceivingDate.hashCode() : 0);
        result = 31 * result + (taxPercentage != null ? taxPercentage.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        return result;
    }
}
