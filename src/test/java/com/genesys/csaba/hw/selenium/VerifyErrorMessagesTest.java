package com.genesys.csaba.hw.selenium;

import com.genesys.csaba.hw.selenium.pages.saucedemo.InventoryPage;
import com.genesys.csaba.hw.selenium.pages.saucedemo.LoginPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class VerifyErrorMessagesTest {

    WebDriver driver;
    LoginPage loginPage;
    InventoryPage inventoryPage;

    private static final Logger logger = LoggerFactory.getLogger(VerifyErrorMessagesTest.class);

    @BeforeTest
    @Parameters({"chromeDriverPath", "implicitWaitSeconds", "url"})
    public void setup(String chromeDriverPath, long implicitWaitSeconds, String url) {
        logger.info("Setting up test...");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        logger.debug("Chromedriver property set");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        logger.debug("Chrome options set");

        driver = new ChromeDriver(options);
        logger.debug("Chrome driver created");
        driver.manage().timeouts().implicitlyWait(implicitWaitSeconds, TimeUnit.SECONDS);
        logger.debug("Implicit wait set");

        logger.debug("Creating page objects");
        loginPage = new LoginPage(driver);
        logger.debug("LoginPage object created");
        inventoryPage = new InventoryPage(driver);
        logger.debug("InventoryPage object created");
        logger.debug("Page objects created");

        logger.info("Setup completed");
        logger.info("Opening url: {}", url);
        driver.navigate().to(url);
    }

    @Test(priority = 1)
    public void givenNoCredential_whenLogIn_thenLoginError() {
        logger.info("Starting test");
        loginPage.clickLoginButton();
        logger.info("Clicking login button");
        String errorMessage = loginPage.getErrorMessage();

        Assert.assertEquals(errorMessage, "Epic sadface: Username is required");
    }

    @Test(priority = 2)
    @Parameters({"username", "password"})
    public void givenCredential_whenLogIn_thenSuccessfulLogin(String username, String password) {
        logger.info("Filling in username");
        loginPage.fillInUsername(username);
        logger.info("Filling in password");
        loginPage.fillInPassword(password);
        logger.info("Clicking login button");
        loginPage.clickLoginButton();

        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, "https://www.saucedemo.com/inventory.html");
    }

    @Test(priority = 3)
    public void givenFooter_whenScrollDown_thenContainTexts() {
        logger.info("Starting test");
        logger.info("Scrolling to the bottom of the page");
        inventoryPage.scrollToBottom();

        String footerText = inventoryPage.getFooterText();
        Assert.assertTrue(footerText.contains("2023") && footerText.contains("Terms of Service"));
    }


    @AfterTest
    public void cleanup() {
        logger.info("Tests finished");
        logger.info("Quitting webdriver");
        driver.quit();
    }
}