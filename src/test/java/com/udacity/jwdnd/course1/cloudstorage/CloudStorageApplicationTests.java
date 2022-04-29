package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;

	public String baseURL;

	//Code wrote on Linux
	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-debugging-port=9222");
		driver = new ChromeDriver(chromeOptions);
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach(){
		baseURL = "http://localhost:" + port;
		}

	@Test
	public void doSignUpLogInLogOut(){

		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signUp("sander", "witte","san","123456");

		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));

		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("san", "123456");

		HomePage homePage = new HomePage(driver);
		homePage.logout();

		driver.get(baseURL + "/home");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void loginFailed(){
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signUp("sander", "witte","san","123456");

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("piet", "qwerty");
		Assertions.assertTrue(driver.findElement(By.id("errormessage")).getText().contains("Invalid username or password"));

		driver.get(baseURL + "/home");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testRedirection() {
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());


		SignupPage signupPage = new SignupPage(driver);
		signupPage.signUp("sander", "witte","SANDER","123456");

		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));

		signupPage.redirectLogin();
		// Check if we have been redirected to the log in page.
		assertEquals(baseURL + "/login", driver.getCurrentUrl());
	}


	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}


	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	private void mockSignUpAccountA(){
		doMockSignUp("URL","Test","UT","123");

	}
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("Large File","Test","PIP","123");
		doLogIn("PIP", "123");

		// Try to access a random made-up URL.
		driver.get(baseURL + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
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


	//Write a test that creates a note, and verifies it is displayed.
	@Test
	public void testNoteIsAdded(){

		doMockSignUp("Note is added","Test","NIA","123");
		doLogIn("NIA", "123");

		HomePage homePage = new HomePage(driver);
		homePage.clickNotesTab();
		homePage.clickAddNewNote();
		homePage.setNoteTitle("to Buy");
		homePage.setNoteDescription("nothing to buy");
		homePage.clickSaveNote();
		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickOk();
		Assertions.assertEquals("Home", driver.getTitle());
		homePage.clickNotesTab();
		homePage = new HomePage(driver);

		Notes note = homePage.getFirstNote();
		Assertions.assertTrue(note.getNotetitle().contains("to Buy"));
		Assertions.assertEquals("nothing to buy", note.getNotedescription());
	}

	//Write a test that edits an existing note and verifies that the changes are displayed.
	@Test
	public void testEditNote() {
		doMockSignUp("Large File","Test","DFD","123");
		doLogIn("DFD", "123");
		HomePage homePage = new HomePage(driver);
		homePage.clickNotesTab();
		homePage.clickAddNewNote();
		homePage.setNoteTitle("To buy");
		homePage.setNoteDescription("nothing to buy");
		homePage.clickSaveNote();
		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickOk();
		homePage.clickNotesTab();
		homePage = new HomePage(driver);
		homePage.editNote();
		String modifiedNoteTitle = "Modified";
		homePage.modifyNoteTitle(modifiedNoteTitle);
		String modifiedNoteDescription = "Modified description";
		homePage.modifyNoteDescription(modifiedNoteDescription);
		homePage.clickSaveNote();
		resultPage = new ResultPage(driver);
		resultPage.clickOk();
		homePage.clickNotesTab();
		Notes note = homePage.getFirstNote();
		Assertions.assertEquals(modifiedNoteTitle, note.getNotetitle());
		Assertions.assertEquals(modifiedNoteDescription, note.getNotedescription());
	}

	//Write a test that deletes a note and verifies that the note is no longer displayed.
	@Test
	public void testNoteDelete() {
		doMockSignUp("Large File","Test","LFL","123");
		doLogIn("LFL", "123");

		HomePage homePage = new HomePage(driver);
		homePage.clickNotesTab();
		homePage.clickAddNewNote();
		homePage.setNoteTitle("to Buy");
		homePage.setNoteDescription("nothing to buy");
		homePage.clickSaveNote();
		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickOk();
		Assertions.assertEquals("Home", driver.getTitle());
		homePage.clickNotesTab();
		homePage = new HomePage(driver);
		Assertions.assertFalse(homePage.noNotes(driver));
		homePage.deleteNote();
		resultPage = new ResultPage(driver);
		resultPage.clickOk();
		Assertions.assertTrue(homePage.noNotes(driver));
	}


	@Test
	public void addCredentials(){
		doLogIn("LFL", "123");

		HomePage homePage = new HomePage(driver);
		homePage.clickCredentialTab();
		homePage.clickAddNewCredential();
		homePage.setCredentialUrl("www.katjes.nl");
		homePage.setCredentialUsername("catlady");
		homePage.setCredentialPassword("123");
		homePage.clickSaveCredential();
		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickOk();
		homePage.clickCredentialTab();
		Assertions.assertTrue(driver.getPageSource().contains("Credentials"));
	}

	//Write a test that creates a set of credentials,
	//verifies that they are displayed,
	// and verifies that the displayed password is encrypted
	@Test
	public void verifyCredentialInput(){
		addCredentials();
		HomePage homePage = new HomePage(driver);
		Credentials credential = homePage.getFirstCredential();
		Assertions.assertEquals("www.katjes.nl", credential.getUrl());
		Assertions.assertEquals("catlady", credential.getUsername());
		Assertions.assertNotEquals("123", credential.getPassword());
	}

	//Write a test that views an existing set of credentials,
	// verifies that the viewable password is unencrypted,
	// edits the credentials,
	// and verifies that the changes are displayed.

	@Test
	public void editingCredentials(){
		addCredentials();
		HomePage homePage = new HomePage(driver);
		homePage.editCredential();

		//checking if password is unencrypted
		Assertions.assertTrue(driver.getPageSource().contains("123"));
		//checking if url and username are visible
		Assertions.assertTrue(driver.getPageSource().contains("www.katjes.nl"));
		Assertions.assertTrue(driver.getPageSource().contains("catlady"));

		String modifiedCredentialUrl= "www.hondjes.nl";
		homePage.modifyCredentialUrl(modifiedCredentialUrl);
		String modifiedCredentialUsername = "doglover";
		homePage.modifyCredentialUsername(modifiedCredentialUsername);
		String modifiedCredentialPassword = "456";
		homePage.modifyCredentialPassword(modifiedCredentialPassword);
		homePage.clickSaveCredential();
		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickOk();
		homePage.clickNotesTab();
	}

	//Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
	@Test
	public void testDeleteCredentials(){
		addCredentials();
		HomePage homePage = new HomePage(driver);
		homePage.deleteCredential();

		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickOk();
		Assertions.assertTrue(homePage.noCredentials(driver));

	}
}
