package com.jayon.flexmail.application.service;

import com.jayon.flexmail.domain.mail.TempMail;
import com.jayon.flexmail.domain.mail.TempMailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TempMailService {
    
    private final TempMailRepository tempMailRepository;
    
    public TempMail create() {
        return tempMailRepository.save(TempMail.create());
    }
    
    public TempMail getCurrentTempMail(String tempMailId) {
        return tempMailRepository.fetchById(tempMailId);
    }
}
