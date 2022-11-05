package com.mindex.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mindex.challenge.data.Compensation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mindex.challenge.service.CompensationService;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/comp")
    public Compensation create(@RequestBody Compensation comp) {
        LOG.debug("Received compensation create request for [{}]", comp.getEmployee());
        return compensationService.create(comp);
    }

    @GetMapping("/comp/{id}")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received compensation get request for id [{}]", id);

        return compensationService.read(id);
    }
}
