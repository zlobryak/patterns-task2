package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {
    public void login(String login,String password){
            $("[data-test-id=login] input")
                    .sendKeys(login);
            $("[data-test-id=password] input")
                    .sendKeys(password);
            $("[data-test-id='action-login']")
                    .click();
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser
                (
                        "active",
                        6,
                        16,
                        true,
                        true,
                        true
                );
        login(registeredUser.getLogin(), registeredUser.getPassword());
        $("h2").shouldHave(Condition.text("Личный кабинет"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {

        var notRegisteredUser = getUser(
                "active",
                6,
                16,
                true,
                true,
                true);
        login(notRegisteredUser.getLogin(), notRegisteredUser.getPassword());
        $("[data-test-id='error-notification']")
                .shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser
                (
                        "blocked",
                        6,
                        16,
                        true,
                        true,
                        true
                );
        login(blockedUser.getLogin(), blockedUser.getPassword());
        $("[data-test-id='error-notification']")
                .shouldHave(Condition.text("Пользователь заблокирован"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }
    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
//        Configuration.holdBrowserOpen = true;
        var registeredUser = getRegisteredUser
                (
                        "active",
                        6,
                        16,
                        true,
                        true,
                        true
                );
        String wrongLogin = getRandomLogin();
        login(wrongLogin, registeredUser.getPassword());
        $("[data-test-id='error-notification']")
                .shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser
                (
                        "active",
                        6,
                        16,
                        true,
                        true,
                        true
                );
        var wrongPassword = getRandomPassword(
                6,
                16,
                true,
                true,
                true);
        login(registeredUser.getLogin(), wrongPassword);
        $("[data-test-id='error-notification']")
                .shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}