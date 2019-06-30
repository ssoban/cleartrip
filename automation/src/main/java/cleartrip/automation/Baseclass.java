package cleartrip.automation;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;

public class Baseclass
{
	
	public WebDriver driver;
	String URL="https://www.cleartrip.com/";
	String browser="chrome";
	
 @BeforeTest
	public void init() 
	{
		setup(browser);
		navigate();
		
	 }

	public void setup(String browser)
	{
		
			if(browser.equalsIgnoreCase("firefox"))
			{
				System.out.println(System.getProperty("user.dir"));
			
				System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
				
			driver = new FirefoxDriver();
			}
	        else if(browser.equalsIgnoreCase("chrome"))
	        	
	        	
	        {
	        	
	        	//Create prefs map to store all preferences 
	        	Map<String, Object> prefs = new HashMap<String, Object>();

	        	//Put this into prefs map to switch off browser notification
	        	prefs.put("profile.default_content_setting_values.notifications", 2);

	        	//Create chrome options to set this prefs
	        	ChromeOptions options = new ChromeOptions();
	        	options.setExperimentalOption("prefs", prefs);
	        	System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
			//System.setProperty("webdriver.chrome.driver","/drivers/chromedriver.exe" );
			driver = new ChromeDriver(options);
			}
			
	}
	public void navigate() 
	{
		    driver.manage().window().maximize();
		    driver.navigate().to(URL);
		    System.out.println("................Navigation to url success...............");
		    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
	
}

 

