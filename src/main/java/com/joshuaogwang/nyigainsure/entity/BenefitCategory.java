package com.joshuaogwang.nyigainsure.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BenefitCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String premium;
    private String description;

    public BenefitCategory() {
    }

    public BenefitCategory(Long id, String name, String premium, String description) {
        this.id = id;
        this.name = name;
        this.premium = premium;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String catOptionList(List<BenefitCategory> list) {
        String options = "";
        for (BenefitCategory benefitCategory : list) {
            options += list.indexOf(benefitCategory) + 1 + ". " + benefitCategory.getName() + " - "
                    + benefitCategory.getPremium() + "\n";
        }
        return options;
    }

}
