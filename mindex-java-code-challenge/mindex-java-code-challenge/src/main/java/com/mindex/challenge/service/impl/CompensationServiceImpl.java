package com.mindex.challenge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Compensation create(Compensation compensation) {
        // The compensation being created should be for an existing employee -- make a
        // new employee through the '/employee' endpoint
        LOG.debug("Creating compensation for employee with id [{}]: ", compensation.getEmployee().getEmployeeId());

        Employee emp = employeeRepository.findByEmployeeId(compensation.getEmployee().getEmployeeId());

        if (emp == null) {
            throw new RuntimeException(
                    "Invalid employee with employeeId: " + compensation.getEmployee().getEmployeeId());
        }

        emp.setCompensation(compensation);
        employeeRepository.save(emp);

        return emp.getCompensation();
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Reading Compensation with employee id [{}]", id);
        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee.getCompensation();
    }

}
