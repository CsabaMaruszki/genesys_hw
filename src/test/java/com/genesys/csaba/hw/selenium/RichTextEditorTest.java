package com.genesys.csaba.hw.selenium;

import com.genesys.csaba.hw.selenium.pages.onlinehtmleditor.MainPage;
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

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class RichTextEditorTest {
    WebDriver driver;
    MainPage mainPage;

    private static final Logger logger = LoggerFactory.getLogger(RichTextEditorTest.class);

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
        mainPage = new MainPage(driver);
        logger.debug("MainPage object created");
        logger.debug("Page objects created");

        logger.info("Setup completed");
        logger.info("Opening url: {}", url);
        driver.navigate().to(url);
    }

    @Test(priority = 1)
    @Parameters({"boldTextInput"})
    public void givenBold_whenEnterText_thenBoldTextAppears(String boldTextInput) {
        logger.info("Starting test");
        logger.info("Clicking bold button");
        mainPage.clickBoldButton();
        logger.info("Writing bold text");
        mainPage.writeIntoIframe(boldTextInput);
        logger.info("Clicking bold button");
        mainPage.clickBoldButton();
        logger.info("Writing space");
        mainPage.writeIntoIframe(" ");

        String boldText = mainPage.getBoldIframeText();
        Assert.assertEquals(boldText, "Automation");
    }

    @Test(priority = 2)
    @Parameters({"underlinedTextInput"})
    public void givenUnderline_whenEnterText_thenUnderlineTextAppears(String underlinedTextInput) {
        logger.info("Starting test");
        logger.info("Clicking underline button");
        mainPage.clickUnderlineButton();
        logger.info("Writing underline text");
        mainPage.writeIntoIframe(underlinedTextInput);
        logger.info("Clicking underline button");
        mainPage.clickUnderlineButton();
        logger.info("Writing space");
        mainPage.writeIntoIframe(" ");

        String underlineText = mainPage.getUnderlineIframeText();
        Assert.assertEquals(underlineText, "Test");
    }

    @Test(priority = 3)
    @Parameters({"undecoratedTextInput"})
    public void givenText_whenEnterText_thenWholeTextAppears(String undecoratedTextInput) {
        logger.info("Starting test");
        logger.info("Writing undecorated text");
        mainPage.writeIntoIframe(undecoratedTextInput);

        String wholeText = mainPage.getIframeText();
        Assert.assertEquals(wholeText, "Automation Test Example");
    }

    @AfterTest
    public void cleanup() {
        logger.info("Tests finished");
        logger.info("Quitting webdriver");
        driver.quit();
    }
}
