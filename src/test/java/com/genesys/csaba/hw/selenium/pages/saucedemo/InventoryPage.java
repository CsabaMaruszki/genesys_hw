package com.genesys.csaba.hw.selenium.pages.saucedemo;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InventoryPage {
    WebDriver driver;

    @FindBy(xpath = "//button[@id='add-to-cart-sauce-labs-backpack']")
    WebElement addBackPackButton;
    @FindBy(xpath = "//button[@id='add-to-cart-sauce-labs-fleece-jacket']")
    WebElement addJacketButton;
    @FindBy(xpath = "//span[@class='shopping_cart_badge']")
    WebElement cartBadge;
    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    WebElement cartButton;
@FindBy(xpath = "//div[contains(@class, 'footer')]")
WebElement footerText;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickAddBackPackButton() {
        addBackPackButton.click();
    }

    public void clickAddJacketButton() {
        addJacketButton.click();
    }

    public String getCartBadgeText() {
        return cartBadge.getText();
    }

    public void clickCartButton() {
        cartButton.click();
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public String getFooterText(){
        return footerText.getText();
    }
}
