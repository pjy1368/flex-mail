package com.jayon.flexmail.domain.mail;

import com.google.common.hash.Hashing;
import net.datafaker.Faker;

import java.nio.charset.StandardCharsets;

public final class TempMailGenerator {
    
    private static final Faker faker = new Faker();
    
    private TempMailGenerator() {
        throw new AssertionError("Utility class");
    }
    
    public static String generateDisplayEmailAddress() {
        String username = faker.name().username();
        String uniqueHash = Hashing.murmur3_32_fixed()
                .hashString(System.nanoTime() + ":" + Thread.currentThread().threadId(), 
                            StandardCharsets.UTF_8)
                .toString().substring(0, 4);
        
        String localPart = username + uniqueHash;
        String domain = faker.internet().domainName();
        
        return localPart + "@" + domain;
    }
}
