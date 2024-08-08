package com.udacity.jwdnd.course1.cloudstorage.Pages;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private WebDriverWait webDriverWait;


    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id="nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "addNote")
    private WebElement addNoteButton;

    @FindBy(id = "addCredential")
    private WebElement addCredentialButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy(id = "noteSave")
    private WebElement noteSaveButton;

    @FindBy(id="noteTitle")
    private WebElement noteTitleText;

    @FindBy(id="noteDescription")
    private WebElement noteDescriptionText;

    @FindBy(id = "noteEditButton")
    private WebElement noteEditButton;

    @FindBy(id = "credential-url")
    private WebElement credentialURL;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credentialSave")
    private WebElement credentialSave;

    @FindBy(id="credentialEditBtn")
    private WebElement credentialEditButton;

    @FindBy(id = "noteDeleteButton")
    private WebElement noteDeleteButton;

    @FindBy(id="url")
    private WebElement credentialUrlText;

    @FindBy(id="username")
    private WebElement credentialUsernameText;

    @FindBy(id="password")
    private WebElement credentialPasswordText;

    @FindBy(id="credentialDeleteBtn")
    private WebElement credentialDeleteButton;

    private WebDriver driver;

    public HomePage(WebDriver driver ) {
        PageFactory.initElements(driver,this);
        webDriverWait = new WebDriverWait(driver, 2);
        this.driver = driver;
    }

    public void logout() throws InterruptedException {
        Thread.sleep(3000);
        logoutButton.click();
    }

    public void openNoteTab() throws InterruptedException {
        Thread.sleep(3000);
        noteTab.click();
    }

    public void addNote(String noteTitle,String noteDescription) throws InterruptedException {
        webDriverWait.until(ExpectedConditions.visibilityOf(noteTab));
        noteTab.click();
        webDriverWait.until(ExpectedConditions.visibilityOf(addNoteButton));
        addNoteButton.click();
        webDriverWait.until(ExpectedConditions.visibilityOf(noteTitleInput));
        noteTitleInput.sendKeys(noteTitle);
        webDriverWait.until(ExpectedConditions.visibilityOf(noteDescriptionInput));
        noteDescriptionInput.sendKeys(noteDescription);
        webDriverWait.until(ExpectedConditions.visibilityOf(noteSaveButton));
        noteSaveButton.click();
    }

    public void CredentialEdit(){
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialEditButton));
        credentialEditButton.click();
    }

    public void setCredential(String URL,String username,String password) throws InterruptedException {
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialURL));
        credentialURL.clear();
        credentialURL.sendKeys(URL);
        credentialUsername.clear();
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialUsername));
        credentialUsername.sendKeys(username);
        credentialPassword.clear();
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialPassword));
        credentialPassword.sendKeys(password);
    }

    public void noteSave(){
        webDriverWait.until(ExpectedConditions.visibilityOf(noteSaveButton));
        noteSaveButton.click();
    }

    public void credentialSave(){
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialSave));
        credentialSave.click();
    }

    public void credentialEdit(){
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialEditButton));
        credentialEditButton.click();
    }

    public void openCredentialTab() throws InterruptedException {
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialsTab));
        credentialsTab.click();
    }

    public void addCredential(String url, String username, String password) throws InterruptedException {
        openCredentialTab();
        webDriverWait.until(ExpectedConditions.visibilityOf(addCredentialButton));
        addCredentialButton.click();
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialURL));
        credentialURL.sendKeys(url);
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialUsername));
        credentialUsername.sendKeys(username);
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialPassword));
        credentialPassword.sendKeys(password);
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialSave));
        credentialSave.click();
    }


    public void setNoteTitleInput(String title){
        webDriverWait.until(ExpectedConditions.visibilityOf(noteTitleInput));
        noteTitleInput.clear();
        noteTitleInput.sendKeys(title);
    }

    public void setNoteDescriptionInput(String description){
        webDriverWait.until(ExpectedConditions.visibilityOf(noteTitleInput));
        noteDescriptionInput.clear();
        noteDescriptionInput.sendKeys(description);
    }

    public void editNote(){
        webDriverWait.until(ExpectedConditions.visibilityOf(noteEditButton));
        noteEditButton.click();
    }

    public Note getNote(){
        String noteTitle = webDriverWait.until(ExpectedConditions.visibilityOf(noteTitleText)).getText();
        String noteDescription= webDriverWait.until(ExpectedConditions.visibilityOf(noteDescriptionText)).getText();
        return new Note(noteTitle,noteDescription);
    }

    public boolean noNoteExist(){
        return !(noteTitleText.isDisplayed() && noteDescriptionText.isDisplayed());
    }

    public void deleteNote(){
        webDriverWait.until(ExpectedConditions.visibilityOf(noteDeleteButton));
        noteDeleteButton.click();
    }
    public void deleteCredential(){
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialDeleteButton));
        credentialDeleteButton.click();
    }

    public boolean noCredentialExist(){
        return !(credentialUrlText.isDisplayed() && credentialUsernameText.isDisplayed() && credentialPasswordText.isDisplayed());
    }

    public Credential getCredential(){
        String url= webDriverWait.until(ExpectedConditions.visibilityOf(credentialUrlText)).getText();
        String username= webDriverWait.until(ExpectedConditions.visibilityOf(credentialUsernameText)).getText();
        String password= webDriverWait.until(ExpectedConditions.visibilityOf(credentialPasswordText)).getText();
        return new Credential(url,username,password);
    }

}
