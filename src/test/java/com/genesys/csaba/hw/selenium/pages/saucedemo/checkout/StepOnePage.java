package com.genesys.csaba.hw.selenium.pages.saucedemo.checkout;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class StepOnePage {
    WebDriver driver;

    @FindBy(xpath = "//input[@id='first-name']")
    WebElement firstNameInput;
    @FindBy(xpath = "//input[@id='last-name']")
    WebElement lastNameInput;
    @FindBy(xpath = "//input[@id='postal-code']")
    WebElement postalCodeInput;
    @FindBy(xpath = "//input[@id='continue']")
    WebElement continueButton;
    public StepOnePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillInFirstname(String firstName){
        firstNameInput.sendKeys(firstName);
    }

    public void fillInLastname(String lastName){
        lastNameInput.sendKeys(lastName);
    }

    public void fillInPostalCode(String postalCode){
        postalCodeInput.sendKeys(postalCode);
    }

    public  void clickContinueButton(){
        continueButton.click();
    }
}
