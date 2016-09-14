package testNGFiles;

import org.testng.annotations.BeforeSuite;

public class SmartAppTC2 {
	
	@BeforeSuite
	public void startAppiumServer(){
		System.out.println("start server");
	}
}
