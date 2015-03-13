import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * WebDriverUtils - Helper functions for Selenium RemoteWebDriver.
 *
 * Created by Zackery Martin on 3/12/2015.
 */
public class WebDriverUtils {
    final static int MAX_ATTEMPTS = 2;
    public static final String USERNAME = "zack35"; //Browserstacks test user account name
    public static final String AUTOMATE_KEY = "goyxh8KtHGEA724awLpQ"; //Browserstacks test user key
    public static final String URL = "http://" + USERNAME + ":" + AUTOMATE_KEY + "@hub.browserstack.com/wd/hub";

    /**
     * WebDriverUtils - default constructor.
     */
    public WebDriverUtils(){ }

    /**
     * getRemoteWebDriver - Returnd a RemoteWebDriver connected to the Browserstack test account hub.
     * Current only supports Firefox.
     * @param browser
     * @return RemoteWebDriver driver
     */
    public RemoteWebDriver getRemoteWebDriver(String browser){
        RemoteWebDriver driver = null;
        if(browser.equals("firefox") || browser.equals("Firefox") || browser.equals("Mozilla Firefox")){
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            try{
                driver =  new RemoteWebDriver(new URL(URL), capabilities);
            } catch (MalformedURLException ex){
                System.err.println("MalformedURL when creating RemoteWebDriver.");
                System.out.println("MalformedURL when creating RemoteWebDriver.");
            }
        }
        else if(browser.equals("chrome") || browser.equals("Chrome") || browser.equals("Google Chrome")){
            //do nothing for now
        }
        else {
            System.out.println("Unsupported Browser. Testing terminated.");
            System.err.println("Unsupported Browser : " + browser + "encountered on " + System.currentTimeMillis() +".");
        }
        return driver;
    }

    /**
     * findElementByLocator() - Uses polling to locate and return an element using the provided locator function.
     * @param locator
     * @return WebElement ele
     */
    public static WebElement findElementByLocator(RemoteWebDriver driver, By locator) {
        driver.manage().timeouts().implicitlyWait(9, TimeUnit.SECONDS);
        WebElement ele = null;
        boolean unfound = true;
        int tries = 0;
        while (unfound && tries < MAX_ATTEMPTS) {
            tries += 1;
            try {
                ele = driver.findElement(locator);
                unfound = false; // success
            } catch (StaleElementReferenceException ser) {
                unfound = true;
            } catch (NoSuchElementException nse) {
                unfound = true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return ele;
    }

    /**
     * findElementsByLocator() - Uses polling to locate and return a list of elements using the provided locator function.
     *
     * @param locator
     * @return WebElement ele
     */
    public static List<WebElement> findElementsByLocator(RemoteWebDriver driver, By locator){
        driver.manage().timeouts().implicitlyWait(9, TimeUnit.SECONDS);
        List<WebElement> ele = null;
        boolean unfound = true;
        int tries = 0;
        while (unfound && tries < MAX_ATTEMPTS) {
            tries += 1;
            try {
                ele = driver.findElements(locator);
                unfound = false; // success
            } catch (StaleElementReferenceException ser) {
                unfound = true;
            } catch (NoSuchElementException nse) {
                unfound = true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return ele;
    }

    /**
     * clickElementByLocator() - Uses polling to find and click a WebElement using the provided locator.
     * @param locator
     * @return
     */
    public static WebElement clickElementByLocator(RemoteWebDriver driver, By locator) {
        driver.manage().timeouts().implicitlyWait(9, TimeUnit.SECONDS);
        WebElement ele = null;
        boolean unfound = true;
        int tries = 0;
        while (unfound && tries < MAX_ATTEMPTS) {
            tries += 1;
            try {
                ele = driver.findElement(locator);
                ele.click(); // try clicking the element
                unfound = false; // success
            } catch (StaleElementReferenceException ser) {
                unfound = true;
            } catch (NoSuchElementException nse) {
                unfound = true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return ele;
    }

    /**
     * typeToElementByLocator() - Uses polling to find and send the provided text to a WebElement.
     *
     * @param locator
     * @return WebElement ele
     */
    public static void typeToElementByLocator(RemoteWebDriver driver, By locator, String text){
        driver.manage().timeouts().implicitlyWait(9, TimeUnit.SECONDS);
        WebElement ele = null;
        boolean unfound = true;
        int tries = 0;
        while (unfound && tries < MAX_ATTEMPTS) {
            tries += 1;
            try {
                ele = driver.findElement(locator);
                ele.sendKeys(text); // try typing to the element
                unfound = false; // success
            } catch (StaleElementReferenceException ser) {
                unfound = true;
            } catch (NoSuchElementException nse) {
                unfound = true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
}
