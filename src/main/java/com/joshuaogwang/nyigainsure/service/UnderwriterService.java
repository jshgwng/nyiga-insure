package com.joshuaogwang.nyigainsure.service;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.africastalking.AfricasTalking;
import com.africastalking.SmsService;
import com.africastalking.sms.Recipient;
import com.joshuaogwang.nyigainsure.config.Config;
import com.joshuaogwang.nyigainsure.entity.Underwriter;
import com.joshuaogwang.nyigainsure.repository.UnderwriterRepository;

@Service
public class UnderwriterService {
    @Autowired
    private UnderwriterRepository underwriterRepository;

    private final Config config;

    @Autowired
    UnderwriterService(Config config) {
        this.config = config;
    }

    public UnderwriterService(UnderwriterRepository underwriterRepository) {
        this.config = new Config();
        this.underwriterRepository = underwriterRepository;
    }

    public UnderwriterRepository getUnderwriterRepository() {
        return underwriterRepository;
    }

    public Underwriter saveUnderwriter(Underwriter underwriter) {
        return underwriterRepository.save(underwriter);
    }

    public Underwriter getUnderwriterById(Long id) {
        return underwriterRepository.findById(id).orElse(null);
    }

    public Underwriter updateUnderwriter(Underwriter underwriter) {
        return underwriterRepository.save(underwriter);
    }

    public void deleteUnderwriter(Long id) {
        underwriterRepository.deleteById(id);
    }

    public Iterable<Underwriter> getAllUnderwriters() {
        return underwriterRepository.findAll();
    }

    public void onboardingSMS(String uniqueMemberIdentifier) throws IOException {
        Underwriter underwriter = getUnderwriterByUniqueIdentifier(uniqueMemberIdentifier);
        String msg = "Dear, " + underwriter.getName()
                + ", Your assessment has been completed. Your unique member identifier is "
                + underwriter.getUniqueIdentifier() + ". Please use to login and purchase a policy.\n"
                + "Dial *384*50382# to get started.";
        SmsService sms = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
        List<Recipient> response = sms.send(msg, new String[] {
                underwriter.getPhoneNumber()
        }, true);

        System.out.println(response);
    }

    public String generateUniqueMemberIdentifier() {
        return "NYIGA-" + "000" + LocalTime.now().getHour() + LocalTime.now().getMinute() + LocalTime.now().getSecond();
    }

    public Underwriter getUnderwriterByUniqueIdentifier(String uniqueIdentifier) {
        return underwriterRepository.findByUniqueIdentifier(uniqueIdentifier);
    }
}
