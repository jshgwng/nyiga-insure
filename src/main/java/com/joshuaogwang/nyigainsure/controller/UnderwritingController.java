package com.joshuaogwang.nyigainsure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joshuaogwang.nyigainsure.service.UnderwriterService;

@RestController
@RequestMapping("/api/v1/underwriter")
public class UnderwritingController {
    @Autowired
    private UnderwriterService underwriterService;

    public UnderwritingController(UnderwriterService underwriterService) {
        this.underwriterService = underwriterService;
    }

    @RequestMapping(value = "/onboarding", method = RequestMethod.POST)
    public void onboarding(@RequestParam String uniqueMemberIdentifier) {
        try {
            underwriterService.onboardingSMS(uniqueMemberIdentifier);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
