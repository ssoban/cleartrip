package cleartrip.automation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class BookFlight extends Baseclass {
	
	@Test
	public void search_flight_OneWay(){
		
		WebElement enter_city= driver.findElement(By.xpath("//*[@id='FromTag']"));
		enter_city.sendKeys("Dub");
		
		
		String suggestndropdown="//*[@id='ui-id-1']/li";
		
		List<WebElement> dropdownpath1 = driver.findElements(By.xpath(suggestndropdown));
		
		// Waiting until the to drop down options get displayed
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(suggestndropdown)));
		String sel1City="Dubai, AE - Dubai International Airport (DXB)";
		// Looping and selecting if it matches with our required option
					for (WebElement clic_sugg : dropdownpath1) {
						
						System.out.println("..................dropdownpath" +dropdownpath1);
						if (clic_sugg.getText().trim().equalsIgnoreCase(sel1City)) {
							clic_sugg.click();
							System.out.println(".................Select  City Name.................");
							break;
						}
					}
	}

}
