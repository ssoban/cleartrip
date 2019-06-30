package cleartrip.automation;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginNSerachHotel extends Baseclass {

	@Test(priority = 1)
	public void NavigatToLogin() throws InterruptedException {
		try {

			// click trip
			driver.findElement(By.xpath("//*[@id='userAccountLink']")).click();
			Thread.sleep(2000);
			// click signin
			driver.findElement(By.xpath("//*[@id='SignIn']")).click();
			System.out.println(".................Redirecting to login Frame.................");
		} catch (Exception e) {

			System.out.println(e);
		}

	}

	@Test(priority = 2)
	public void LoginWithValidCredentials() throws InterruptedException {

		try {
			Thread.sleep(2000);
			System.out.println(".................Switching to frame.................");
			driver.switchTo().frame("modal_window");

			Thread.sleep(5000);
			// Enter Email
			driver.findElement(By.xpath("//*[@id='signinForm']/dl/dd[1]//*[@id='email']"))
					.sendKeys("cleartriptester@gmail.com");
			Thread.sleep(2000);
			// Enter password
			driver.findElement(By.xpath("//*[@id='signinForm']/dl/dd[3]//*[@id='password']")).sendKeys("cleartrip");
			Thread.sleep(2000);
			// Enter submit
			driver.findElement(By.xpath("//*[@id='signinForm']/dl/dd[5]//*[@id='signInButton']")).click();
			Thread.sleep(5000);
			System.out.println(".................Login Success.................");
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Test(priority = 3)
	public void HotelSearch() throws InterruptedException {

		try {
			driver.findElement(By.xpath("//*[@id='Home']/div/aside[1]/nav/ul[1]/li[2]/a[1]")).click();

			Thread.sleep(3000);
			WebElement selCity = driver.findElement(By.xpath("//*[@id='Tags']"));
			selCity.sendKeys("Mumbai");
			//selCity.sendKeys(Keys.ENTER);
			String dropdownpath = "//*[@id='ui-id-1']";
			WebElement dropdownpath1 = driver.findElement(By.xpath(dropdownpath));
			// Waiting until the to drop down options get displayed
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dropdownpath)));
			// Option that you want to select
			String sel1City = "Mumbai, Maharashtra, India";
			// Fetching all the to drop down options and storing in the list
			List<WebElement> dropdownsugg = dropdownpath1.findElements(By.tagName("li"));

			// Looping and selecting if it matches with our required option
			for (WebElement clic_sugg : dropdownsugg) {
				if (clic_sugg.getText().trim().equalsIgnoreCase(sel1City)) {
					clic_sugg.click();
					System.out.println(".................Enter City Name.................");
					break;
				}
			}

			WebElement datetable = driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[1]/table/tbody"));

			// select datepicker
			List<WebElement> checkin = datetable.findElements(By.tagName("td"));
			for (WebElement ele : checkin) {

				String date = ele.getText();

				if (date.equalsIgnoreCase("26")) {
					// select checkin date
					ele.click();
					break;
				}

			}
			Thread.sleep(2000);
			WebElement datetable1 = driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[1]/table/tbody"));

			List<WebElement> checkout = datetable1.findElements(By.tagName("td"));
			for (WebElement ele1 : checkout) {

				String date1 = ele1.getText();

				if (date1.equalsIgnoreCase("29")) {
					// select checkout date
					ele1.click();
					System.out.println(".................DateSelected.................");
					break;
				}

			}
			Thread.sleep(2000);
			WebElement searchbtn = driver.findElement(By.xpath("//*[@id='SearchHotelsButton']"));
			searchbtn.click();
			System.out.println(".................Searching hotel.................");
			Thread.sleep(10000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(priority = 4)
	public void ViewAndSelectHotel() throws InterruptedException {
		Thread.sleep(10000);
		List<WebElement> hotelList = driver.findElements(By.xpath("//*[@class='listItem listUnit clearFix   ']"));
		int count = 0;
		boolean found = false;
		String HotelFound = null;
		for (WebElement ele1 : hotelList) {

			if (found) {
				break;
			}
			List<WebElement> priceList = driver.findElements(By.xpath("//*[@id='perRoomPrDisp']/strong"));

			if (count < priceList.size()) {
				String price = priceList.get(count).getText().trim();

				String[] newprice = price.split("₹");

				String finalPrice = "";
				if (newprice.length > 2) {
					finalPrice = newprice[2];
				} else {
					finalPrice = newprice[1];
				}
				System.out.println("................."+finalPrice+".................");

				int clickableprice = Integer.parseInt(finalPrice.replaceAll(",", "").trim());

				if (clickableprice > 1000) {
					WebElement hotelName = driver.findElement(
							By.xpath("//*[@id=" + ele1.getAttribute("id") + "]/section/div[3]/nav/ul/li[1]/h2/a"));
					WebElement showDetails = driver.findElement(
							By.xpath("//*[@id=" + ele1.getAttribute("id") + "]/section/div[3]/div[3]/div/button[2]"));
					HotelFound = hotelName.getText();
					showDetails.click();
					Thread.sleep(3000);
					found = true;
				}
			}
			count++;

		}
		System.out.println(".................Selected hotel................." + HotelFound);
		String Maintab = driver.getWindowHandle();

		// To handle all new opened window.
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();

		while (i1.hasNext()) {
			String Nexttab = i1.next();

			if (!Maintab.equalsIgnoreCase(Nexttab)) {

				// Switching to Child window
				driver.switchTo().window(Nexttab);

			}
		}
	
		WebElement hotelNameNewTab = driver
				.findElement(By.xpath("//*[@id='hotelDetailsHeader']/div/div[1]/div[2]/div/div[1]/h1"));
		String getHotName = hotelNameNewTab.getText();
		String[] fullHotelName = getHotName.split("\\r?\\n");
		System.out.println(".................Opened Same hotel................." + fullHotelName[0]);
		Assert.assertEquals(fullHotelName[0], HotelFound);
	}

}
