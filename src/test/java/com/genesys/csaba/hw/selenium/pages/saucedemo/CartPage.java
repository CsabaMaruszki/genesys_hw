package com.genesys.csaba.hw.selenium.pages.saucedemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
    WebDriver driver;
    @FindBy(xpath = "//button[@id='checkout']")
    WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickCheckoutButton(){
        checkoutButton.click();
    }
}
