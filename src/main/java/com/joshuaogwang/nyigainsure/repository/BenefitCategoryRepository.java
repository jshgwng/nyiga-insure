package com.joshuaogwang.nyigainsure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joshuaogwang.nyigainsure.entity.BenefitCategory;

public interface BenefitCategoryRepository extends JpaRepository<BenefitCategory, Long> {
    List<BenefitCategory> findByName(String name);
}
