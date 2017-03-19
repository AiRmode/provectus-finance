package com.provectus.taxmanagement.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by alexey on 12.03.17.
 */
@Document(collection = "properties")
public class Property implements Serializable {
    @Id
    private String id;
    private String key;
    private String value;
}
