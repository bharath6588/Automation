package com.github;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class LoginTest {
	
	static WebDriver driver=null;
	static String url = "https://github.com";
	
	@BeforeTest
	public static void initialize(){
		
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.get(url);
		
	}
	
	//common method for both tests
	public static void login(String userName, String password){
		
		WebElement homePagesignInButton = driver.findElement(By.xpath("//a[text()='Sign in']"));
		homePagesignInButton.click();
		
		WebElement userNameField = driver.findElement(By.id("login_field"));
		userNameField.clear();
		userNameField.sendKeys(userName);
		
		WebElement passwordField = driver.findElement(By.id("password"));
		passwordField.clear();
		passwordField.sendKeys(password);
		
		WebElement signInButton = driver.findElement(By.name("commit"));
		signInButton.click();
		
	}
	
	//method for valid data
	@Test(priority=1,dataProvider="validData")
	public static void validLogin(String userName, String password ,String expected){		
		
		login(userName, password);
		
		String actual = driver.getTitle();
		
		Assert.assertEquals(actual, expected);
				
		WebElement viewProfile = driver.findElement(By.xpath("//a[@aria-label='View profile and more']"));
		viewProfile.click();
		
		WebElement signOutLink = driver.findElement(By.xpath("//button[@class='dropdown-item dropdown-signout']"));
		signOutLink.click();
					
	}
	
	//method for invalid data
	@Test(priority=2,dataProvider="invalidData")
	public static void invalidLogin(String userName, String password ,String expected){		
		
		login(userName, password);
		
		String actual = driver.findElement(By.xpath("//div[@class='container']")).getText();
		
		Assert.assertEquals(actual, expected);
					
	}
	
	
	@AfterTest
	public static void closeBrowser(){
		driver.quit();
	}
	
	
	@DataProvider
	public Object[][] validData(){
		 
		Object obj[][] = new Object[1][3];
		
		//Correct data
		obj[0][0] = "bharath6588";
		obj[0][1] = "123456ab";
		obj[0][2] = "GitHub";
		
		return obj;
		
	}
	
	@DataProvider
	public Object[][] invalidData(){
		 
		Object obj[][] = new Object[1][3];
		
		//Incorrect data
		obj[0][0] = "sam123am123";
		obj[0][1] = "147852bh";
		obj[0][2] = "Incorrect username or password.";
		
		return obj;
		
	}
	
	

}
