package com.example.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.example.listener.TestLogListener;

import org.testng.annotations.Listeners;

@Listeners(TestLogListener.class)
public class LoginPage extends BaseTest {

    @Test
    public void loginSuccessTest() {       
        loginSuccess();

        boolean isLoggedIn = driver.getCurrentUrl().contains("inventory");
        Assert.assertTrue(isLoggedIn, "Login failed: inventory page not loaded.");
    }
}
