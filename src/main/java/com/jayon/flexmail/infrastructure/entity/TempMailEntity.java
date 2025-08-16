package com.jayon.flexmail.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "temp_mail", timeToLive = 600)
public class TempMailEntity {
    
    @Id
    private String id;
    
    private String emailAddress;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime expiresAt;
} 
