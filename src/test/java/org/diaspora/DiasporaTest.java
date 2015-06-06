package org.diaspora;

import org.junit.Test;

import static com.codeborne.selenide.Selenide.open;

/**
 * Created by dmitriy on 06.06.15.
 */
public class DiasporaTest {

    @Test
    public void LogInTest() {
        open("http://localhost:3000/");

    }
}
