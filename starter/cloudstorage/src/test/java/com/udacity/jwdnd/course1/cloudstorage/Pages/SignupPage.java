package com.udacity.jwdnd.course1.cloudstorage.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstNameInput;

    @FindBy(id = "inputLastName")
    private WebElement lastNameInput;

    @FindBy(id = "inputUsername")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(id = "buttonSignUp")
    private WebElement signUpButton;

    private WebDriverWait webDriverWait;

    WebDriver driver;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        webDriverWait = new WebDriverWait(driver, 2);
        this.driver = driver;
    }

    public void signUp(String firstName, String lastName, String username, String password)  {
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
        webDriverWait.until(ExpectedConditions.visibilityOf(firstNameInput));
        firstNameInput.sendKeys(firstName);
        webDriverWait.until(ExpectedConditions.visibilityOf(lastNameInput));
        lastNameInput.sendKeys(lastName);
        webDriverWait.until(ExpectedConditions.visibilityOf(usernameInput));
        usernameInput.sendKeys(username);
        webDriverWait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.sendKeys(password);
        webDriverWait.until(ExpectedConditions.visibilityOf(signUpButton));
        signUpButton.click();
    }

}
