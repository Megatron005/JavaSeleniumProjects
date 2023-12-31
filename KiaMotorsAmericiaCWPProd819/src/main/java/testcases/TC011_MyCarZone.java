package testcases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.P2_Login;
import wrappers.KMABase;

public class TC011_MyCarZone extends KMABase {
	
	@Test(priority=1, dataProvider="fetchData")
	public void geoFenceAlert(String emailId, String pwdLogin, String vehicleModel, String alertName, String locationName) throws InterruptedException {
		
		new P2_Login(driver, test)
		
		.clickSignIn()
		.enterLoginEmailId(emailId)
		.enterLoginPassword(pwdLogin)
		.clkSignIn()
		.pickTheCar(vehicleModel);
		
	}
}
