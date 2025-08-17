package com.jayon.flexmail.presentation.controller;

import com.jayon.flexmail.application.service.TempMailService;
import com.jayon.flexmail.domain.mail.TempMail;
import com.jayon.flexmail.presentation.dto.TempMailResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/temp-emails")
@RequiredArgsConstructor
public class TempMailController {
    
    private static final String FLEX_MAIL_ID_COOKIE = "flex_mail_id";
    
    private final TempMailService tempMailService;
    
    @PostMapping
    public ResponseEntity<TempMailResponse> createTempMail(HttpServletResponse response) {
        TempMail tempMail = tempMailService.createTempMail();
        
        Cookie cookie = new Cookie(FLEX_MAIL_ID_COOKIE, tempMail.getId());
        cookie.setMaxAge(600);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TempMailResponse.from(tempMail));
    }
    
    @GetMapping("/current")
    public ResponseEntity<TempMailResponse> getCurrentTempMail(
            @CookieValue(value = FLEX_MAIL_ID_COOKIE, required = false) String flexMailId) {
        
        if (!StringUtils.hasText(flexMailId)) {
            return ResponseEntity.notFound().build();
        }
        
        Optional<TempMail> tempMail = tempMailService.getCurrentTempMail(flexMailId);
        
        return tempMail.map(mail -> ResponseEntity.ok(TempMailResponse.from(mail)))
                      .orElse(ResponseEntity.notFound().build());
    }
}
