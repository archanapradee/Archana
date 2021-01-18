package pagefactory;

import com.sun.org.apache.xerces.internal.xinclude.XIncludeHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;

public class WeatherPage_PF {

	WebDriver driver;
	@FindBy(xpath = "//body/div[1]/div[1]/ul[1]/li[3]/a[1]")
	private WebElement FourDayLink;
	@FindBy(xpath = "//tbody/tr[2]/td[2]/span[1]")
	private WebElement HighTemp;
	@FindBy(xpath = "//tbody/tr[2]/td[2]/span[2]")
	private WebElement LowTemp;
	@FindBy(xpath = "//tbody/tr[2]/td[1]")
	private WebElement DayAfterTomorrow;

	public WeatherPage_PF(WebDriver driver) {

		this.driver = driver;

		//This initElements method will create all WebElements

		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);

	}
	public boolean isFourDayLinkDisplayed() {

		return FourDayLink.isDisplayed();

	}

	public void clickFourDayLink() {

		FourDayLink.click();

	}

	public String getHighTemp(){
		return HighTemp.getText();
	}
	public String getLowTemp(){
		return LowTemp.getText();
	}

	public String getDate(){
		return DayAfterTomorrow.getText();
	}

	}
