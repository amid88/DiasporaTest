/**
 * Created by dmitriy on 06.06.15.
 */
package org.diaspora.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Diaspora {

    public static SelenideElement user_email = $("#user_email");
    public static SelenideElement user_username = $("#user_username");
    public static SelenideElement user_password = $("#user_password");
    public static SelenideElement user_password_confirmation = $("#user_password_confirmation");
    public static ElementsCollection profile_information = $$("#profile_information>li");


    @Step
    public static void fillCell(SelenideElement element, String text){
        element.click();
        element.setValue(text).pressTab();
    }

    @Step
    public static void logIn(String username, String password){
        fillCell(user_username, username);
        fillCell(user_password, password);
        $("[value = 'Sign in']").pressEnter();
    }

    @Step
    public static void openProfile(){
        $(".user-menu-trigger").click();
        $(byText("Profile")).click();
    }

    @Step
    public static void logOut(){
        $(".user-menu-trigger").click();
        $(byText("Log out")).click();
    }




}