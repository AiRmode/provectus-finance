package com.provectus.taxmanagement.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Document(collection = "taxationWordAnalyticsDetails")
public class TaxationWordAnalyticsDetails implements Serializable, Comparable<TaxationWordAnalyticsDetails> {

    @Id
    private ObjectId id;
    
    @Indexed(unique = true)
    private String word;
    private int weight;

    @Version
    private long version;

    public String getId() {
        return id == null ? ObjectId.get().toString() : id.toString();
    }

    public void setId(String id) {
        this.id = new ObjectId(id);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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
    public int compareTo(TaxationWordAnalyticsDetails o) {
        if (this.hashCode() == o.hashCode() && this.equals(o)) {
            return 0;
        } else if (word.compareTo(o.getWord()) > 0) {
            return 1;
        } else
            return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxationWordAnalyticsDetails that = (TaxationWordAnalyticsDetails) o;
        return version == that.version &&
                Objects.equals(id, that.id) &&
                Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word, version);
    }
}
