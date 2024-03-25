package com.joshuaogwang.nyigainsure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joshuaogwang.nyigainsure.entity.Session;
import com.joshuaogwang.nyigainsure.repository.SessionRepository;
@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session saveSession(Session session) {
        return sessionRepository.save(session);
    }

    public Session getSession(Long id) {
        return sessionRepository.findById(id).orElse(null);
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    public String generatePrn() {
        return "PRN" + System.currentTimeMillis();
    }
}
