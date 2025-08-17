package com.jayon.flexmail.presentation.controller;

import com.jayon.flexmail.AbstractIntegrationTest;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class TempMailControllerTest extends AbstractIntegrationTest {

    @Test
    void createTempMail_임시메일을_성공적으로_생성한다() {
        given()
            .contentType("application/json")
        .when()
            .post("/api/temp-emails")
        .then()
            .statusCode(201)
            .body("emailAddress", notNullValue())
            .body("expiresAt", notNullValue())
            .body("expiresInSeconds", greaterThan(0))
            .cookie("flex_mail_id", notNullValue());
    }

    @Test
    void getCurrentTempMail_쿠키가_있으면_현재메일을_조회한다() {
        // given - 임시메일 생성하여 쿠키 얻기
        Response createResponse = given()
            .contentType("application/json")
        .when()
            .post("/api/temp-emails");

        Cookie cookie = createResponse.getDetailedCookie("flex_mail_id");
        String expectedEmail = createResponse.jsonPath().getString("emailAddress");

        // when & then - 쿠키로 현재 메일 조회
        given()
            .cookie(cookie)
        .when()
            .get("/api/temp-emails/current")
        .then()
            .statusCode(200)
            .body("emailAddress", equalTo(expectedEmail))
            .body("expiresAt", notNullValue())
            .body("expiresInSeconds", greaterThan(0));
    }

    @Test
    void getCurrentTempMail_쿠키가_없으면_BadRequest를_반환한다() {
        given()
        .when()
            .get("/api/temp-emails/current")
        .then()
            .statusCode(400)
            .body("message", containsString("임시 메일 ID가 필요합니다"));
    }

    @Test
    void getCurrentTempMail_존재하지않는_ID로_조회시_NotFound를_반환한다() {
        given()
            .cookie("flex_mail_id", "non-existent-id")
        .when()
            .get("/api/temp-emails/current")
        .then()
            .statusCode(404)
            .body("message", containsString("임시 메일을 찾을 수 없습니다"));
    }
}
