package testNGiOSFiles;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;


public class SmartAppTC1iOS{
	
	AppiumDriver driver;
	CommandLine command;
	DefaultExecutor executor;
	Process appiumProcess;
	
	@BeforeSuite
	public void appiumServerStart() throws IOException, InterruptedException{
		Runtime.getRuntime().exec("killall node");
		
		/****** change shell file *****/
		
		appiumProcess = Runtime.getRuntime().exec("/bin/sh /Users/roopali/Desktop/scriptAppium.sh");
		Thread.sleep(10*1000);
		System.out.println("Appium server is started");
		
		
	}
	
	@AfterSuite
	public void appiumServerStop() throws IOException{
		
		Runtime.getRuntime().exec("killall node");
		
	}
	
	@BeforeTest
	public void initalSetUp() throws IOException, InterruptedException{

		DesiredCapabilities cap = new DesiredCapabilities();
		System.out.println("before test start");

		cap.setCapability(MobileCapabilityType.APP_PACKAGE, "com.inquesttechnologies.iqmobile");
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.3");
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 5s");
		cap.setCapability(MobileCapabilityType.APP,"https://drive.google.com/a/smartapp.com/file/d/0ByD5dkfTpnWHT2pqWFVxQURuMWc/view");
		cap.setCapability(MobileCapabilityType.APP,
				"https://drive.google.com/a/smartapp.com/file/d/0ByD5dkfTpnWHT2pqWFVxQURuMWc/view");
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 4*60);
		
		driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);

		System.out.println("###");
	
		Thread.sleep(5000);

		System.out.println("before test");
		
	}
	
	@Test
	public void login() throws InterruptedException{
		Set<String> availableContexts = driver.getContextHandles();
		System.out.println("Total No of Context Found After we reach to WebView = " + availableContexts.size());
		
		for (String context : availableContexts) {
			//System.out.println("Context Name is " + context);
			if (context.contains("WEBVIEW")) {
				//System.out.println("Context Name is " + context);
				driver.context(context);
				System.out.println("context set");
				break;
			}
		}

		
		System.out.println("implicit wait for 20 seconds");
		Thread.sleep(20*1000);
		
		System.out.println("implicit wait end");
	
		WebElement username = driver.findElement(By.xpath("//*[@id=\"loginUsername\"]"));
		username.sendKeys("nreddy@smartapp.com");
		
		WebElement password = driver.findElement(By.xpath("//*[@id=\"loginPassword\"]"));
		password.sendKeys("password" + "\n");
	
		Thread.sleep(10*1000);
		
		System.out.println("Login here");
	}
	
	@Test(dependsOnMethods="login")
	public void clickV16Smoke() throws InterruptedException{
		List<WebElement> columnDivs = driver.findElements(By.xpath("//*[@id=\"columns\"]/div"));
		System.out.println(columnDivs.size());
		for(WebElement divElement:columnDivs){
			WebElement zoneDiv = divElement.findElement(By.xpath("./div[1]"));
			System.out.println(zoneDiv.getText().trim());
			if(zoneDiv.getText().trim().equalsIgnoreCase("v16smoke")){
				zoneDiv.click();
				break;
			}
		}
		
		Thread.sleep(15*1000);
		
		driver.context("NATIVE_APP");
		System.out.println("context set");
		
		By loader = By.id("com.inquesttechnologies.iqmobile:id/smartapplogo");

		WebDriverWait lwait = new WebDriverWait(driver, 90);
		lwait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
		System.out.println("loader1 done");
		
		Thread.sleep(30*1000);
		
		System.out.println("wait done");
		
		driver.context("WEBVIEW_com.inquesttechnologies.iqmobile");
		
		System.out.println("context set");
		
	}
	
	@Test(dependsOnMethods="clickV16Smoke")
	public void clickSmartApps(){
		
		((JavascriptExecutor)driver).executeScript("document.getElementById(\"smartapps\").click();");

		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		
		System.out.println("smart app page ");
		
	}
	
	@Test(dependsOnMethods="clickSmartApps")
	public void clickQAPushNotification() throws InterruptedException{
	
		WebElement qAPushNotification = driver.findElement(By.xpath(".//*[text()='QA - Push Notification']"));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", qAPushNotification);

		System.out.println("qa push notification clicked! ");	
	
		Thread.sleep(10*1000);
	}
	
	@Test(dependsOnMethods="clickQAPushNotification")
	public void clickFAB1() throws InterruptedException{
		WebElement fabButton = driver.findElement(By.id("ext-iqfabbutton-2"));
		tapOnWebElement(fabButton, driver);
		
		Thread.sleep(10*1000);
	}
	
	@Test(dependsOnMethods="clickFAB1")
	public void clickQAPushNotification1(){
		
		WebElement qAPushNotification1 = driver.findElement(By.xpath(".//*[text()='QA - Push Notification1']"));
		tapOnWebElement(qAPushNotification1, driver);
	
		System.out.println("qa push notification1 clicked! ");
	}
	
	@Test(dependsOnMethods="clickQAPushNotification1")
	public void enterDataQAPushNotification1() throws InterruptedException{
		
		WebElement pilotName = driver.findElement(By.name("f1889110-82d5-46ae-a7ce-f029c9598737"));
		((JavascriptExecutor)driver).executeScript("arguments[0].value='Lorem ipsum dolor sit amet';", pilotName);

		WebElement airlinesName = driver.findElement(By.name("5164ca00-49ac-47bd-a80a-d8d804bed494"));	
		((JavascriptExecutor)driver).executeScript("arguments[0].value='consectetur adipiscing elit';", airlinesName);

		WebElement location = driver.findElement(By.name("acc-label"));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", location);

		Thread.sleep(10*1000);
		WebElement enterLocation = driver.findElement(By.name("48ba920e-fa9c-40f1-affa-8372e8d42668"));
		((JavascriptExecutor)driver).executeScript("arguments[0].value='sed do eiusmod tempor incididunt';", enterLocation);

	}
	
	@Test(dependsOnMethods="enterDataQAPushNotification1")
	public void clickFAB2() throws InterruptedException{
		
		WebElement fabButton2 = driver.findElement(By.id("ext-iqfabbutton-4"));
		tapOnWebElement(fabButton2, driver);
		System.out.println("fab clicked...");
	
		Thread.sleep(5*1000);
	}
	
	@Test(dependsOnMethods="clickFAB2")
	public void clickADDFAB(){	
	
		WebElement saveAndClose = driver.findElement(By.xpath(".//*[text()='Save & Close']"));
		tapOnWebElement(saveAndClose, driver);

	}
	
	
	public static void tapOnWebElement(WebElement element, AppiumDriver driver) {

		JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
	        // Fetch DOM document full size
	        double documentWidth = Double.parseDouble(jsExecutor.executeScript("return document.documentElement.scrollWidth").toString());
	        double documentHeight = Double.parseDouble(jsExecutor.executeScript("return document.documentElement.scrollHeight").toString());

	        // Fetch element center position from the DOM
	        double elementLeftCenter = element.getLocation().getX() + (element.getSize().getWidth() / 2);
	        double elementTopCenter = element.getLocation().getY() + (element.getSize().getHeight() / 2);

	        ((AppiumDriver) driver).context("NATIVE_APP");
	        // Fetch the android WebView element
	        WebElement androidWebView = driver.findElement(By.xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.webkit.WebView[1]"));

	        double screenXMultiplier = androidWebView.getSize().getWidth() / documentWidth;
	        double screenYMultiplier = androidWebView.getSize().getHeight() / documentHeight;

	        int tapX = (int)(elementLeftCenter * screenXMultiplier);
	        int tapY = (int)(elementTopCenter * screenYMultiplier) + androidWebView.getLocation().getY();

	        // Send a tap event on the specific button position
	        driver.tap(1, tapX, tapY, 200);

	        driver.context("WEBVIEW_com.inquesttechnologies.iqmobile");
	}		
		
	@AfterTest
	public void tearDown(){
		
	}
}
