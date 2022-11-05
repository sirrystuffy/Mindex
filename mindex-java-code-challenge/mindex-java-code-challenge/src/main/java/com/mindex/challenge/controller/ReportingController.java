package com.mindex.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ReportingController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingController.class);
    @Autowired
    private ReportingService reportingService;

    @GetMapping("/reporting/{id}")
    public ReportingStructure reports(@PathVariable String id) {
        LOG.debug("Recevied get request for number of reports for emploee with id [{}] ", id);
        return reportingService.getStructure(id);
    }
}
