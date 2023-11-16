package com.genesys.csaba.hw.selenium.pages.onlinehtmleditor;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
    WebDriver driver;

    @FindBy(xpath = "//p")
    WebElement iframeText;
    @FindBy(xpath = "//a[contains(@title,'Ctrl+B')]")
    WebElement boldButton;
    @FindBy(xpath = "//a[contains(@title,'Ctrl+U')]")
    WebElement underlineButton;
    @FindBy(xpath = "//p//strong")
    WebElement iframeBoldText;
    @FindBy(xpath = "//p//u")
    WebElement iframeUnderlineText;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void switchToFrame(int id) {
        driver.switchTo().frame(id);
    }

    public void clickBoldButton() {
        boldButton.click();
    }

    public void clickUnderlineButton() {
        underlineButton.click();
    }

    public void writeIntoIframe(String text) {
        switchToFrame(0);
        iframeText.sendKeys(text);
        driver.switchTo().defaultContent();
    }

    public String getIframeText() {
        switchToFrame(0);
        String text = iframeText.getText();
        driver.switchTo().defaultContent();
        return text;
    }

    public String getBoldIframeText() {
        switchToFrame(0);
        String text = iframeBoldText.getText();
        driver.switchTo().defaultContent();
        return text;
    }

    public String getUnderlineIframeText() {
        switchToFrame(0);
        String text = iframeUnderlineText.getText();
        driver.switchTo().defaultContent();
        return text;
    }
}
