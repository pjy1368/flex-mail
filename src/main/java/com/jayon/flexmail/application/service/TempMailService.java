package com.jayon.flexmail.application.service;

import com.jayon.flexmail.domain.mail.TempMail;
import com.jayon.flexmail.presentation.dto.TempMailCreateResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TempMailService {
    
    public TempMail createTempMail() {
        return null;
    }
    
    public TempMailCreateResponse createNewTempMail() {
        return new TempMailCreateResponse(
                "john.smith2a8c@quickmail.org",
                LocalDateTime.now().plusMinutes(10),
                600
        );
    }
    
    public TempMailCreateResponse getExistingTempMail(String tempMailId) {
        return new TempMailCreateResponse(
                "john.smith2a8c@quickmail.org",
                LocalDateTime.now().plusMinutes(10),
                600
        );
    }
    
    public String generateTempMailId() {
        return "dummy-uuid-12345";
    }
} 
