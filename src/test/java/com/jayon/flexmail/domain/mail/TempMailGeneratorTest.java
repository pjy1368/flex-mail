package com.jayon.flexmail.domain.mail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.DisplayName;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class TempMailGeneratorTest {

    @Test
    @DisplayName("연속으로 호출했을 때 서로 다른 이메일이 생성된다")
    void generateDisplayEmailAddress_연속호출시_서로다른값생성() {
        // given & when
        String email1 = TempMailGenerator.generateDisplayEmailAddress();
        String email2 = TempMailGenerator.generateDisplayEmailAddress();
        String email3 = TempMailGenerator.generateDisplayEmailAddress();

        // then
        assertThat(email1)
            .isNotEqualTo(email2)
            .isNotEqualTo(email3);
        assertThat(email2).isNotEqualTo(email3);
    }

    @Test
    @DisplayName("대량 생성 시 중복이 발생하지 않는다")
    void generateDisplayEmailAddress_대량생성시_중복없음() {
        // given
        int generateCount = 1000;
        Set<String> emailSet = new HashSet<>();

        // when
        for (int i = 0; i < generateCount; i++) {
            String email = TempMailGenerator.generateDisplayEmailAddress();
            emailSet.add(email);
        }

        // then
        assertThat(emailSet).hasSize(generateCount); // 중복이 없으면 Set 크기 = 생성 개수
    }

    @Test
    @DisplayName("멀티스레드 환경에서도 중복이 발생하지 않는다")
    void generateDisplayEmailAddress_멀티스레드환경_중복없음() throws Exception {
        // given
        int threadCount = 10;
        int perThreadGenerateCount = 100;
        Set<String> allEmails = ConcurrentHashMap.newKeySet();

        // when
        List<CompletableFuture<Void>> futures = IntStream.range(0, threadCount)
            .mapToObj(threadIndex -> 
                CompletableFuture.runAsync(() -> {
                    for (int i = 0; i < perThreadGenerateCount; i++) {
                        String email = TempMailGenerator.generateDisplayEmailAddress();
                        allEmails.add(email);
                    }
                })
            )
            .toList();

        // 모든 스레드 완료 대기
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();

        // then
        int expectedTotalCount = threadCount * perThreadGenerateCount;
        assertThat(allEmails).hasSize(expectedTotalCount);
    }

    @RepeatedTest(value = 50, name = "반복 테스트 {currentRepetition}/{totalRepetitions}")
    @DisplayName("반복 실행해도 항상 다른 값이 생성된다")
    void generateDisplayEmailAddress_반복실행시_항상다른값() {
        // given & when
        String email1 = TempMailGenerator.generateDisplayEmailAddress();
        String email2 = TempMailGenerator.generateDisplayEmailAddress();

        // then
        assertThat(email1).isNotEqualTo(email2);
    }

    @Test
    @DisplayName("짧은 시간 간격으로 생성해도 중복되지 않는다")
    void generateDisplayEmailAddress_짧은시간간격_중복없음() {
        // given
        List<String> emails = new ArrayList<>();
        
        // when - 매우 빠르게 연속 생성
        for (int i = 0; i < 100; i++) {
            emails.add(TempMailGenerator.generateDisplayEmailAddress());
        }

        // then
        Set<String> uniqueEmails = new HashSet<>(emails);
        assertThat(uniqueEmails).hasSize(emails.size());
    }
}
