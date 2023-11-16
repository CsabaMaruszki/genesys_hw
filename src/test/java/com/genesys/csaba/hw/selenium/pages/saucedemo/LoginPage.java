package com.genesys.csaba.hw.selenium.pages.saucedemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
  WebDriver driver;

  @FindBy(xpath = "//input[@id='user-name']")
  WebElement userNameInput;
  @FindBy(xpath = "//input[@id='password']")
  WebElement passwordInput;
  @FindBy(xpath = "//input[@id='login-button']")
  WebElement loginButton;
  @FindBy(xpath = "//div[contains(@class, 'error')]")
  WebElement errorContainer;

  public LoginPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public void fillInUsername(String username){
    userNameInput.sendKeys(username);
  }

  public void fillInPassword(String password){
    passwordInput.sendKeys(password);
  }

  public void clickLoginButton(){
    loginButton.click();
  }

  public String getErrorMessage(){
    return errorContainer.getText();
  }
}
