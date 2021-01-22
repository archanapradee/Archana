package pagefactory;

//import java.dataProviders.ConfigFileReader;
import dataProvider.ConfigFileReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.concurrent.TimeUnit;

public class HomePage_PF{

	WebDriver driver;
	ConfigFileReader configFileReader;
	@FindBy(xpath = "//header/div[1]/nav[1]/ul[1]/li[1]/a[1]")
	WebElement lnk_weather;
	@FindBy(xpath = "//a[contains(text(),'2-hr Nowcast')]")
	WebElement lnk_twoHour;


	public HomePage_PF(WebDriver driver) {
		this.driver = driver;

		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);

	}

	public void clickWeatherLink() {

		lnk_weather.click();
		try { Thread.sleep(2000);}

		catch (InterruptedException e){
			e.printStackTrace();
		}

	}
	public boolean isWeatherLinkDisplayed() {
		boolean returnVal;
		try{
		lnk_weather.isDisplayed();
			returnVal = true;
		} catch (Exception e) {
			returnVal = false;
		}
		return returnVal;


	}

	public boolean isTwoHourLinkDisplayed() {

		boolean returnVal;
		try {
			lnk_twoHour.isDisplayed();
			returnVal = true;
		} catch (Exception e) {
			returnVal = false;
		}
		return returnVal;

	}

	public void clickTwoHourLink() {

		lnk_twoHour.click();
		try { Thread.sleep(2000);}

		catch (InterruptedException e){
			e.printStackTrace();
		}

	}
	public void navigateTo_HomePage() {
		driver.navigate().to(configFileReader.getApplicationUrl());

		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	

}
