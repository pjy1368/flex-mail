package com.jayon.flexmail.infrastructure.entity;

import com.jayon.flexmail.domain.mail.TempMail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "temp_mail")
public class TempMailEntity {
    
    @Id
    private String id;
    
    private String displayEmailAddress;
    
    private LocalDateTime expiresAt;
    
    private LocalDateTime createdAt;
    
    public static TempMailEntity from(TempMail domain) {
        return TempMailEntity.builder()
                .id(domain.getId())
                .displayEmailAddress(domain.getDisplayEmailAddress())
                .expiresAt(domain.getExpiresAt())
                .createdAt(domain.getCreatedAt())
                .build();
    }
    
    public TempMail toDomain() {
        return TempMail.builder()
                .id(this.id)
                .displayEmailAddress(this.displayEmailAddress)
                .expiresAt(this.expiresAt)
                .createdAt(this.createdAt)
                .build();
    }

    @TimeToLive
    public long getTimeToLive() {
        long ttlSeconds = Duration.between(LocalDateTime.now(), this.expiresAt).getSeconds();
        return Math.max(1, ttlSeconds);
    }
}
