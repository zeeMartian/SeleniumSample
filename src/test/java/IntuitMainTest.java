import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import java.util.List;
/**
 * IntuitMainTest - Contains Selenium WebDriver test cases for http://intuit.com.
 * Created by Zackery Martin on 3/12/2015.
 */
public class IntuitMainTest {
    RemoteWebDriver driver; //the global RemoteWebDriver that will be created before a test run
    WebDriverUtils util = new WebDriverUtils();
    WebDriverWait wait; //object used to implement waiting behavior for the RemoteWebDriver

    /**
     * setUp - Creates the RemoteWebDriver object and loads the main page before any tests are able to run.
     */
    @Before
    public void setUp(){
        driver = util.getRemoteWebDriver("firefox");
        wait = new WebDriverWait(driver, 10);
        driver.get("http://intuit.com");
    }

    /**
     * tearDown - Destroy the RemoteWebDriver after a test finishes.
     */
    @After
    public void tearDown(){
        driver.quit();
    }

    /**
     * Tests that the "Country" link on the top of the main page redirects the user to the appropriate page.
     */
    @Test
    public void testCountryLink(){
        //click the country link
        util.clickElementByLocator(driver, By.linkText("Country"));
        //assert that the expected url was encountered
        assertEquals("testCountryLink failed, the browser was not redirected to the appropriate page after " +
                "clicking the 'Country' link.",driver.getCurrentUrl().toString(), "http://global.intuit.com/choose-country.jsp");
    }

    /**
     * Tests that changing the country to Brazil results in the language to change to Portuguese.
     */
    @Test
    public void testCountryChangeBrazil(){
        //find and click the Country link
        util.clickElementByLocator(driver, By.linkText("Country"));
        util.clickElementByLocator(driver, By.linkText("Brasil (Português)"));
        //wait until an expected page element is visible before proceeding
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("pelling-wrapper")));
        //grab an element from the navigation bar & assert that it is in Portuguese
        WebElement ele = util.findElementByLocator(driver, By.id("Menu_1"));
        assertEquals("testCountryChangeBrazil failed, site language did not change to Portuguese.", ele.getText(), "Início");
    }

    /**
     * Tests that the search function returns only results that contain the search term.
     */
    @Test
    public void testSearch(){
        //search for the term 'help'
        util.typeToElementByLocator(driver, By.id("search_term"), "help");
        util.clickElementByLocator(driver, By.id("searchSubmitButton"));
        //wait until the main content div is loaded before proceeding
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mainContent")));
        //gather the returned search result objects
        List<WebElement> search_results = util.findElementsByLocator(driver, By.className("search-results-title"));
        boolean result = true;
        for(WebElement search_item: search_results){
            //check if the term searched exists in the search results' title text
            if(!search_item.getText().toLowerCase().contains("help")){
                //if it does not, check if the description of the search result item contains the term
                WebElement search_item_desc = util.findElementByLocator(driver, By.className("search-results-description"));
                if(!search_item_desc.getText().toLowerCase().contains("help")){
                    //if the term is not found in either the title or description, fail the test
                    result = false;
                }
            }
        }
        //assert that the search results all contained an instance of the term
        assertEquals(true, result);
    }
}