package com.provectus.taxmanagement.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

/**
 * Created by alexey on 12.03.17.
 */
@Document(collection = "localizations")
public class Localization implements Serializable {
    @Id
    private String id;
    private String key;
    private Map<Locale, String> locales;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<Locale, String> getLocales() {
        return locales;
    }

    public void setLocales(Map<Locale, String> locales) {
        this.locales = locales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Localization that = (Localization) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        return !(locales != null ? !locales.equals(that.locales) : that.locales != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (locales != null ? locales.hashCode() : 0);
        return result;
    }
}
