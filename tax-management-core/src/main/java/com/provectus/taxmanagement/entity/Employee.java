package com.provectus.taxmanagement.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.*;

/**
 * Created by alexey on 10.03.17.
 */
@Document(collection = "employees")
public class Employee implements Serializable {
    public static final int TAX_PERCENTAGE_EMPLOYEE_3th_CATEGORY = 5;

    @Id
    private ObjectId id;
    @Version
    private Long version;
    @Indexed
    private String firstName;
    @Indexed
    private String lastName;
    @Indexed
    private String secondName;
    private String comment;
    @Indexed
    private String department;
    @Indexed
    private String email;
    private Integer taxPercentage = TAX_PERCENTAGE_EMPLOYEE_3th_CATEGORY;//default current value
    private List<String> kved;
    @DBRef
    private Set<Quarter> quartersSet = new TreeSet<>();

    private Date createdDate;
    private Date modifiedDate;

    public String getId() {
        return id == null ? ObjectId.get().toString() : id.toString();
    }

    public void setId(String id) {
        this.id = new ObjectId(id);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(Integer taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public List<String> getKved() {
        return kved;
    }

    public void setKved(List<String> kved) {
        this.kved = kved;
    }

    public Set<Quarter> getQuartersSet() {
        return quartersSet;
    }

    public void setQuartersSet(Set<Quarter> quartersSet) {
        this.quartersSet = quartersSet;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != null ? !id.equals(employee.id) : employee.id != null) return false;
        if (version != null ? !version.equals(employee.version) : employee.version != null) return false;
        if (firstName != null ? !firstName.equals(employee.firstName) : employee.firstName != null) return false;
        if (lastName != null ? !lastName.equals(employee.lastName) : employee.lastName != null) return false;
        if (secondName != null ? !secondName.equals(employee.secondName) : employee.secondName != null) return false;
        if (comment != null ? !comment.equals(employee.comment) : employee.comment != null) return false;
        if (department != null ? !department.equals(employee.department) : employee.department != null) return false;
        if (email != null ? !email.equals(employee.email) : employee.email != null) return false;
        if (taxPercentage != null ? !taxPercentage.equals(employee.taxPercentage) : employee.taxPercentage != null)
            return false;
        if (kved != null ? !kved.equals(employee.kved) : employee.kved != null) return false;
        if (quartersSet != null ? !quartersSet.equals(employee.quartersSet) : employee.quartersSet != null)
            return false;
        if (createdDate != null ? !createdDate.equals(employee.createdDate) : employee.createdDate != null)
            return false;
        return !(modifiedDate != null ? !modifiedDate.equals(employee.modifiedDate) : employee.modifiedDate != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (taxPercentage != null ? taxPercentage.hashCode() : 0);
        result = 31 * result + (kved != null ? kved.hashCode() : 0);
        result = 31 * result + (quartersSet != null ? quartersSet.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        return result;
    }
}
