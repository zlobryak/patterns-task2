package ru.netology.testmode.data;

import java.util.Objects;

import static com.codeborne.selenide.Selenide.$;

public class Login {
    public static void login
            (
                    DataGenerator.RegistrationDto user,
                    String login,
                    String password
            ) {
        if (Objects.equals(login, "fromUser")){
            $("[data-test-id=login] input")
                    .sendKeys(user.getLogin());
        } else {
            $("[data-test-id=login] input")
                    .sendKeys(login);
            DataToConsole.printToConsole("Использованный в тесте логин: " + login);
        }
        if (Objects.equals(password, "fromUser")) {
            $("[data-test-id=password] input")
                    .sendKeys(user.getPassword());
        } else {
            $("[data-test-id=password] input")
                    .sendKeys(password);
            DataToConsole.printToConsole("Использованный в тесте пароль: " + password);
        }
        $("[data-test-id='action-login']")
                .click();
    }

}
