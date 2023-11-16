package com.genesys.csaba.hw.selenium;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesys.csaba.hw.selenium.pages.saucedemo.CartPage;
import com.genesys.csaba.hw.selenium.pages.saucedemo.InventoryPage;
import com.genesys.csaba.hw.selenium.pages.saucedemo.LoginPage;
import com.genesys.csaba.hw.selenium.pages.saucedemo.checkout.CompletePage;
import com.genesys.csaba.hw.selenium.pages.saucedemo.checkout.StepOnePage;
import com.genesys.csaba.hw.selenium.pages.saucedemo.checkout.StepTwoPage;
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

import com.genesys.csaba.hw.selenium.pojo.Credential;

import javax.imageio.IIOException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class AutomatePurchaseProcessTest {
    Credential credential;
    WebDriver driver;
    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;
    StepOnePage stepOnePage;
    StepTwoPage stepTwoPage;
    CompletePage completePage;

    private static final Logger logger = LoggerFactory.getLogger(AutomatePurchaseProcessTest.class);

    @BeforeTest
    @Parameters({"chromeDriverPath", "credentialFileName", "implicitWaitSeconds", "url"})
    public void setup(String chromeDriverPath, String credentialFileName, long implicitWaitSeconds, String url) throws IllegalArgumentException, IOException {
        logger.info("Setting up test...");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        logger.debug("Chromedriver property set");

        logger.debug("Parsing {}", credentialFileName);
        ObjectMapper mapper = new ObjectMapper();
        credential = mapper.readValue(getFileAsIOStream(credentialFileName), Credential.class);

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
        cartPage = new CartPage(driver);
        logger.debug("CartPage object created");
        stepOnePage = new StepOnePage(driver);
        logger.debug("StepOnePage object created");
        stepTwoPage = new StepTwoPage(driver);
        logger.debug("StepTwoPage object created");
        completePage = new CompletePage(driver);
        logger.debug("CompletePage object created");
        logger.debug("Page objects created");

        logger.info("Setup completed");
        logger.info("Opening url: {}", url);
        driver.navigate().to(url);

    }

    private InputStream getFileAsIOStream(final String fileName) {
        InputStream ioStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }

    @Test(priority = 1)
    public void givenCredential_whenLogIn_thenSuccessfulLogin() {
        logger.info("Starting test");
        logger.info("Filling in username");
        loginPage.fillInUsername(credential.getUsername());
        logger.info("Filling in password");
        loginPage.fillInPassword(credential.getPassword());
        logger.info("Clicking login button");
        loginPage.clickLoginButton();

        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, "https://www.saucedemo.com/inventory.html");
    }

    @Test(priority = 2)
    public void givenItems_whenAddToCart_thenItemsAddedToCart() {
        logger.info("Starting test");
        logger.info("Adding backpack to cart");
        inventoryPage.clickAddBackPackButton();
        logger.info("Adding jacket to cart");
        inventoryPage.clickAddJacketButton();

        String cartBadgeText = inventoryPage.getCartBadgeText();
        Assert.assertEquals(cartBadgeText, "2");
    }

    @Test(priority = 3)
    @Parameters({"firstname", "lastname", "postalCode"})
    public void givenCart_whenCheckout_thenOrderPlaced(String firstname, String lastname, String postalCode) {
        logger.info("Starting test");
        logger.info("Clicking cart button");
        inventoryPage.clickCartButton();

        logger.info("Clicking checkout button");
        cartPage.clickCheckoutButton();

        logger.info("Filling in firstname");
        stepOnePage.fillInFirstname(firstname);
        logger.info("Filling in lastname");
        stepOnePage.fillInLastname(lastname);
        logger.info("Filling in postal code");
        stepOnePage.fillInPostalCode(postalCode);
        logger.info("Clicking continue button");
        stepOnePage.clickContinueButton();
        logger.info("Clicking finish button");
        stepTwoPage.clickFinishButton();

        String completeHeaderText = completePage.getCompleteHeaderText();
        Assert.assertEquals(completeHeaderText, "Thank you for your order!");
    }

    @AfterTest
    public void cleanup() {
        logger.info("Tests finished");
        logger.info("Quitting webdriver");
        driver.quit();
    }
}