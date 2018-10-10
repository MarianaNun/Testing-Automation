package ar.com.informatorio.calidad.test;

import static org.testng.Assert.assertEquals;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import ar.com.informatorio.calidad.pages.AuthenticationPage;
import ar.com.informatorio.calidad.pages.CreateAccountPage;
import ar.com.informatorio.calidad.pages.SuccessfulPage;
import ar.com.informatorio.calidad.pages.UserClass;;

public class LoginTest extends BaseTest {
	
	String count= "testing"+RandomStringUtils.randomAlphanumeric(4)+"@testing.com";
	UserClass usuario = new UserClass(1,"Mari", "Lopez",count, "testing",4 ,8 ,2004 ,false, false, "FirstName", "SecondName", "MyCompany", "España 1200", "Ciudad de Resistencia", "Cualquiera", 15, "00000", 21, "No hay informacion", "236458483", "192363544", "MyAlias");
		
	@Test
	public void AuthenticationUser(){
		driver.get("http://automationpractice.com/index.php");
		
		By buttonLoginLocator = By.className("login");
		WebElement loginButton = driver.findElement(buttonLoginLocator); 
		loginButton.click();
		
		AuthenticationPage authenticationPage = new AuthenticationPage(driver);
		SuccessfulPage successfulPage= authenticationPage.CreateAccount("jose@jose.com","josejose", driver);
		assertEquals(successfulPage.getFirstHeadingText(), "MY ACCOUNT");
	}
	
	@Test
	public void loginUsers(){
		driver.get("http://automationpractice.com/index.php");
		
		By buttonLoginLocator = By.className("login");
		WebElement loginButton = driver.findElement(buttonLoginLocator); 
		loginButton.click();
			
		AuthenticationPage authenticationPage = new AuthenticationPage(driver);
		CreateAccountPage accountPage= authenticationPage.CreateAccount(usuario.getEmail(), driver);
		SuccessfulPage successfulPage = accountPage.clickSubmitButton(usuario, driver);
		assertEquals(successfulPage.getFirstHeadingText(), "MY ACCOUNT");
	}
	
	@Test
	public void AuthenticationUsers(){
		driver.get("http://automationpractice.com/index.php");
		
		By buttonLoginLocator = By.className("login");
		WebElement loginButton = driver.findElement(buttonLoginLocator); 
		loginButton.click();
		
		AuthenticationPage authenticationPage = new AuthenticationPage(driver);
		SuccessfulPage successfulPage= authenticationPage.CreateAccount(usuario.getEmail(), usuario.getPassword(), driver);
		assertEquals(successfulPage.getFirstHeadingText(), "MY ACCOUNT");
	}
	

}

