package com.browserstack.stepdefs;

import com.browserstack.RunWebDriverCucumberTests;
import com.browserstack.util.Utility;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;

import static org.junit.Assert.assertTrue;


public class Steps {

    private WebDriver driver;
    List<WebElement> sProductLink,sProductName,sProductPrice;
    String isLocalDriver;


    @Before

    public void beforeScenario() {
        isLocalDriver = System.getProperty("isLocalDriver", "false");
        System.out.println(isLocalDriver);
        if (isLocalDriver.equals("true")) {
              System.setProperty("webdriver.chrome.driver", "C:\\project\\chromedriver_win32\\chromedriver.exe");
              driver= new ChromeDriver();
        }else {
           //
            driver = RunWebDriverCucumberTests.getManagedWebDriver().getWebDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


    @Given("Using Selenium load the flipkart.com desktop home page")
    public void usingSeleniumLoadTheFlipkartComDesktopHomePage() throws InterruptedException {
        driver.get("https://www.flipkart.com/");
        driver.findElement(By.xpath("/html/body/div[2]/div/div/button")).click();
        Thread.sleep(2000);
    }

    @When("^Search for the (.*) on that page$")
    public void searchForTheProductOnThatPage(String sProduct) throws InterruptedException {
       WebElement searchBar = driver.findElement(By.name("q")) ;
        searchBar.click();
        searchBar.sendKeys(sProduct);
        searchBar.submit();
        Thread.sleep(2000);
    }

    @And("On the search results click on Mobiles in categories")
    public void onTheSearchResultsClickOnInCategories() throws InterruptedException {
        driver.findElement(By.xpath("//a[@title = 'Mobiles']")).click();
        Thread.sleep(2000);
    }

    @When("Apply the following filters Samsung and select Flipkart assured")
    public void applyTheFollowingFiltersBrandAndSelectFlipkartAssured() throws InterruptedException {
        driver.findElement(By.xpath("//div[@title = 'SAMSUNG']")).click();
        Thread.sleep(2000);
      //  driver.findElement(By.className("_30VH1S")).click();
        driver.findElement(By.xpath("//img[contains(@src,'img/fa_62673a.png')]")).click();
        Thread.sleep(2000);

    }

    @And("Sort the entries with Price -> High to Low")
    public void sortTheEntriesWithPriceHighToLow() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='container']/div/div[3]/div/div[2]/div[1]/div/div/div[3]/div[4]")).click();

    }




    @When("Read the set of results that show up on page 1")
    public void readTheSetOfResultsThatShowUpOnPage() throws InterruptedException {
        Thread.sleep(1000);
        sProductLink = driver.findElements(By.xpath("//*[@id=\"container\"]/div/div[3]/div/div[2]/div/div/div/div/a"));
        sProductName = driver.findElements(By.xpath("//*[@id=\"container\"]/div/div[3]/div/div[2]/div/div/div/div/a/div[2]/div[1]/div[1]"));
        sProductPrice = driver.findElements(By.xpath("//*[@id=\"container\"]/div/div[3]/div/div[2]/div/div/div/div/a/div[2]/div[2]/div[1]/div[1]/div[1]"));

//        System.out.println("Href value of link: "+sProductLink.size());




    }

    @Then("Create a list with the Product Name,Display Price,Link to Product Details Page and print this on the console")
    public void createAListWithTheProductNameDisplayPriceLinkToProductDetailsPageAndPrintThisOnTheConsole() {
        for (int i = 0; i < sProductLink.size(); i++) {
            System.out.println("Product Name:" +sProductName.get(i).getText() + "\tPrice:"+ sProductPrice.get(i).getText().replace("₹","INR")+ "\tLink: "+sProductLink.get(i).getAttribute("href"));
        }
        assertTrue(sProductLink.size()==24);
    }
    @After
    public void teardown(Scenario scenario) throws Exception {
        if (isLocalDriver.equals("false"))
        if (scenario.isFailed()) {
            Utility.setSessionStatus(driver, "failed", String.format("%s failed.", scenario.getName()));
        } else {
            Utility. setSessionStatus(driver, "passed", String.format("%s passed.", scenario.getName()));
        }
        Thread.sleep(2000);
        driver.quit();
    }
}