package org.diaspora;

import com.codeborne.selenide.Configuration;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.diaspora.pages.Diaspora.*;
import static org.diaspora.pages.Helpers.dropCreateSeedDiasporaDB;
import static com.codeborne.selenide.Condition.visible;

/**
 * Created by dmitriy on 06.06.15.
 */
public class DiasporaTest {

    @Before
    public void CleanDB(){
        dropCreateSeedDiasporaDB();
    }

    @Test
    public void SignUp() {
        open("http://localhost:3000/");
        Configuration.timeout = 12000;

        //Create new account
        $(byText("creating an account")).click();
        fillCell(user_email, "john@gmail.com");
        fillCell(user_username, "John");
        fillCell(user_password, "123456");
        fillCell(user_password_confirmation, "123456");
        $("[value = 'Sign up']").pressEnter();

        //Checking that new account has been created
        $(byText("Well, hello there!")).shouldBe(visible);
        fillCell($("#tags"), "#programming");
        $("#awesome_button").click();

        //Checking the first comment on stream
        $("#status_message_fake_text").shouldHave(
                exactText("Hey everyone, I’m #newhere. I’m interested in #programming. "));
        $(byText("Share")).click();

        logOut();

        //Sign up with existing email
        $(byText("Sign up")).click();
        fillCell(user_email, "john@gmail.com");
        fillCell(user_username, "David");
        fillCell(user_password, "123456");
        fillCell(user_password_confirmation, "123456");
        $("[value = 'Sign up']").pressEnter();
        $(".message").shouldHave(text("Email is already taken"));

        //Sign up with existing username
        $(byText("Sign up")).click();
        fillCell(user_email, "david@gmail.com");
        fillCell(user_username, "John");
        fillCell(user_password, "123456");
        fillCell(user_password_confirmation, "123456");
        $("[value = 'Sign up']").pressEnter();
        $(".message").shouldHave(text("Username is already taken. - That username has already been taken"));

        //Sign up with passwords mismatching
        $(byText("Sign up")).click();
        fillCell(user_email, "david@gmail.com");
        fillCell(user_username, "David");
        fillCell(user_password, "123456");
        fillCell(user_password_confirmation, "123457");
        $("[value = 'Sign up']").pressEnter();
        $(".message").shouldHave(text("Password confirmation doesn't match Password"));

        //Sign up with unavailable data
    }

    @Test
    public void UserProfile(){

        //Preconditions
        Configuration.timeout = 18000;
        open("http://localhost:3000/users/sign_in");
        logIn("bob", "evankorth");
        openProfile();

        //Edit profile
        $("#edit_profile").click();

        fillCell($("#profile_first_name"), "Michael");
        fillCell($("#profile_last_name"), "Jackson");
        fillCell($("#tags"), "music");
        fillCell($("#profile_bio"), "Michael Joseph Jackson (August 29, 1958 – June 25, 2009)" +
                " was an American singer, songwriter, record producer, dancer, and actor");
        fillCell($("#profile_location"), "Los Angeles, California, U.S.");
        fillCell($("#profile_gender"), "male");
        $("#profile_date_year>[value='1958']").setSelected(true);
        $("#profile_date_month>[value='8']").setSelected(true);
        $("#profile_date_day>[value='29']").setSelected(true);


        //Checking updated information
        $("#update_profile").click();
        $(".message").shouldHave(text("Profile updated"));
        openProfile();
        $("#name").shouldHave(exactText("Michael Jackson"));

        profile_information.get(0).$("h4").shouldHave(exactText("Bio"));
        profile_information.get(0).$(".ltr>p").shouldHave(
                exactText("Michael Joseph Jackson (August 29, 1958 – June 25, 2009)" +
                        " was an American singer, songwriter, record producer, dancer, and actor"));

        profile_information.get(1).$("h4").shouldHave(exactText("Location"));
        profile_information.get(1).$(".ltr>p").shouldHave(exactText("Los Angeles, California, U.S."));

        profile_information.get(2).shouldHave(text("Gender"));
        profile_information.get(2).shouldHave(text("male"));

        profile_information.get(3).shouldHave(text("Birthday"));
        profile_information.get(3).shouldHave(text("August 29 1958"));

        /*Delete(lock) profile
        $("#edit_profile").click();
        $$("#settings_nav>li>a").get(1).click();
        $("#close_account").click();
        $$("[method='post']>p").get(0).click();
        //fillCell($("#close_account_password"), "evankorth");
        $("#close_account_confirm").pressEnter().pressEnter();
        */
    }

}
