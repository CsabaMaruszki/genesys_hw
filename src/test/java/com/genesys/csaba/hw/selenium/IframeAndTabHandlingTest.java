package com.genesys.csaba.hw.selenium;

import com.genesys.csaba.hw.selenium.pages.guru99.DemoPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class IframeAndTabHandlingTest {
    WebDriver driver;

    DemoPage demoPage;

    private static final Logger logger = LoggerFactory.getLogger(IframeAndTabHandlingTest.class);

    @BeforeTest
    @Parameters({"chromeDriverPath", "implicitWaitSeconds", "url"})
    public void setup(String chromeDriverPath, long implicitWaitSeconds, String url) {
        logger.info("Setting up test...");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        logger.debug("Chromedriver property set");

        ChromeOptions options = new ChromeOptions();
        //Headless mode is inconsistent because of cookie screen
        //options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        logger.debug("Chrome options set");

        driver = new ChromeDriver(options);
        logger.debug("Chrome driver created");
        driver.manage().timeouts().implicitlyWait(implicitWaitSeconds, TimeUnit.SECONDS);
        logger.debug("Implicit wait set");

        logger.debug("Creating page objects");
        demoPage = new DemoPage(driver);
        logger.debug("DemoPage object created");
        logger.debug("Page objects created");

        logger.info("Setup completed");
        logger.info("Opening url: {}", url);
        driver.navigate().to(url);
    }

    @Test(priority = 1)
    public void givenIframe_whenRedirect_thenCorrectTitle() {
        logger.info("Starting test");
        logger.info("Clicking Reject all cookie button");
        demoPage.clickRejectAllButton();
        logger.info("Clicking Reject confirm button");
        demoPage.clickRejectButton();
        logger.info("Clicking image in iframe");
        demoPage.clickJmeterImg();

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        logger.info("Switching tab");
        driver.switchTo().window(tabs.get(1));

        logger.info("Getting title");
        String title = driver.getTitle();
        Assert.assertEquals(title, "Selenium Live Project: FREE Real Time Project for Practice");

        logger.info("Closing tab");
        driver.close();
    }

    @Test(priority = 2)
    public void givenSubmitButton_whenOnNewPage_thenButtonIsPresent() {
        logger.info("Starting test");

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        logger.info("Switching tab");
        driver.switchTo().window(tabs.get(0));

        //This part is very inconsistent
        logger.info("Hovering Testing menu item");
        demoPage.hoverTestingMenuItem();
        logger.info("Clicking Selenium menu item");
        demoPage.clickSeleniumMenuItem();

        Assert.assertTrue(demoPage.isSubmitButtonDisplayed());
    }

    @AfterTest
    public void cleanup() {
        logger.info("Tests finished");
        logger.info("Quitting webdriver");
        driver.quit();
    }
}
