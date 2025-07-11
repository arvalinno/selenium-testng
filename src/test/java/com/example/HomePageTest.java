package com.example;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.example.listener.TestLogListener;

@Listeners(TestLogListener.class)
public class HomePageTest extends BaseTest {
    
    @Test
    public void itemCardsSuccessShow() {  
        loginSuccess();
        
        String titleText = driver.findElement(By.cssSelector("[data-test='title']")).getText();
        Assert.assertEquals(titleText, "Products", "Title is not correct");
    }
}
