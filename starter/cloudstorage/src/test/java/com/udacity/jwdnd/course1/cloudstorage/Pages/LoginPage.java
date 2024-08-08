package com.udacity.jwdnd.course1.cloudstorage.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriverWait webDriverWait;

    @FindBy(id = "inputUsername")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    private WebDriver driver;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        webDriverWait  =new WebDriverWait(driver,2);
        this.driver=driver;
    }

    public void login(String username,String password) throws InterruptedException {
        webDriverWait.until(ExpectedConditions.visibilityOf(usernameInput));
        usernameInput.sendKeys(username);
        webDriverWait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.sendKeys(password);
        webDriverWait.until(ExpectedConditions.visibilityOf(loginButton));
        loginButton.click();
    }


}
