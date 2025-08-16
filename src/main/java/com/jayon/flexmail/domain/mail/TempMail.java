package com.jayon.flexmail.domain.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TempMail {
    
    private String id;
    
    private String emailAddress;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime expiresAt;
    
    public String getInternalEmailAddress() {
        return this.id + "@flex-mail.temp";
    }
    
    public String getDisplayEmailAddress() {
        return this.emailAddress;
    }
    
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }
    
    public String getEmailLocalPart() {
        return this.id;
    }
} 
