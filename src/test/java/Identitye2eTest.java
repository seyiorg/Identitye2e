import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import pages.Landing;
import pages.Results;
import support.FileManager;
import support.TestContext;
import support.WebSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static support.TestContext.*;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Identitye2eTest {

    Results results;
    Landing landing;
    private static WebDriver driver;
    FileManager fileManager;
    WebSupport support;
    public static int successCount = 0;
    public static int failCount = 0;
    static List<String> failedCarReg;

    public Identitye2eTest() {
        this.support = new WebSupport();
        fileManager = new FileManager();
        landing = new Landing();
        results = new Results();
        failedCarReg = new ArrayList<String>();
    }

    @BeforeAll
    public void openBrowser() throws Exception {
        driver = WebSupport.getDriver();
    }

    @Test
    public void CarSearchTest() throws IOException {
        fileManager.fileReaderManager(inputFile);
        fileManager.extractCarRegNumbers();

        log.info("****************** Extracted the following Car Registration Numbers: " + carRegNumbers);

        carRegNumbers.forEach(x -> {
            try {
                log.info("****************** Performing a car search for: " + x);

                support.gotoPage("https://cartaxcheck.co.uk/");
                landing.carSearch(x);

                fileManager.fileReaderManager(outputFile);

                results.verifyCarSearchResults(x);
                successCount++;
            } catch (Exception | AssertionError e) {

                failCount++;
                support.getscreenshot(driver, failCount);
                failedCarReg.add(x);
                e.printStackTrace();
                log.error(e.toString());
            }
        });
    }

    @AfterAll
    public static void clearDown() {
        if (failCount != 0) {
            log.error("ERROR: Test FAILED, See Screenshots for" + failedCarReg);
        }
        driver.close();
    }
}
