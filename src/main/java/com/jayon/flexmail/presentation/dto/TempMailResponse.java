package com.jayon.flexmail.presentation.dto;

import com.jayon.flexmail.domain.mail.TempMail;

import java.time.LocalDateTime;

public record TempMailResponse(
        String emailAddress,
        LocalDateTime expiresAt,
        int expiresInSeconds
) {
    public static TempMailResponse from(TempMail tempMail) {
        return new TempMailResponse(
                tempMail.getDisplayEmailAddress(),
                tempMail.getExpiresAt(),
                tempMail.getRemainingSeconds()
        );
    }
}
