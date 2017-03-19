package com.provectus.taxmanagement.repository;

import com.provectus.taxmanagement.entity.Employee;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by alexey on 10.03.17.
 */
public interface EmployeeRepository extends MongoRepository<Employee, ObjectId> {
    List<Employee> findByFirstNameLikeIgnoreCase(String firstName);
    List<Employee> findByLastNameLikeIgnoreCase(String lastName);
    List<Employee> findBySecondNameLikeIgnoreCase(String secondName);
}
