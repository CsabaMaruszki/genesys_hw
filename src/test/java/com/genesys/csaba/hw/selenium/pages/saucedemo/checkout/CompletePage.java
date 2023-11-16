package com.genesys.csaba.hw.selenium.pages.saucedemo.checkout;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CompletePage {
    WebDriver driver;

@FindBy(xpath = "//h2[@class='complete-header']")
    WebElement completeHeader;

    public CompletePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getCompleteHeaderText(){
        return completeHeader.getText();
    }
}
