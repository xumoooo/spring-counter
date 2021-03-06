package com.github.xumoooo.springcounter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringCounterApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class SpringCounterApplicationIT {

    @Value("${local.server.port}")
    private int serverPort;

    @Autowired
    private ObjectMapper objectMapper;


    @Before
    public void setUp() {
        RestAssured.port = serverPort;
    }

    @Test
    public void methodNotAllowedTest() {
        RestAssured.when()
                .post("/")
                .then()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void accessDeniedTest() {
        RestAssured
                .when()
                .get("/")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .content(equalMessage(CommonRestService.ACCESS_DENIED_MESSAGE));
    }

    @Test
    public void adminTest() {
        int restRunCount = 10;

        for (int i = 1; i <= restRunCount; ++i) {
            RestAssured.given()
                    .auth().digest("admin", "admin")
                    .when()
                    .get("/")
                    .then()
                    .statusCode(HttpStatus.SC_OK)
                    .content(equalCountInformation(UserInformation.ADMIN.getMessage(), i));
        }
    }

    @Test
    public void userTest() {
        int restRunCount = 10;

        for (int i = 1; i <= restRunCount; ++i) {
            RestAssured.given()
                    .auth().digest("user", "user")
                    .when()
                    .get("/")
                    .then()
                    .statusCode(HttpStatus.SC_OK)
                    .content(equalCountInformation(UserInformation.USER.getMessage(), i));
        }
    }

    @Test
    public void otherTest() {
        RestAssured.given()
                .auth().digest("other", "other")
                .when()
                .get("/")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .content(equalMessage(CommonRestService.getForbiddenMessage("other")));
    }

    private Matcher<CountInformation> equalCountInformation(String message, long count) {
        return new CountInformationMatcher(objectMapper, message, count);
    }

    private Matcher<Message> equalMessage(String message) {
        return new MessageMatcher(objectMapper, message);
    }
}
