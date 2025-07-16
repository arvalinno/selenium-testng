package com.example.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        // driver = new ChromeDriver(options);
        try {
            options = new ChromeOptions();
            driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid Selenium Grid URL");
        }
        // driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    public void loginSuccess(){
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }
}
