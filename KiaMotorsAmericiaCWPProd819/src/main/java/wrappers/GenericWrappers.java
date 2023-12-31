package wrappers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;

import org.openqa.selenium.OutputType;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.relevantcodes.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.Reporter;

public class GenericWrappers extends Reporter implements Wrappers {

	
	public RemoteWebDriver driver;
	
	protected static Properties prop;
	public String sUrl,primaryWindowHandle, sHubUrl, sHubPort;
	
	//-------------------------------------------------Selenium Grid---------------------------------------------------------------	

	public GenericWrappers() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));
			sHubUrl = prop.getProperty("HUB");
			sHubPort = prop.getProperty("PORT");
			sUrl = prop.getProperty("URL");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public GenericWrappers(RemoteWebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test=test;
	}

	

//----------------------------------------------------Load Objects---------------------------------------------------------------	

	public void loadObjects() {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/object.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//----------------------------------------------------UnLoad Objects---------------------------------------------------------------	
	
	public void unloadObjects() {
		prop = null;
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------	
	
	
	
	public void invokeApp(String browser) {
		invokeApp(browser,false);
		
	}
	
	
	
	

	
	public void invokeApp(String browser, boolean bRemote) {
		
		try {
				if(browser.equalsIgnoreCase("chrome")){					
					 System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
//					 WebDriverManager.chromedriver().setup();
					 driver = new ChromeDriver();
				}

				else if(browser.equalsIgnoreCase("edge")){					
//					 System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
					 WebDriverManager.edgedriver().setup();
					 driver = new EdgeDriver();
				}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(sUrl);
			primaryWindowHandle = driver.getWindowHandle();		

			reportStep("The browser:" + browser + " launched successfully", "PASS");

		} catch (Exception e) {
			e.printStackTrace();
			reportStep("The browser:" + browser + " could not be launched", "FAIL");
		}
	}


	// This method will wait until element is visible
	
	
	public void waitUntilInvisibilityOfElementLocated(String xpath) {
		
		try {
		new WebDriverWait(driver, 25).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
		reportStep("The element has been found.", "PASS");
	} catch (Exception e) {
		reportStep("The element was not found.", "FAIL");
	}
	}
	
	

	
	public void waitUntilElementVisisble(String xpath) {
		
		try {
		new WebDriverWait(driver, 120).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		reportStep("The element has been found.", "PASS");
		} catch (Exception e) {
			reportStep("The element was not found.", "FAIL");
		}
	}
	
	public void clickOncloseButtonxPopup() {

		try {
			waitForPageLoaded();
		boolean status = false;

		List<WebElement> listOfButton = driver.findElementsByXPath("//button[@class='close']");
		if (!(listOfButton.size() == 0)) {
		for (int i = 1; i <= listOfButton.size(); i++) {
		String buttonElement ="(//button[@class='close'])["
		+ i + "]";		
		status= driver.findElement(By.xpath(buttonElement)).isDisplayed();
		if (status=true) {
			clickByXpath(buttonElement);	
			break;
		}
		
		}

		}
		if (!status) {
		}
		} catch (Exception e) {
		
		}

		}
	public void waitForPageLoaded() {


        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            for (int i = 0; i < 20000; i++) {
                String status = js.executeScript("return document.readyState").toString();
                if (status.equals("complete")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	
	private boolean isElementDisplayed(String buttonElement, int i) {
		// TODO Auto-generated method stub
		return false;
	}

	public void waitVisibilityOfElementLocatedByXpath(String xpath) {
		try {
			
			new WebDriverWait(driver, 120).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			reportStep("The element has been found.", "PASS");
		} catch (Exception e) {
			reportStep("The element was not found.", "FAIL");
		}
	}
	
	
	
		public void waitVisibilityOfElementLocatedByName(String name) {
			try {
				new WebDriverWait(driver, 120).until(ExpectedConditions.visibilityOfElementLocated(By.name(name)));
				reportStep("The element has been found.", "PASS");
			} catch (Exception e) {
				reportStep("The element was not found.", "FAIL");
			}
		}
		
		

		
			public void waitVisibilityOfElementLocatedByID(String id) {
				try {
					new WebDriverWait(driver, 120).until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
					reportStep("The element has been found.", "PASS");
				} catch (Exception e) {
					reportStep("The element was not found.", "FAIL");
				}
			}
		

	
		public void waitelementToBeClickableByXpath(String xpath) {
			try {
				new WebDriverWait(driver, 120).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
				reportStep("The element has been found.", "PASS");
			} catch (Exception e) {
				reportStep("The element was not found.", "FAIL");
			}
		}
	

		//Fluent Wait
	public void waitElementToBeClickableXpathFW() {
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	       .withTimeout(120, TimeUnit.SECONDS)
	       .pollingEvery(2, TimeUnit.SECONDS)
	       .ignoring(NoSuchElementException.class);

		
	reportStep("The element has been found.", "PASS");
		} catch (Exception e) {
			reportStep("The element was not found.", "FAIL");
		}
	}
	
	
	
		
//------------------------------------------------------Enter-------------------------------------	
		
	public void enterById(String idValue, String data) {
		try {
			driver.findElement(By.id(idValue)).clear();
			driver.findElement(By.id(idValue)).sendKeys(data);
			
			reportStep("The data were entered successfully into the field :", "PASS");
		} catch (NoSuchElementException e) {
			reportStep("The data could not be entered into the field :", "FAIL");
		} catch (Exception e) {
			reportStep("Unknown exception occured while entering the data into the field :", "FAIL");
		}
	}

	
	// This method will enter the value to the text field using name attribute to locate
	
	public void enterByName(String nameValue, String data) {
		try {
			driver.findElement(By.name(nameValue)).clear();
			driver.findElement(By.name(nameValue)).sendKeys(data);	
			reportStep("The data were entered successfully into the field :", "PASS");
		} catch (NoSuchElementException e) {
			reportStep("The data could not be entered into the field :", "FAIL");
		} catch (Exception e) {
			reportStep("Unknown exception occured while entering the data into the field :", "FAIL");
		}

	}

	
	 //  This method will enter the value to the text field using name attribute to locate
	 
	public void enterByXpath(String xpathValue, String data) {
		try {
			driver.findElement(By.xpath(xpathValue)).clear();
			driver.findElement(By.xpath(xpathValue)).sendKeys(data);	
			reportStep("The data were entered successfully into the field :", "PASS");

		} catch (NoSuchElementException e) {
			reportStep("The data could not be entered into the field :", "FAIL");
		} catch (Exception e) {
			reportStep("Unknown exception occured while entering the data into the field :", "FAIL");
		}

	}
	
	
//--------------------------------------------This method will verify the page title of the browser--------------------------------------------- 
	
	public boolean verifyTitle(String title){
		boolean bReturn = false;
		try{
			if (driver.getTitle().equalsIgnoreCase(title)){
				reportStep("The title of the page matches with the expected value :", "PASS");
				bReturn = true;
			}else
				System.out.println();
				reportStep("The title of the page did not match the expected value :", "SUCCESS");

		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
		return bReturn;
	}

	
	 // This method will verify the given text matches in the element text
	
	public void verifyTextByXpath(String xpath, String text){
		try {
			String sText = driver.findElementByXPath(xpath).getText();
			if (sText.equalsIgnoreCase(text)){
				reportStep("The text matched with the expected value :", "PASS");
			}else{
				reportStep("The text did not matched with the expected value :", "FAIL");
			}
		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
	}

	
	// This method will verify the given text is available in the element text
	
	public void verifyTextContainsByXpath(String xpath, String text){
		try{
			String sText = driver.findElementByXPath(xpath).getText();
			if (sText.contains(text)){
				reportStep("The text matched with the expected value :", "PASS");
			}else{
				reportStep("The text did not matched with the expected value :", "FAIL");
			}
		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
	}

	
	 // This method will verify the given text is available in the element text
	 
	public void verifyTextById(String id, String text) {
		try{
			String sText = driver.findElementById(id).getText();
			if (sText.equalsIgnoreCase(text)){
				reportStep("The text matched with the expected value :", "PASS");
			}else{
				reportStep("The text did not matched with the expected value :", "FAIL");
			}
		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
	}

	
	 // This method will verify the given text is available in the element text
	 
	public void verifyTextContainsById(String id, String text) {
		try{
			String sText = driver.findElementById(id).getText();
			if (sText.contains(text)){
				reportStep("The text matched with the expected value :", "PASS");
			}else{
				reportStep("The text did not matched with the expected value :", "FAIL");
			}
		}catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
	}

	
	 // This method will close all the browsers
	 
	public void closeAllBrowsers() {
		try {
			//closeBrowser();
			driver.quit();
		} catch (Exception e) {
			reportStep("The browser could not be closed.", "WARN");
		}

	}
	
	public void closeBrowser() {
		try {
			driver.close();
		} catch (Exception e) {
			reportStep("The browser could not be closed.", "WARN");
		}
	}

	
	 // This method will click the element using id as locator
	 
	public void clickById(String id) {
		try{
			driver.findElement(By.id(id)).click();
			reportStep("The element with the id attribute has been clicked.", "PASS");

		} catch (Exception e) {
			reportStep("The element identified by the ID attribute could not be clicked.", "FAIL");
		}
	}

	
	 // This method will click the element using id as locator
	
	public void clickByClassName(String classVal) {
		try{
			driver.findElement(By.className(classVal)).click();
			reportStep("The element with the class Name attribute has been clicked.", "PASS");
		} catch (Exception e) {
			reportStep("The element identified by the class Name attribute could not be clicked.", "FAIL");
		}
	}

	
	 // This method will click the element using name as locator
	 
	public void clickByName(String name) {
		try{
			driver.findElement(By.name(name)).click();
			reportStep("The element with the name attribute has been clicked.", "PASS");
		} catch (Exception e) {
			reportStep("The element identified by the name attribute could not be clicked.", "FAIL");
		}
	}

	
	 // This method will click the element using link name as locator
	 
	public void clickByLink(String name) {
		try{
			driver.findElement(By.linkText(name)).click();
			reportStep("The element with the linkText has been clicked.", "PASS");
		} catch (Exception e) {
			reportStep("The element identified by the linkText could not be clicked.", "FAIL");
		}
	}

	
	
	 // This method will click the element using xpath as locator
	
	public void clickByXpath(String xpathVal) {
		try{
			driver.findElement(By.xpath(xpathVal)).click();
			
			reportStep("The element with the xpath has been clicked.", "PASS");
		} catch (Exception e) {
			reportStep("The element with the xpath could not be clicked.", "FAIL");
		}
	}
	
	
	public void clickByXpathWithCondition(String status, String xpathVal) {
		
		try{
			
			if(status.equalsIgnoreCase("REQUESTED")) {
				driver.findElement(By.xpath(xpathVal)).click();
			} 			
		
		reportStep("The element with the xpath has been clicked.", "PASS");
	} catch (Exception e) {
		reportStep("The element with the xpath could not be clicked.", "FAIL");
	}
}

	
	
//-------------------------------------Java script Executor---------------------------------------------------------------------------

	
	public void clickByXpathJavascriptExecutor(String xpathVal) {
		
		try{
			JavascriptExecutor ex = (JavascriptExecutor) driver;
			ex.executeScript("arguments[0].click();",driver.findElement(By.xpath(xpathVal)));
			
			reportStep("The element with the JS-Xpath attribute has been clicked.", "PASS");
		} catch (Exception e) {
			reportStep("The element with the JS-Xpath attribute could not be clicked.", "FAIL");
		}
		}
	
	

	
	public void enterByXpathJavascriptExecutor(String xpathVal, String searchKeyword) {
		
		try{
			JavascriptExecutor ex = (JavascriptExecutor) driver;
			ex.executeScript("arguments[0].value=searchKeyword;",driver.findElement(By.xpath(xpathVal)));
			
			reportStep("The data were entered successfully into the field.", "PASS");
		} catch (Exception e) {
			reportStep("The data could not be entered into the field.", "FAIL");
		}
	}
	
	
	
public void enterByXpathJavascriptExecutor(String xpathVal, int searchKeyword) {
		
		try{
			JavascriptExecutor ex = (JavascriptExecutor) driver;
			ex.executeScript("arguments[0].value=searchKeyword;",driver.findElement(By.xpath(xpathVal)));
			
			reportStep("The data were entered successfully into the field.", "PASS");
		} catch (Exception e) {
			reportStep("The data could not be entered into the field.", "FAIL");
		}
	}
	
	
	public void enterByXpathJSExecutor(String locatorValue, String values) {
		
		try {
			JavascriptExecutor ex = (JavascriptExecutor) driver;
			WebElement enterValues = driver.findElement(By.xpath(locatorValue)); 
			ex.executeScript("arguments[0].value=arguments[1];", enterValues, values);
			enterValues.sendKeys(Keys.ENTER);
			
	} catch (NoSuchElementException e) {
		reportStep("The data could not be entered into the field :", "FAIL");
	} catch (Exception e) {
		reportStep("Unknown exception occured while entering the data into the field :", "FAIL");
	}
	}
		
	
	
	/*public void getTextByXpathJavascriptExecutor() {
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String sText =  js.executeScript("return document.documentElement.innerText;").toString();
		System.out.println(sText);
		
	}*/
	
	
	
	
//----------------------------------------------------------------------------------------------------------------
	public void clickByXpathCheck(String xpathVal){
	
		if (!driver.findElement(By.xpath(xpathVal)).isSelected()) {
			driver.findElement(By.xpath(xpathVal)).click();
		}
		
	}
	
//----------------------------------------------------------------------------------------------------------------

	public void clickByLinkNoSnap(String name) {
		try{
			driver.findElement(By.linkText(name)).click();
			reportStep("The element with the link name has been clicked.", "PASS",false);
		} catch (Exception e) {
			reportStep("The element with the link name could not be clicked.", "FAIL");
		}

	}

	public void clickByXpathNoSnap(String xpathVal) {
		try{
			driver.findElement(By.xpath(xpathVal)).click();
			
			reportStep("The element with the xpath has been clicked.", "PASS",false);
		} catch (Exception e) {
			reportStep("The element with the xpath could not be clicked.", "FAIL");
		}		
	}

	
	
	
	
	// This method will mouse over on the element using xpath as locator
	
	public void mouseOverByXpath(String xpathVal) {
		try{
			new Actions(driver).moveToElement(driver.findElement(By.xpath(xpathVal))).build().perform();
			reportStep("The mouseover by the xpath has been performed.", "PASS");
		} catch (Exception e) {
			reportStep("The mouse over by the xpath could not be performed.", "FAIL");
		}
	}

	
	 // This method will mouse over on the element using link name as locator
	
	public void mouseOverByLinkText(String linkName) {
		try{
			new Actions(driver).moveToElement(driver.findElement(By.linkText(linkName))).build().perform();
			reportStep("The mouse over by the link has been performed.", "PASS");
		} catch (Exception e) {
			reportStep("The mouse over by the link could not be performed.", "FAIL");
		}
	}

	
//---------------------------------------------------------------------------------------------------------------------------------------------	
	
	 // This method will return the text of the element using xpath as locator
	
	
	public String getTextByXpath(String xpathVal){
		String bReturn = "";
		try{
			return driver.findElement(By.xpath(xpathVal)).getText();
		} catch (Exception e) {
			reportStep("The element with the xpath could not be found and captured.", "FAIL");
		}
		return bReturn; 
	}
	
	
	
	public String getTextByXpath1(String xpathVal) {
		String id =null;
		   try {
			  id=driver.findElementByXPath(xpathVal).getText();
			   //System.out.println(id);
		       } catch (NoSuchElementException e) {
			     System.err.println("The Element could not be found to get the text");
		        }finally{
			    takeSnap();
		       }
		         return id;
			   }

	
	 // This method will return the text of the element using id as locator

	public String getTextById(String idVal) {
		String bReturn = "";
		try{
			return driver.findElementById(idVal).getText();
		} catch (Exception e) {
			reportStep("The element with the id attribute could not be found.", "FAIL");
		}
		return bReturn; 
	}


//----------------------------------------------------------------------Select Method-----------------------------------------------------------------------	
	
	
	
	 // This method will select the drop down value using id as locator
	 
	public void selectVisibileTextById(String id, String value) {
		try{
			new Select(driver.findElement(By.id(id))).selectByVisibleText(value);;
			reportStep("The element with the id attribute has been selected with the value :"+value, "PASS");
		} catch (Exception e) {
			reportStep("The element with the id attribute could not be selected.", "FAIL");
		}
	}


	public void selectVisibileTextByXPath(String xpath, String value) {
		try{
			new Select(driver.findElement(By.xpath(xpath))).selectByVisibleText(value);;
			reportStep("The element with the xpath has been selected with the value :"+value, "PASS");
		} catch (Exception e) {
			reportStep("The element with the xpath could not be selected.", "FAIL");
		}
	}

	public void selectIndexById(String id, int value) {
		try{
			new Select(driver.findElement(By.id(id))).selectByIndex(value);
			reportStep("The element with the id has been selected with the index value :"+value, "PASS");
		} catch (Exception e) {
			reportStep("The element with the id could not be selected.", "FAIL");
		}
	}

	
//------------------------------------------------------------- Window ----------------------------------------------------------------
	
	
	 // This method will switch to the parent Window
	
	public void switchToParentWindow() {
		try {
						
			Set<String> winHandles = driver.getWindowHandles();
			for (String wHandle : winHandles) {
				driver.switchTo().window(wHandle);
				break;
			}
		} catch (Exception e) {
			reportStep("The window could not be switched to the first window.", "FAIL");
		}
	}

	
	
	 // This method will move the control to the last window
		
	public void clearbyId(String Id){
		try {
			driver.findElementById(Id).clear();
			System.out.println("The existing value has been cleared successfully");
		} catch (NoSuchElementException  e) {
			System.err.println("Unable to find the element to clear the value");
			
		}
		finally{
			takeSnap();
		}
		
	}
	
	
	
	public void clearbyXpath(String xpathValue){
		try {
			driver.findElementByXPath(xpathValue).clear();
			driver.findElementByXPath(xpathValue).sendKeys(Keys.TAB);
			System.out.println("The existing value has been cleared successfully");
		} catch (NoSuchElementException  e) {
			System.err.println("Unable to find the element to clear the value");
			
		}
		finally{
			takeSnap();
		}
		
	}
	
	
	
	public void switchToLastWindow() {
		try {
			Set<String> winHandles = driver.getWindowHandles();
			for (String wHandle : winHandles) {
				driver.switchTo().window(wHandle);
			}
		} catch (Exception e) {
			reportStep("The window could not be switched to the last window.", "FAIL");
		}
	}

//-------------------------------------------------------Alerts-------------------------------------------------------------
	
	 // This method will accept the alert opened

	public void acceptAlert() {
		try {
			driver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {
			reportStep("The alert could not be found.", "FAIL");
		} catch (Exception e) {
			reportStep("The alert could not be accepted.", "FAIL");
		}

	}

	
	 // This method will return the text of the alert
	 
	public String getAlertText() {		
		String text = null;
		try {
			driver.switchTo().alert().getText();
		} catch (NoAlertPresentException e) {
			reportStep("The alert could not be found.", "FAIL");
		} catch (Exception e) {
			reportStep("The alert could not get the text.", "FAIL");
		}
		return text;

	}
	
	
	public void enterValuesInAlert(String enterValues) {
		try {
			driver.switchTo().alert().sendKeys(enterValues);;
		} catch (NoAlertPresentException e) {
			reportStep("The alert could not be found.", "FAIL");
		} catch (Exception e) {
			reportStep("The alert could not be dismissed.", "FAIL");
		}
	}
	
	

	
	 // This method will dismiss the alert opened
	 
	public void dismissAlert() {
		try {
			driver.switchTo().alert().dismiss();
		} catch (NoAlertPresentException e) {
			reportStep("The alert could not be found.", "FAIL");
		} catch (Exception e) {
			reportStep("The alert could not be dismissed.", "FAIL");
		}

	}
	
	public void testData() {
		try {
			driver.switchTo().alert().accept();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	
	// This method will take snapshot of the browser
	
	 
	public long takeSnap(){
		long number = (long) Math.floor(Math.random() * 9000000000L) + 100000000L; 
		try {
			FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE) , new File("./reports/images/"+number+".jpg"));
		} catch (WebDriverException e) {
			reportStep("The browser has been closed.", "FAIL");
		} catch (IOException e) {
			reportStep("The snapshot could not be taken", "WARN");
		}
		return number;
	}
	
//-------------------------------------------------------------------------------------------------------------------
	public void pageRefresh() {
		// TODO Auto-generated method stub
		driver.navigate().refresh();
	}


//-------------------------------------------Dashboard----------------------------------------------------------------
	
	
public void pickTheCar(String xpathValue, String textValue, String xpathValues) {
		
		List<WebElement> vehiclesList = driver.findElements(By.xpath(xpathValue)); 
		
		for (WebElement listOfVehicles : vehiclesList) {
			String totalVehicles = listOfVehicles.getText().trim();
			//System.out.println("Vehicles available in this account = " + totalVehicles);
			
			if(totalVehicles.equalsIgnoreCase(textValue)){
				driver.findElement(By.xpath(xpathValues)).click();   
				acceptAlert();
				break;
			} 
		}		
	}



public void pickThePrimaryDriverAccount(String xpathValue, String textValue, String xpathValues) {
	
	List<WebElement> vehiclesList = driver.findElements(By.xpath(xpathValue)); 
	
	for (WebElement listOfVehicles : vehiclesList) {
		String totalVehicles = listOfVehicles.getText().trim();
		//System.out.println("Vehicles available in this account = " + totalVehicles);
		
		if(totalVehicles.equalsIgnoreCase(textValue)){
			driver.findElement(By.xpath(xpathValues)).click();  
			break;
		} 
	}		
}

//-----------------------------------------------

			
			public void doubleClick(String xpathValue) {
				try {
					/*WebElement fromLocation =  driver.findElement(By.xpath(fromLocator));
					WebElement toLocation = driver.findElement(By.xpath(toLocator));	*/
					Actions actionClass = new Actions(driver);
					//actionClass.dragAndDrop(fromLocation, toLocation);
					WebElement elementDoubleClick = driver.findElement(By.xpath(xpathValue));
					actionClass.doubleClick(elementDoubleClick).build().perform();
					
					reportStep("The data were entered successfully into the field :", "PASS");
				} catch (NoSuchElementException e) {
					reportStep("The data could not be entered into the field :", "FAIL");
				} catch (Exception e) {
					reportStep("Unknown exception occured while entering the data into the field :", "FAIL");
				}
			}
			
			
			//-----------------------------------------------
			
			//CSS Selector:
			
			
			public void clickByCSSTagClass(String cssTagName) {
				try{
					driver.findElement(By.cssSelector(cssTagName)).click();
					reportStep("The element with the id attribute has been clicked.", "PASS");

				} catch (Exception e) {
					reportStep("The element identified by the ID attribute could not be clicked.", "FAIL");
				}
			}
			
			
			 
			public void clicknEnterByCSSTagClass(String cssTagName, String tripTags) {
				try{
					WebElement enterInput  = driver.findElement(By.cssSelector(cssTagName));
					enterInput.sendKeys(tripTags);
					enterInput.sendKeys(Keys.ENTER);
					reportStep("The element with the id attribute has been clicked.", "PASS");

				} catch (Exception e) {
					reportStep("The element identified by the ID attribute could not be clicked.", "FAIL");
				}
			}
			
			
			 
				public void enterByCSSselector(String cssTagName, String tripTags) {
					try{
						WebElement enterInput  = driver.findElement(By.cssSelector(cssTagName));
						enterInput.sendKeys(tripTags);
						enterInput.sendKeys(Keys.ENTER);
						reportStep("The element with the id attribute has been clicked.", "PASS");

					} catch (Exception e) {
						reportStep("The element identified by the ID attribute could not be clicked.", "FAIL");
					}
				}
				
				
				
				//-----------------------------------------------
				
				//Scroll Down - java script executor:
				
				public void scrollDownJavaScriptExecutor(String xpathVal) {
				try {
					waitUntilInvisibilityOfElementLocated(xpathVal);
					WebElement element = driver.findElement(By.xpath(xpathVal));
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].scrollIntoView();", element);
				} catch (Exception e) {
					reportStep("The element identified by the ID attribute could not be clicked.", "FAIL");
				}
				}}
				
				















