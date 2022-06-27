package support;

import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static support.TestContext.configFile;

public class WebSupport extends FileManager{

    static WebDriver driver;
    Logger log = LoggerFactory.getLogger(WebSupport.class);

    public static WebDriver getDriver() throws IOException {
        String browser = getConfigDataFileData("browser");
        switch (browser.toLowerCase()) {
            case "ie":
                driver = new InternetExplorerDriver();
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "browsers//chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                driver = new ChromeDriver();
                break;
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    public void gotoPage(String pageURL) throws IOException {
        driver.get(pageURL);
    }

    public void completeTextField(By by, String text) throws InterruptedException {
        getElementWhenReady(by).sendKeys(text);
    }

    public WebElement getElementWhenReady(By by) throws InterruptedException {
        int i = 0;
        while (!isElementPresent(by)) {
            if (++i >= 10) {
                throw new NoSuchElementException("Element not available");
            }
        }
        return driver.findElement(by);
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void clickOnElement(By by) throws Exception {
        driver.findElement(by).click();
    }

    public String getElementValue(By selector) throws InterruptedException {
        return getElementWhenReady(selector).getText();
    }

    public void getscreenshot(WebDriver driver, int num) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(scrFile, new File("screenshots\\screenshot"+num+".png"));
        } catch (IOException e) {
            log.error(String.valueOf(e));
        }
    }
}
