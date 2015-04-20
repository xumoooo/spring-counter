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
    public void testAdminOnce() {
        RestAssured.given()
                //.auth().digest("admin", "admin")
                .when()
                .get("/")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .content(equalCountInformation(UserInformation.ADMIN.getMessage(), 1));
    }

    //    @Test
    public void testUserOnce() {
        RestAssured.given()
                //.auth().digest("user", "user")
                .when()
                .get("/")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .content(equalCountInformation(UserInformation.USER.getMessage(), 1));
    }

    private Matcher<CountInformation> equalCountInformation(String message, long count) {
        return new CountInformationMatcher(objectMapper, message, count);
    }
}