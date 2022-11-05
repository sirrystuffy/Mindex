package com.mindex.challenge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ReportingServiceImpl implements ReportingService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure getStructure(String id) {
        Employee employee = employeeRepository.findByEmployeeId(id);
        int numberOfReports = getReports(id, 0);

        return new ReportingStructure(employee, numberOfReports);
    }

    public int getReports(String id, int num) {
        LOG.debug("Getting reports for employee with id [{}]", id);
        int temp = 0;
        Employee emp = employeeRepository.findByEmployeeId(id);
        // Check if employee has direct reports
        if (emp.getDirectReports() == null) {
            return 0;
        }
        // increment number for each direct report
        for (Employee reportEmp : emp.getDirectReports()) {
            num++;
            // getReports will return a greater num if these employees also have
            // directReports
            temp = getReports(reportEmp.getEmployeeId(), num);
        }

        return (temp > num) ? temp : num;
    }

}
