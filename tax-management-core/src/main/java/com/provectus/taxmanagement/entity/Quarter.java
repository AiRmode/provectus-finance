package com.provectus.taxmanagement.entity;

import com.provectus.taxmanagement.enums.QuarterName;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alexey on 12.03.17.
 */
@Document(collection = "quarters")
public class Quarter implements Serializable, Comparable<Quarter> {
    @Id
    private ObjectId id;
    private QuarterDefinition quarterDefinition;

    @DBRef
    private List<TaxRecord> taxRecords = new ArrayList<>();

    @Version
    private long version;
    private Date createDate;
    private Date modifiedDate;

    @Override
    public int compareTo(Quarter o) {
        return quarterDefinition.compareTo(o.getQuarterDefinition());
    }

    public void addTaxRecords(List<TaxRecord> taxRecords) {
        this.taxRecords.addAll(taxRecords);
    }

    /**
     * Just a workaround. There is an issue with sending multipart request with a payload
     */
    public static class QuarterDefinitionDTO extends QuarterDefinition {
        private MultipartFile file;

        public MultipartFile getFile() {
            return file;
        }

        public void setFile(MultipartFile file) {
            this.file = file;
        }
    }

    public static class QuarterDefinition implements Comparable<QuarterDefinition> {
        private QuarterName quarterName;
        private Integer year;

        public QuarterDefinition(String quarterName, Integer year) {
            this.year = year;
            this.quarterName = QuarterName.valueOf(quarterName.toUpperCase());
        }

        public QuarterDefinition() {
        }

        public QuarterName getQuarterName() {
            return quarterName;
        }

        public void setQuarterName(QuarterName quarterName) {
            this.quarterName = quarterName;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            QuarterDefinition that = (QuarterDefinition) o;

            if (quarterName != that.quarterName) return false;
            return !(year != null ? !year.equals(that.year) : that.year != null);

        }

        @Override
        public int hashCode() {
            int result = quarterName != null ? quarterName.hashCode() : 0;
            result = 31 * result + (year != null ? year.hashCode() : 0);
            return result;
        }

        @Override
        public int compareTo(QuarterDefinition o) {
            if (year.compareTo(o.getYear()) == 0) {
                return o.getQuarterName().compareTo(quarterName);
            } else return o.getYear().compareTo(year);
        }
    }

    public Quarter() {
    }

    public Quarter(QuarterDefinition quarterDefinition) {
        this.quarterDefinition = quarterDefinition;
    }

    public void addTaxRecord(TaxRecord taxRecord) {
        taxRecords.add(taxRecord);
    }

    public String getId() {
        return id == null ? ObjectId.get().toString() : id.toString();
    }

    public void setId(String id) {
        this.id = new ObjectId(id);
    }

    public QuarterDefinition getQuarterDefinition() {
        return quarterDefinition;
    }

    public void setQuarterDefinition(QuarterDefinition quarterDefinition) {
        this.quarterDefinition = quarterDefinition;
    }

    public List<TaxRecord> getTaxRecords() {
        return taxRecords;
    }

    public void setTaxRecords(List<TaxRecord> taxRecords) {
        this.taxRecords = taxRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quarter quarter = (Quarter) o;

        if (id != null ? !id.equals(quarter.id) : quarter.id != null) return false;
        if (quarterDefinition != null ? !quarterDefinition.equals(quarter.quarterDefinition) : quarter.quarterDefinition != null)
            return false;
        if (taxRecords != null ? !taxRecords.equals(quarter.taxRecords) : quarter.taxRecords != null) return false;
        if (createDate != null ? !createDate.equals(quarter.createDate) : quarter.createDate != null) return false;
        return !(modifiedDate != null ? !modifiedDate.equals(quarter.modifiedDate) : quarter.modifiedDate != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (quarterDefinition != null ? quarterDefinition.hashCode() : 0);
        result = 31 * result + (taxRecords != null ? taxRecords.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        return result;
    }
}
