package com.joshuaogwang.nyigainsure.controller;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.africastalking.AfricasTalking;
import com.africastalking.SmsService;
import com.africastalking.sms.Recipient;
import com.joshuaogwang.nyigainsure.config.Config;
import com.joshuaogwang.nyigainsure.entity.BenefitCategory;
import com.joshuaogwang.nyigainsure.entity.Menu;
import com.joshuaogwang.nyigainsure.entity.Underwriter;
import com.joshuaogwang.nyigainsure.repository.BenefitCategoryRepository;
import com.joshuaogwang.nyigainsure.service.ClinicResultService;
import com.joshuaogwang.nyigainsure.service.SessionService;
import com.joshuaogwang.nyigainsure.service.UnderwriterService;

@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UnderwriterService underwriterService;

    @Autowired
    private ClinicResultService clinicResultService;

    @Autowired
    private BenefitCategoryRepository benefitCategoryRepository;

    private final Config config;

    @Autowired
    SessionController(Config config) {
        this.config = config;
    }

    public SessionController(SessionService sessionService, UnderwriterService underwriterService,
            ClinicResultService clinicResultService, BenefitCategoryRepository benefitCategoryRepository) {
        this.config = new Config();
        this.sessionService = sessionService;
        this.underwriterService = underwriterService;
        this.clinicResultService = clinicResultService;
        this.benefitCategoryRepository = benefitCategoryRepository;
    }

    public SessionService getSessionService() {
        return sessionService;
    }

    Menu initialMenu = new Menu(
            "Welcome to Nyiga Insure.",
            "1. Register\n2. Login",
            Arrays.asList("Register", "Login"));

    Menu registerMenu = new Menu(
            "Enter your full name",
            "",
            Arrays.asList("Enter your full name"));

    Menu termsAndConditionsMenu = new Menu(
            "Terms and Conditions",
            "1. Accept\n2. Decline",
            Arrays.asList("Accept", "Decline"));

    Menu loginMenu = new Menu(
            "Enter your unique member identifier",
            "",
            Arrays.asList("Enter your unique member identifier"));

    Menu insurerMenu = new Menu(
            "Select your insurer",
            "1. ICEA\n2. UAP\n3. Prudential",
            Arrays.asList("ICEA", "UAP", "Prudential"));

    Menu currentResidence = new Menu(
            "Enter your current residence (District)",
            "",
            Arrays.asList("Enter your current residence"));

    Menu endSessMenu = new Menu(
            "Thank you for using Nyiga Insure.\n",
            "You'll receive a comfirmation code and a list of nearby accredited health facilities for medical assessment.",
            Arrays.asList(""));

    String nameRegex = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
    List<String> policyOptionIds = new ArrayList<>();
    List<BenefitCategory> benefitCategories;
    Underwriter currentUnderwriter;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String index(@RequestBody String body) throws IOException {
        Map<String, String> data = Arrays
                .asList(body.split("&"))
                .stream()
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry.length == 2 ? entry[1] : ""));

        String sessionId = data.get("sessionId");
        String serviceCode = data.get("serviceCode");
        String phoneNumber = data.get("phoneNumber");
        String text = data.get("text");
        String networkCode = data.get("networkCode");
        // 1*Okello+Opio
        String[] parts = text.split("\\*");
        System.out.println(parts[0]);

        System.out.println(text);

        if (text.equals("")) {
            return "CON " + initialMenu.getName() + "\n" + initialMenu.getDescription();
        } else if (text.contentEquals("1")) {
            return "CON " + registerMenu.getName() + "\n" + registerMenu.getDescription();
        } else if (parts[0].equals("1") && parts.length == 2) {
            return "CON " + termsAndConditionsMenu.getName() + "\n" + termsAndConditionsMenu.getDescription();
        } else if (parts[0].equals("1") && parts.length == 3) {
            return "CON " + insurerMenu.getName() + "\n" + insurerMenu.getDescription();
        } else if (parts[0].equals("1") && parts.length == 4) {
            return "CON " + currentResidence.getName() + "\n" + currentResidence.getDescription();
        } else if (parts[0].equals("1") && parts.length == 5) {
            System.out.println(parts[0]);
            System.out.println(parts[1]);
            System.out.println(parts[2]);
            System.out.println(parts[3]);
            System.out.println(parts[4]);
            // save user
            Underwriter underwriter = new Underwriter();
            underwriter.setName(parts[1].replace("+", " "));
            underwriter.setPhoneNumber(phoneNumber.replaceAll("%2B", "+"));
            underwriter.setLocation(parts[4]);
            underwriter.setUniqueIdentifier(underwriterService.generateUniqueMemberIdentifier());
            underwriterService.saveUnderwriter(underwriter);

            // send SMS
            SmsService sms = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
            String msg = "Thank you for registering with Nyiga Insure. Your unique member identifier is "
                    + underwriter.getUniqueIdentifier()
                    + ". You'll use this identifier for your medical assessment. Please visit any of the following accredited health facilities for your assessment: \n1. Mulago Hospital\n2. Mengo Hospital\n3. Rubaga Hospital";

            List<Recipient> response = sms.send(msg, new String[] { underwriter.getPhoneNumber() }, true);
            System.out.println(response);
            return "END " + endSessMenu.getName() + "\n" + endSessMenu.getDescription();
        } else if (text.contentEquals("2")) {
            return "CON " + loginMenu.getName() + "\n" + loginMenu.getDescription();
        } else if (parts[0].equals("2") && parts.length == 2) {
            Underwriter underwriter = underwriterService.getUnderwriterByUniqueIdentifier(parts[1]);
            if (underwriter != null) {
                currentUnderwriter = underwriter;
                String catCode = clinicResultService.patientPolicyOptions(underwriter.getUniqueIdentifier());

                benefitCategories = benefitCategoryRepository.findByName(catCode);
                String policyOptions = BenefitCategory.catOptionList(benefitCategories);
                policyOptionIds = benefitCategories.stream().map(BenefitCategory::getId).map(String::valueOf)
                        .collect(Collectors.toList());
                return "CON Your policy options are: \n" + policyOptions;
            } else {
                return "END Invalid unique member identifier";
            }
        } else if (parts[0].equals(parts[0]) && parts.length == 3) {
            String selectedOption = parts[2];
            String selectedPolicy = benefitCategories.get(Integer.parseInt(selectedOption) - 1).getName();
            return "CON You've selected " + selectedPolicy + ".\n1. Confirm\n2. Cancel";
            // return "CON " + insurerMenu.getName() + "\n" + insurerMenu.getDescription();
        } else if (parts[0].equals(parts[0]) && parts.length == 4) {
            String selectedOption = parts[2];
            String selectedPolicy = benefitCategories.get(Integer.parseInt(selectedOption) - 1).getName();
            if (parts[3].equals("1")) {
                SmsService sms = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
                String msg = "Thank you for your confirmation for " + selectedPolicy + ". Use PRN: "
                        + sessionService.generatePrn()
                        + " to pay for your policy.";
                List<Recipient> response = sms.send(msg, new String[] { currentUnderwriter.getPhoneNumber() }, true);
                return "END You've successfully selected " + selectedPolicy
                        + ". You'll receive a PRN shortly. Use any payment agent to pay for your policy.";
            } else {
                return "END You've cancelled the selection of " + selectedPolicy;
            }
        } else {
            return "END Please enter a valid option";
        }
    }
}
