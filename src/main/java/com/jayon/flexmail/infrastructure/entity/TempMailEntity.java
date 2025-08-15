package com.jayon.flexmail.infrastructure.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.time.LocalDateTime;

@RedisHash(value = "temp_mail", timeToLive = 600)
public class TempMailEntity {
    @Id
    private String id;
    private String emailAddress;
    private String sessionId;
    private LocalDateTime expiresAt;
} 
