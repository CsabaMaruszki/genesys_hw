package com.genesys.csaba.hw.selenium.pages.guru99;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DemoPage {
    WebDriver driver;

    @FindBy(xpath = "//img")
    WebElement jmeterImg;
    @FindBy(xpath = "//button[@id='denyAll']")
    WebElement rejectAllButton;
    @FindBy(xpath = "//div[@class='confirmation-dialog']//span[contains(text(),'Reject')]")
    WebElement rejectButton;
    @FindBy(xpath = "//li[contains(@class,'item118')]/a[contains(text(),'Testing')]")
    WebElement testingMenuItem;
    @FindBy(xpath = "//li[contains(@class,'item121')]/a[contains(text(),'Selenium')]")
    WebElement seleniumMenuItem;
    @FindBy(xpath = "//input[@class='submit']")
    WebElement submitButton;

    public DemoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private int findJmeterIframeId() {
        int size = driver.findElements(By.tagName("iframe")).size();
        for (int i = 0; i <= size; i++) {
            driver.switchTo().frame(i);
            if (!driver.findElements(By.xpath("html/body/a/img")).isEmpty()) {
                driver.switchTo().defaultContent();
                return i;
            }
            driver.switchTo().defaultContent();
        }
        return -1;
    }

    public void clickRejectAllButton() {
        driver.switchTo().frame("gdpr-consent-notice");
        rejectAllButton.click();
        driver.switchTo().defaultContent();
    }

    public void clickRejectButton() {
        driver.switchTo().frame("gdpr-consent-notice");
        rejectButton.click();
        driver.switchTo().defaultContent();
    }

    public void switchToFrame(int id) {
        driver.switchTo().frame(id);
    }

    public void clickJmeterImg() {
        switchToFrame(findJmeterIframeId());
        jmeterImg.click();
        driver.switchTo().defaultContent();
    }

    public void hoverTestingMenuItem() {
        Actions action = new Actions(driver);
        action.moveToElement(testingMenuItem).perform();
    }

    public void clickSeleniumMenuItem(){
        seleniumMenuItem.click();
    }

    public boolean isSubmitButtonDisplayed(){
        Actions action = new Actions(driver);
        action.moveToElement(submitButton).perform();
        return submitButton.isDisplayed();
    }
}