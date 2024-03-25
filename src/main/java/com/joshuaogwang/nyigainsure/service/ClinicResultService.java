package com.joshuaogwang.nyigainsure.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joshuaogwang.nyigainsure.entity.ClinicResult;
import com.joshuaogwang.nyigainsure.repository.ClinicResultRepository;

@Service
public class ClinicResultService {
    @Autowired
    private ClinicResultRepository clinicResultRepository;

    public ClinicResultService(ClinicResultRepository clinicResultRepository) {
        this.clinicResultRepository = clinicResultRepository;
    }

    public ClinicResult saveClinicResult(ClinicResult clinicResult) {
        return clinicResultRepository.save(clinicResult);
    }

    public ClinicResult getClinicResultById(Long id) {
        return clinicResultRepository.findById(id).orElse(null);
    }

    public ClinicResult updateClinicResult(ClinicResult clinicResult) {
        return clinicResultRepository.save(clinicResult);
    }

    public void deleteClinicResult(Long id) {
        clinicResultRepository.deleteById(id);
    }

    public Iterable<ClinicResult> getAllClinicResults() {
        return clinicResultRepository.findAll();
    }

    public String patientPolicyOptions(String uniqueMemberIdentifier) {
        ClinicResult clinicResult = clinicResultRepository.findByUserAssessmentId(uniqueMemberIdentifier);

        String recommendedCat = clinicResult.getClinicRecommendation();
        return recommendedCat;
    }

}
