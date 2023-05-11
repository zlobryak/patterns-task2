package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
//import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.DataToConsole;
import ru.netology.testmode.data.Login;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

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
        Login.login(registeredUser, "fromUser", "fromUser");
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
        Login.login(notRegisteredUser, "fromUser", "fromUser");
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
        Login.login(blockedUser, "fromUser", "fromUser");
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
        Login.login(registeredUser, wrongLogin, "fromUser");
        $("[data-test-id='error-notification']")
                .shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        DataToConsole.printToConsole("Использованный в тесте логин: " + wrongLogin);
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
        Login.login(registeredUser, "fromUser", wrongPassword);
        $("[data-test-id='error-notification']")
                .shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        DataToConsole.printToConsole("Использованный в тесте пароль: " + wrongPassword);
    }
}