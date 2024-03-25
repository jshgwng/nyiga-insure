package com.joshuaogwang.nyigainsure.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class ClinicResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userAssessmentId;
    private String clinicId;
    private List<String> clinicResults;
    private String clinicResultDate;
    private String clinicRecommendation;

    public ClinicResult() {
    }

    public ClinicResult(Long id, String userAssessmentId, String clinicId, List<String> clinicResults,
            String clinicResultDate, String clinicRecommendation) {
        this.id = id;
        this.userAssessmentId = userAssessmentId;
        this.clinicId = clinicId;
        this.clinicResults = clinicResults;
        this.clinicResultDate = clinicResultDate;
        this.clinicRecommendation = clinicRecommendation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserAssessmentId() {
        return userAssessmentId;
    }

    public void setUserAssessmentId(String userAssessmentId) {
        this.userAssessmentId = userAssessmentId;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public List<String> getClinicResults() {
        return clinicResults;
    }

    public void setClinicResults(List<String> clinicResults) {
        this.clinicResults = clinicResults;
    }

    public String getClinicResultDate() {
        return clinicResultDate;
    }

    public void setClinicResultDate(String clinicResultDate) {
        this.clinicResultDate = clinicResultDate;
    }

    public String getClinicRecommendation() {
        return clinicRecommendation;
    }

    public void setClinicRecommendation(String clinicRecommendation) {
        this.clinicRecommendation = clinicRecommendation;
    }

    
}
