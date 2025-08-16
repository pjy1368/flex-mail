package com.jayon.flexmail.presentation.dto;

import java.time.LocalDateTime;

public record TempMailCreateResponse(
        String emailAddress,
        LocalDateTime expiresAt,
        int expiresInSeconds
) {
} 
