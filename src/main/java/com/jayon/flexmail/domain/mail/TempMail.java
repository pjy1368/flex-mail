package com.jayon.flexmail.domain.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TempMail {

    private static final int DEFAULT_EXPIRY_MINUTES = 10;

    private String id;

    private String displayEmailAddress;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    public static TempMail create() {
        String id = generateId();
        String emailAddress = TempMailGenerator.generateDisplayEmailAddress();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusMinutes(DEFAULT_EXPIRY_MINUTES);

        return TempMail.builder()
                .id(id)
                .displayEmailAddress(emailAddress)
                .createdAt(now)
                .expiresAt(expiresAt)
                .build();
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }
}
