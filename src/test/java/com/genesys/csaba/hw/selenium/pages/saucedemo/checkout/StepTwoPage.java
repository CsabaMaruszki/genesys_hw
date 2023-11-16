package com.genesys.csaba.hw.selenium.pages.saucedemo.checkout;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class StepTwoPage {
    WebDriver driver;

    @FindBy(xpath = "//button[@id='finish']")
    WebElement finishButton;
    public StepTwoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickFinishButton(){
        finishButton.click();
    }
}
