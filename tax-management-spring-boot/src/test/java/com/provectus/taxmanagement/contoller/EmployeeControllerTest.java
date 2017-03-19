package com.provectus.taxmanagement.contoller;

import com.provectus.taxmanagement.entity.Employee;
import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by alexey on 19.03.17.
 */
@EnableAutoConfiguration
public class EmployeeControllerTest extends RootControllerTest {
    @Test
    public void testSaveEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Test first name");
        employee.setSecondName("Test second name");
        employee.setLastName("Test last name");

        String path = "employee/";

        ResponseEntity<Employee> response = testRestTemplate.postForEntity(base.toString() + path, employee, Employee.class);
        Employee responseEmployee = response.getBody();
        assertTrue(responseEmployee.getFirstName().equals("Test first name"));
        assertTrue(responseEmployee.getSecondName().equals("Test second name"));
        assertTrue(responseEmployee.getLastName().equals("Test last name"));
    }

    @Test
    public void testSearch() {
        Employee employee = new Employee();
        employee.setFirstName("Test first name2");
        employee.setSecondName("Test second name2");
        employee.setLastName("Test last name2");

        String pathSearchUpperCase = "employee/search/Test";
        String pathSearchLowerCase = "employee/search/name";
        String pathSave = "employee/";

        //save employee
        ResponseEntity<Employee> response = testRestTemplate.postForEntity(base.toString() + pathSave, employee, Employee.class);
        assertNotNull(response);
        Employee body = response.getBody();
        assertNotNull(body.getId());

        //try to find it with upper case
        ResponseEntity<Employee[]> responseEntity = testRestTemplate.getForEntity(base.toString() + pathSearchUpperCase, Employee[].class);
        Employee[] bodies = responseEntity.getBody();
        assertFalse(bodies.length == 0);
        for (Employee e : bodies) {
            assertNotNull(e.getId());
            assertTrue(e.getFirstName().contains("Test") || e.getLastName().contains("Test") || e.getSecondName().contains("Test"));
        }

        //try to find it with upper case
        responseEntity = testRestTemplate.getForEntity(base.toString() + pathSearchLowerCase, Employee[].class);
        bodies = responseEntity.getBody();
        assertFalse(bodies.length == 0);
        for (Employee e : bodies) {
            assertNotNull(e.getId());
            assertTrue(e.getFirstName().contains("name") || e.getLastName().contains("name") || e.getSecondName().contains("name"));
        }
    }
}
