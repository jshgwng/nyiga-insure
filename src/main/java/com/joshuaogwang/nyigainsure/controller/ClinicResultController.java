package com.joshuaogwang.nyigainsure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joshuaogwang.nyigainsure.entity.ClinicResult;
import com.joshuaogwang.nyigainsure.service.ClinicResultService;

@RestController
@RequestMapping("/api/v1/clinicresult")
public class ClinicResultController {
    @Autowired
    private ClinicResultService clinicResultService;

    public ClinicResultController(ClinicResultService clinicResultService) {
        this.clinicResultService = clinicResultService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<ClinicResult> getAllClinicResults() {
        return clinicResultService.getAllClinicResults();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ClinicResult saveClinicResult(@RequestBody ClinicResult clinicResult) {
        return clinicResultService.saveClinicResult(clinicResult);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ClinicResult getClinicResultById(@RequestBody Long id) {
        return clinicResultService.getClinicResultById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ClinicResult updateClinicResult(@RequestBody ClinicResult clinicResult) {
        return clinicResultService.updateClinicResult(clinicResult);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deleteClinicResult(@RequestBody Long id) {
        clinicResultService.deleteClinicResult(id);
    }
}
