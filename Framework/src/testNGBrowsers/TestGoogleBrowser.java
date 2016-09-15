package testNGBrowsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestGoogleBrowser {
	

	WebDriver driver;
	
	@BeforeTest
	public void openBrowser(){
		System.setProperty("webdriver.chrome.driver", "/Users/roopali/Downloads/chromedriver");
		driver = new ChromeDriver();
		System.out.println("opens browser");
		driver.get("http://www.google.com");
		
	}
	@Test
	public void testBrowser(){
		System.out.println("testing browser");
	}
	@AfterTest
	public void closeBrowser(){
		driver.quit();
	}

}
