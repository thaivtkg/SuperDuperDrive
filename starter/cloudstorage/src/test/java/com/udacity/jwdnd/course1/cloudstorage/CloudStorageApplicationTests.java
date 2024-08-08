package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.Pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.Pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.Pages.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {


	@Autowired
	EncryptionService encryptionService;


//	@LocalServerPort
	private int port=8080;


	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
//		WebDriverManager.chromedriver().setup();
		System.setProperty("webdriver.chrome.driver", "E:/chromedriver-win64/chromedriver.exe");
	}

	@BeforeEach
	public void beforeEach() {
		System.setProperty("webdriver.chrome.driver", "E:/chromedriver-win64/chromedriver.exe");
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password) {
		// Create a dummy account for logging in later.

		SignupPage signupPage= new SignupPage(driver);
		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.signUp(firstName,lastName,userName,password);

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
//		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up! Please continue to the login page."));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password) throws InterruptedException {
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage= new LoginPage(driver);
		loginPage.login(userName,password);

	}

	private void getHomePage(){
		driver.get("http://localhost:" + this.port + "/home");
	}
	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() throws InterruptedException {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertTrue(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() throws InterruptedException {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		Thread.sleep(1000);
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());


		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
	}

	@Test
	public void testCreateNote() throws InterruptedException {
		doMockSignUp("Create Note","Test","CRT","123");
		doLogIn("CRT", "123");
		HomePage homePage = new HomePage(driver);
		homePage.addNote("Test","123");
		Assertions.assertTrue(driver.findElement(By.id("success")).getText().contains("Success"));
	}

	@Test
	public void testEditNote() throws InterruptedException {
		doMockSignUp("Edit Note","Test","EDT","123");
		doLogIn("EDT", "123");
		HomePage homePage = new HomePage(driver);
		homePage.addNote("Test","123");
		getHomePage();
		homePage.openNoteTab();
		homePage.editNote();
		String newNoteTitle ="TestEdit";
		String newNoteDescription ="1234";
		homePage.setNoteTitleInput(newNoteTitle);
		homePage.setNoteDescriptionInput(newNoteDescription);
		homePage.noteSave();
		getHomePage();
		homePage.openNoteTab();
		Note note = homePage.getNote();
		Assertions.assertEquals(newNoteTitle, note.getNoteTitle());
		Assertions.assertEquals(newNoteDescription, note.getNoteDescription());
	}

	@Test
	public void TestCreateCredential() throws InterruptedException {
		doMockSignUp("Create Credential","Test","CRT","123");
		doLogIn("CRT", "123");
		HomePage homePage = new HomePage(driver);
		homePage.addCredential("http://localhost:8080/login","CRT","123");
		Assertions.assertTrue(driver.findElement(By.id("success")).getText().contains("Success"));
	}

	@Test
	public void TestEditCredential() throws InterruptedException {
		doMockSignUp("Create Credential", "Test", "CRT", "123");
		doLogIn("CRT", "123");
		HomePage homePage = new HomePage(driver);
		homePage.addCredential("http://localhost:8080/login", "CRT", "123");
		getHomePage();
		homePage.openCredentialTab();
		homePage.credentialEdit();
		String newURL = "http://localhost:8080/home";
		String newUsername = "test";
		String newPassword = "1234";
		homePage.setCredential(newURL, newUsername, newPassword);
		homePage.credentialSave();
		getHomePage();
		homePage.openCredentialTab();
		Credential credential = homePage.getCredential();
		Assertions.assertEquals(newURL, credential.getUrl());
		Assertions.assertEquals(newUsername, credential.getUsername());
	}

	@Test
	public void TestDeleteCredential() throws InterruptedException {
		doMockSignUp("Delete Credential","Test","CRT","123");
		doLogIn("CRT", "123");
		HomePage homePage = new HomePage(driver);
		homePage.addCredential("http://localhost:8080/login", "CRT", "123");
		getHomePage();
		homePage.openCredentialTab();
		homePage.deleteCredential();
		Assertions.assertTrue(homePage.noCredentialExist());
	}

	@Test
	public void TestSignUpAndLogin() throws InterruptedException {
		doMockSignUp("Test","Test","Thaivtkg","123");
		Assertions.assertEquals("Login",driver.getTitle());
		doLogIn("Thaivtkg", "123");
		Assertions.assertEquals("Home",driver.getTitle());
	}

	@Test
	public void testPageAccess() {
		getHomePage();
		Assertions.assertEquals(driver.getTitle(),"Login");
	}


	@Test
	public void testDeleteNote() throws InterruptedException {
		doMockSignUp("Delete Note","Test","CRT","123");
		doLogIn("CRT", "123");
		HomePage homePage = new HomePage(driver);
		homePage.addNote("Test","123");
		getHomePage();
		homePage.openNoteTab();
		homePage.deleteNote();
		Assertions.assertTrue(homePage.noNoteExist());
	}


}
