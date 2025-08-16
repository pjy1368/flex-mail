package com.jayon.flexmail.presentation.controller;

import com.jayon.flexmail.application.service.TempMailService;
import com.jayon.flexmail.presentation.dto.TempMailCreateResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/temp-emails")
@RequiredArgsConstructor
public class TempMailController {
    
    private static final String FLEX_MAIL_ID_COOKIE = "flex_mail_id";
    
    private final TempMailService tempMailService;
    
    @PutMapping
    public ResponseEntity<TempMailCreateResponse> upsertTempMail(
            @CookieValue(value = FLEX_MAIL_ID_COOKIE, required = false) String flexMailId,
            HttpServletResponse response
    ) {
        
        if (StringUtils.hasText(flexMailId)) {
            TempMailCreateResponse mailResponse = tempMailService.getExistingTempMail(flexMailId);
            return ResponseEntity.ok(mailResponse);
        }
        
        String tempMailId = tempMailService.generateTempMailId();
        TempMailCreateResponse mailResponse = tempMailService.createNewTempMail();
        
        Cookie cookie = new Cookie(FLEX_MAIL_ID_COOKIE, tempMailId);
        cookie.setMaxAge(600);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(mailResponse);
    }
} 
