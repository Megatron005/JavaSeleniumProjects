package testcases;

import org.testng.annotations.Test;

import pages.P2_Login;
import wrappers.KMABase;

public class TC002_Login extends KMABase {

		
	@Test(priority=1)
	public void verifySignInIsDisplayed() {
		new P2_Login(driver, test)
		.clickSignIn();
	}
	
	
	
	@Test(priority=2, dataProvider="fetchData")
	public void verifyLoginIn(String emailId, String pwdLogin) throws InterruptedException{
	System.out.println(emailId);
	new P2_Login(driver, test)
	.clickSignIn()
	.enterLoginEmailId(emailId)
	.enterLoginPassword(pwdLogin)
	.clkSignIn();	
	}	
}
