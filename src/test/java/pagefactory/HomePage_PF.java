package pagefactory;

//import java.dataProviders.ConfigFileReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HomePage_PF{

	WebDriver driver;

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

	}
	public boolean isTwoHourLinkDisplayed() {

		return lnk_twoHour.isDisplayed();

	}

	public void clickTwoHourLink() {

		lnk_twoHour.click();

	}

	

}
