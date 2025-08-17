package com.jayon.flexmail.domain.mail;

import java.util.Optional;

public interface TempMailRepository {
    
    TempMail save(TempMail tempMail);
    Optional<TempMail> findById(String id);
}
