package com.jayon.flexmail.presentation.dto;

import com.jayon.flexmail.domain.mail.TempMail;

import java.time.Duration;
import java.time.LocalDateTime;

public record TempMailResponse(
        String emailAddress,
        LocalDateTime expiresAt,
        int expiresInSeconds
) {
    public static TempMailResponse from(TempMail tempMail) {
        long remainingSeconds = Duration.between(LocalDateTime.now(), tempMail.getExpiresAt()).getSeconds();
        return new TempMailResponse(
                tempMail.getDisplayEmailAddress(),
                tempMail.getExpiresAt(),
                (int) Math.max(0, remainingSeconds)
        );
    }
}
