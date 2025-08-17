package com.jayon.flexmail.application.service;

import com.jayon.flexmail.domain.mail.TempMail;
import com.jayon.flexmail.domain.mail.TempMailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TempMailService {
    
    private final TempMailRepository tempMailRepository;
    
    public TempMail create() {
        return tempMailRepository.save(TempMail.create());
    }
    
    public Optional<TempMail> getCurrentTempMail(String tempMailId) {
        return tempMailRepository.findById(tempMailId);
    }
}
