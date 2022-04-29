package com.udacity.jwdnd.course1.cloudstorage;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    //Note implementation
    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy (id="btnAddNewNote")
    private WebElement addNewNote;

    @FindBy (id= "note-title")
    private WebElement inputNoteTitle;

    @FindBy(id = "tableNotetitle")
    private WebElement tableNoteTitle;

    @FindBy (id= "note-description")
    private WebElement inputNoteDescription;

    @FindBy(id = "tableNotedescription")
    private WebElement tableNoteDescription;

    @FindBy (id= "btnSaveChanges")
    private WebElement saveNote;

    @FindBy(id = "btnEditNote")
    private WebElement noteEditBtn;

    @FindBy(id = "ancDeleteNote")
    private WebElement ancDeleteNote;

    //Credential implementation
    @FindBy(id = "credential-url")
    private WebElement inputCredentialUrl;

    @FindBy(id = "credential-username")
    private WebElement inputCredentialUsername;

    @FindBy(id = "credential-password")
    private WebElement inputCredentialPassword;

    @FindBy (id= "tblCredentialUrl")
    private WebElement tableCredentialUrl;

    @FindBy (id= "tblCredentialUsername")
    private WebElement tableCredentialUsername;

    @FindBy (id= "tblCredentialPassword")
    private WebElement tableCredentialPassword;

    @FindBy (id= "nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy (id= "btnAddNewCredential")
    private WebElement addNewCredential;

    @FindBy (id= "credentialSubmit")
    private WebElement saveCredential;

    @FindBy (id= "btnEditCredential")
    private WebElement editCredentialButton;

    @FindBy (id= "aDeleteCredential")
    private WebElement deleteCredential ;

    @FindBy(id = "btnLogout")
    private WebElement logoutButton;

    private final WebDriverWait webDriverWait;

    private final JavascriptExecutor jse;
    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        jse = (JavascriptExecutor) webDriver;
        webDriverWait = new WebDriverWait(webDriver, 500);
    }

    public void logout() {
        this.logoutButton.click();
    }
    public void clickNotesTab() {
        this.navNotesTab.click();
    }

    public void clickAddNewNote(){
        jse.executeScript("arguments[0].click();", addNewNote);
    }

    public void clickSaveNote(){
        jse.executeScript("arguments[0].click();", saveNote);
    }

    public void setNoteTitle(String noteTitle) {
        jse.executeScript("arguments[0].value='" + noteTitle + "';", inputNoteTitle);
    }

    public void setNoteDescription(String noteDescription) {
        jse.executeScript("arguments[0].value='"+ noteDescription +"';", inputNoteDescription);
    }

    public Notes getFirstNote() {
        String title = webDriverWait.until(ExpectedConditions.elementToBeClickable(tableNoteTitle)).getText();
        String description = tableNoteDescription.getText();

        return new Notes(title, description);
    }
    public void editNote() {
        jse.executeScript("arguments[0].click();", noteEditBtn);
    }

    public void deleteNote() {
        jse.executeScript("arguments[0].click();", ancDeleteNote);
    }

    public boolean noNotes(WebDriver webDriver) {
        return !isElementPresent(By.id("tableNotetitle"), webDriver) && !isElementPresent(By.id("tableNotedescription"), webDriver);
    }

    public boolean isElementPresent(By locatorKey, WebDriver webDriver) {
        try {
            webDriver.findElement(locatorKey);

            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public void modifyNoteTitle(String newNoteTitle) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inputNoteTitle)).clear();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inputNoteTitle)).sendKeys(newNoteTitle);
    }

    public void modifyNoteDescription(String newNoteDescription) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inputNoteDescription)).clear();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inputNoteDescription)).sendKeys(newNoteDescription);
    }

    public void clickCredentialTab(){
        this.credentialTab.click();
    }

    public void clickAddNewCredential(){
        jse.executeScript("arguments[0].click();", addNewCredential);
    }

    public void clickSaveCredential(){
        jse.executeScript("arguments[0].click();", saveCredential);
    }

    public void editCredential(){
        jse.executeScript("arguments[0].click();", editCredentialButton);
    }

    public void deleteCredential(){

        jse.executeScript("arguments[0].click();", deleteCredential);
    }

    public void setCredentialUrl(String credentialUrl) {
        jse.executeScript("arguments[0].value='" + credentialUrl + "';", inputCredentialUrl);
    }

    public void setCredentialUsername(String credentialUsername) {
        jse.executeScript("arguments[0].value='"+ credentialUsername +"';", inputCredentialUsername);
    }

    public void setCredentialPassword(String credentialPassword) {
        jse.executeScript("arguments[0].value='" + credentialPassword+ "';", inputCredentialPassword);
    }

    public Credentials getFirstCredential() {
        String url = webDriverWait.until(ExpectedConditions.elementToBeClickable(tableCredentialUrl)).getText();
        String username = tableCredentialUsername.getText();
        String password = tableCredentialPassword.getText();

        return new Credentials(url, username, password);
    }

    public void modifyCredentialUrl(String newCredentialUrl) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inputCredentialUrl)).clear();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inputCredentialUrl)).sendKeys(newCredentialUrl);
    }

    public void modifyCredentialUsername(String newCredentialUsername) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inputCredentialUsername)).clear();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inputCredentialUsername)).sendKeys(newCredentialUsername);
    }

    public void modifyCredentialPassword(String newCredentialPassword) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inputCredentialPassword)).clear();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inputCredentialPassword)).sendKeys(newCredentialPassword);
    }


    public boolean noCredentials(WebDriver webDriver) {
        return !isElementPresent(By.id("tblCredentialUrl"), webDriver)
                && !isElementPresent(By.id("tblCredentialUsername"),webDriver)
                && !isElementPresent(By.id("tblCredentialPassword"),webDriver);
    }








}

