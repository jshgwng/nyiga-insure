package com.joshuaogwang.nyigainsure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joshuaogwang.nyigainsure.entity.BenefitCategory;
import com.joshuaogwang.nyigainsure.repository.BenefitCategoryRepository;
@RestController
@RequestMapping("/api/v1/benefitcategory")
public class BenefitCategoryService {
 @Autowired
    private BenefitCategoryRepository benefitCategoryRepository;       

    public BenefitCategoryService(BenefitCategoryRepository benefitCategoryRepository) {
        this.benefitCategoryRepository = benefitCategoryRepository;
    }

    @RequestMapping(value = "/all")
    public Iterable<BenefitCategory> getAllBenefitCategories() {
        return benefitCategoryRepository.findAll();
    }

    @RequestMapping(value = "/save")
    public BenefitCategory saveBenefitCategory(@RequestBody BenefitCategory benefitCategory) {
        return benefitCategoryRepository.save(benefitCategory);
    }

    @RequestMapping(value = "/get")
    public BenefitCategory getBenefitCategoryById(@RequestBody Long id) {
        return benefitCategoryRepository.findById(id).orElse(null);
    }   

    @RequestMapping(value = "/update")
    public BenefitCategory updateBenefitCategory(@RequestBody BenefitCategory benefitCategory) {
        return benefitCategoryRepository.save(benefitCategory);
    }

    @RequestMapping(value = "/delete")
    public void deleteBenefitCategory(@RequestBody Long id) {
        benefitCategoryRepository.deleteById(id);
    }
    
}
