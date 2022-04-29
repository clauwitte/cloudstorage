package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id= "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id= "inputLastName")
    private WebElement inputLastName;

    @FindBy (id= "inputUsername")
    private WebElement inputUsername;

    @FindBy (id="inputPassword")
    private WebElement inputPassword;

    @FindBy (id="buttonSignUp")
    private WebElement buttonSignUp;

    @FindBy (id="loginbutton")
    private WebElement loginButton;

    public SignupPage (WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void signUp(String firstname, String lastname, String username, String password){
        this.inputFirstName.sendKeys(firstname);
        this.inputLastName.sendKeys(lastname);
        this.inputUsername.sendKeys(username);
        this.inputPassword.sendKeys(password);
        this.buttonSignUp.click();
    }

    public void redirectLogin(){
        this.loginButton.click();
    }
}
