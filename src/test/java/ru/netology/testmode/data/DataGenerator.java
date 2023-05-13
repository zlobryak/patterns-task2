package ru.netology.testmode.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    private static void sendRequest(RegistrationDto user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getRandomLogin() {
        return faker.name().username();
    }

    public static String getRandomPassword
            (
                    int minLength,
                    int maxLength,
                    boolean includeUppercase,
                    boolean includeSpecial,
                    boolean includeDigit
            ) {
        return faker.internet().password(
                minLength,
                maxLength,
                includeUppercase,
                includeSpecial,
                includeDigit
        );
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto getUser
                (
                        String status,
                        int minLength,
                        int maxLength,
                        boolean includeUppercase,
                        boolean includeSpecial,
                        boolean includeDigit
                ) {
            return new RegistrationDto
                    (
                            getRandomLogin(),
                            getRandomPassword(
                                    minLength,
                                    maxLength,
                                    includeUppercase,
                                    includeSpecial,
                                    includeDigit
                            ),
                            status
                    );
        }

        public static RegistrationDto getRegisteredUser
                (
                        String status,
                        int minLength,
                        int maxLength,
                        boolean includeUppercase,
                        boolean includeSpecial,
                        boolean includeDigit
                ) {

            RegistrationDto registeredUser = getUser
                    (
                            status,
                            minLength,
                            maxLength,
                            includeUppercase,
                            includeSpecial,
                            includeDigit
                    );
            sendRequest(registeredUser);
            return registeredUser;

        }
    }

    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }
}