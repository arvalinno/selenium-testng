package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.example.listener.TestLogListener;

import org.testng.annotations.Listeners;

@Listeners(TestLogListener.class)
public class SauceDemoTest {

    @Test
    public void loginTest() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.saucedemo.com/");
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            boolean isLoggedIn = driver.getCurrentUrl().contains("inventory");
            Assert.assertTrue(isLoggedIn, "Login failed: inventory page not loaded.");
        } finally {
            driver.quit();
        }
    }
}
