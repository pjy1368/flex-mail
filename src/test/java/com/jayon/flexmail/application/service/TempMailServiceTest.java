package com.jayon.flexmail.application.service;

import com.jayon.flexmail.AbstractIntegrationTest;
import com.jayon.flexmail.domain.exception.BusinessException;
import com.jayon.flexmail.domain.mail.TempMail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TempMailServiceTest extends AbstractIntegrationTest {

    @Autowired
    private TempMailService tempMailService;

    @Test
    void create_TempMail을_성공적으로_생성한다() {
        // when
        TempMail tempMail = tempMailService.create();

        // then
        assertThat(tempMail.getId()).isNotNull();
        assertThat(tempMail.getDisplayEmailAddress()).isNotNull();
        assertThat(tempMail.getExpiresAt()).isNotNull();
        assertThat(tempMail.getCreatedAt()).isNotNull();
        assertThat(tempMail.getRemainingSeconds()).isGreaterThan(0);
    }

    @Test
    void getCurrentTempMail_존재하는_ID로_조회_성공한다() {
        // given
        TempMail created = tempMailService.create();

        // when
        TempMail found = tempMailService.getCurrentTempMail(created.getId());

        // then
        assertThat(found.getId()).isEqualTo(created.getId());
        assertThat(found.getDisplayEmailAddress()).isEqualTo(created.getDisplayEmailAddress());
    }

    @Test
    void getCurrentTempMail_존재하지않는_ID로_조회시_예외발생() {
        // given
        String nonExistentId = "non-existent-id";

        // when & then
        assertThatThrownBy(() -> tempMailService.getCurrentTempMail(nonExistentId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("임시 메일을 찾을 수 없습니다");
    }
}
