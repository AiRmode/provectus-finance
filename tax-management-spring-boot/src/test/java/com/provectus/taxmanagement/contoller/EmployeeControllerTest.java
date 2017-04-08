package com.provectus.taxmanagement.contoller;

import com.provectus.taxmanagement.entity.Employee;
import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by alexey on 19.03.17.
 */
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, EmbeddedMongoAutoConfiguration.class, MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class EmployeeControllerTest extends ControllerTestParent {
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

    @Test
    public void testPageRequest() {
        String path = "employee/employees";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(base.toString() + path);
        builder.queryParam("page", "0");
        builder.queryParam("size", "22");

        ResponseEntity<PagedResources> exchange = testRestTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, PagedResources.class);
        Collection content = exchange.getBody().getContent();
        assertFalse(content.isEmpty());
    }

    @Test
    public void testUpdate() {
        Employee employee = new Employee();
        employee.setFirstName("Terminator");
        employee.setSecondName("T1000");
        employee.setLastName("Good guy");

        String path = "employee/";

        ResponseEntity<Employee> response = testRestTemplate.postForEntity(base.toString() + path, employee, Employee.class);
        Employee responseEmployee = response.getBody();
        assertTrue(responseEmployee.getFirstName().equals("Terminator"));
        assertTrue(responseEmployee.getSecondName().equals("T1000"));

        //update employee object and put it into the system
        path = "employee/{id}";
        String url = base.toString() + path;
        employee.setLastName("I'll be back");

        Map<String, String> params = new HashMap<>();
        params.put("id", responseEmployee.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Employee> entity = new HttpEntity<Employee>(employee, headers);

        ResponseEntity<Employee> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, entity, Employee.class, params);

        Employee body = responseEntity.getBody();
        assertEquals(body.getLastName(), "I'll be back");
    }
}
