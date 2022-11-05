package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    private String compensationIdUrl;
    private String compensationUrl;

    @Autowired
    private EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/comp";
        compensationIdUrl = "http://localhost:" + port + "/comp/{id}";
    }

    @Test
    public void testCompensation() {
        LocalDate date = LocalDate.of(1983, Calendar.SEPTEMBER, 25);
        Employee employee = employeeRepository.findByEmployeeId("b7839309-3348-463b-a7e3-5de1c168beb3");
        // make a test compensation object
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(employee);
        testCompensation.setSalary(50.00);
        testCompensation.setEffectiveDate(date);

        // Create Checks
        Compensation createdCompensation = restTemplate
                .postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();
        assertEmployeeEquivalence(testCompensation.getEmployee(), createdCompensation.getEmployee());
        assertCompensationEquivalence(testCompensation, createdCompensation);

        // Read Checks
        Compensation readCompensation = restTemplate
                .getForEntity(compensationIdUrl, Compensation.class, createdCompensation.getEmployee().getEmployeeId())
                .getBody();
        assertEmployeeEquivalence(createdCompensation.getEmployee(), readCompensation.getEmployee());
        assertCompensationEquivalence(createdCompensation, readCompensation);
    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
