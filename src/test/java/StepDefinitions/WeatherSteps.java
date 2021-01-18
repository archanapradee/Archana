package StepDefinitions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.maven.surefire.shared.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pagefactory.HomePage_PF;
import pagefactory.WeatherPage_PF;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;



public class WeatherSteps {

     WebDriver driver=null;

    WeatherPage_PF weather;
    HomePage_PF home;
   private static final String BASE_URL = "https://api.data.gov.sg/v1/environment/4-day-weather-forecast";
   private static final String URL = "http://www.weather.gov.sg/home/";

    String path1 = System.getProperty("user.dir") + "/target/Reports/Extent_Automation_Report.html";
    ExtentHtmlReporter reporter = new ExtentHtmlReporter(path1);
    ExtentReports extent = new ExtentReports();

   public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
       String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
       TakesScreenshot ts = (TakesScreenshot) driver;
       File source = ts.getScreenshotAs(OutputType.FILE);
       //after execution, screenshots saved under WeatherToday\Screenshots folder

       String destination = System.getProperty("user.dir") + "/Screenshots/"+screenshotName+dateName+".png";
       File finalDestination = new File(destination);
       FileUtils.copyFile(source, finalDestination);
       return destination;
   }
    @Given("user is navigate to weather home page")
    public void user_is_navigate_to_weather_home_page() {

        ExtentTest logger=extent.createTest("user_is_navigate_to_weather_home_page");
        logger.assignCategory("Automation Test Submission", "Extent-Report");
        logger.assignAuthor("Archana Pradeep");
        String projectPath = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", projectPath+"/src/test/resources/drivers/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        extent.flush();
        driver.navigate().to(URL);
        driver.manage().timeouts().pageLoadTimeout(40,TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @When("user select two hour NowCast")
    public void user_select_two_hour_now_cast() throws Exception {

        home = new HomePage_PF(driver);
        home.clickWeatherLink();
        ExtentTest logger = extent.createTest("user_select_two_hour_now_cast");
        extent.attachReporter(reporter);
        if(home.isTwoHourLinkDisplayed())
        {
            home.clickTwoHourLink();
            logger.log(Status.PASS, "Two Hour NowCast Weather Loaded");
            String screenshotPath = WeatherSteps.getScreenshot(driver, "before");
            logger.log(Status.PASS , "Two hour page nowCast_Success",MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }
        else
        {

            logger.log(Status.FAIL, "Two Hour NowCast Weather not Displayed");
            String screenshotPath = WeatherSteps.getScreenshot(driver, "before");
            logger.log(Status.FAIL , "Two hour page nowCast_FAIL",MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }

    }

     @When("User select four day forecast")
    public void user_select_four_day_forecast() throws Exception {
       weather = new WeatherPage_PF(driver);
         extent.attachReporter(reporter);

         ExtentTest logger=extent.createTest("user_select_four_day_forecast");
        if(weather.isFourDayLinkDisplayed())
        {
            weather.clickFourDayLink();
            logger.log(Status.PASS, "four_day_forecast Weather displayed");
            String screenshotPath = WeatherSteps.getScreenshot(driver, "before");
            logger.log(Status.PASS , "four_day_forecast",MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }
        else
        {

            logger.log(Status.FAIL, "four_day_forecast Weather not displayed");
            String screenshotPath = WeatherSteps.getScreenshot(driver, "before");
            logger.log(Status.FAIL , "four_day_forecast",MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }

    }

    @Then("user validate day after tomorrow weather details Vs API Response")
    public void user_validate_day_after_tomorrow_weather_details_vs_api_response() throws Exception {
        weather = new WeatherPage_PF(driver);
        String HighTemp = weather.getHighTemp();
        String lowTemp = weather.getLowTemp();
        String Tomo = weather.getDate();
        System.out.println("Low temperature is " + lowTemp);
        System.out.println("High temperature is " + HighTemp);
        System.out.println("Day After Tomo " + Tomo);
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        Response response = request.get();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        List<String> jsonResponse_low = response.jsonPath().getList("items.forecasts.temperature.low[0]");
        List<String> jsonResponse_high = response.jsonPath().getList("items.forecasts.temperature.high[0]");


        String json_tempLow = jsonResponse_low.toArray()[1].toString();
        String Web_LowTemp = lowTemp.substring(0, 2);

        String json_tempHigh = jsonResponse_high.toArray()[1].toString();
        String Web_HighTemp =HighTemp.substring(0, 2);
        extent.attachReporter(reporter);
        ExtentTest logger=extent.createTest("user_validate_day_after_tomorrow_weather_details_vs_api_response");

        if ((json_tempLow.equals(Web_LowTemp)) && (json_tempHigh.equals(Web_HighTemp))) {

            logger.log(Status.INFO, "High Temp from UI:" +Web_HighTemp);
            logger.log(Status.INFO, "High Temp from API:" +json_tempHigh);
            logger.log(Status.INFO, "Low Temp from UI:" +Web_LowTemp);
            logger.log(Status.INFO, "Low Temp from API:" +json_tempLow);
            String screenshotPath = WeatherSteps.getScreenshot(driver, "before");
            logger.log(Status.PASS , "user_validate_weather_details as pass",MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }
        else{
            logger.log(Status.FAIL, "High Temp from UI:" +Web_HighTemp);
            logger.log(Status.INFO, "High Temp from API:" +json_tempHigh);
            logger.log(Status.INFO, "Low Temp from UI:" +Web_LowTemp);
            logger.log(Status.INFO, "Low Temp from API:" +json_tempLow);
            String screenshotPath = WeatherSteps.getScreenshot(driver, "before");
            logger.log(Status.FAIL , "user_validate_weather_details as fail",MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());

        }

        extent.flush();
        driver.close();
        driver.quit();

    }}




