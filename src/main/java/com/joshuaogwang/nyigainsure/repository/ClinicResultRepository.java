package com.joshuaogwang.nyigainsure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joshuaogwang.nyigainsure.entity.ClinicResult;
@Repository
public interface ClinicResultRepository extends JpaRepository<ClinicResult, Long>{

    ClinicResult findByUserAssessmentId(String userAssessmentId);

} 
