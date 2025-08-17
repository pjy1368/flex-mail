package com.jayon.flexmail.domain.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
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

    private LocalDateTime expiresAt;

    private LocalDateTime createdAt;

    public static TempMail create() {
        String id = generateId();
        String emailAddress = TempMailGenerator.generateDisplayEmailAddress();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = createdAt.plusMinutes(DEFAULT_EXPIRY_MINUTES);

        return TempMail.builder()
                .id(id)
                .displayEmailAddress(emailAddress)
                .expiresAt(expiresAt)
                .createdAt(createdAt)
                .build();
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }

    public int getRemainingSeconds() {
        long remainingSeconds = Duration.between(LocalDateTime.now(), this.expiresAt).getSeconds();
        return (int) Math.max(0, remainingSeconds);
    }
}
