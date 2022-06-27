package pages;

import model.SearchResult;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.TestContext;
import support.WebSupport;

import java.util.Arrays;
import java.util.List;

public class Results {

    Logger log = LoggerFactory.getLogger(Results.class);
    private final By result_Registration = By.xpath("//*[text() = 'Registration']/..//dd");
    private final By result_Make = By.xpath("//*[text() = 'Make']/..//dd");
    private final By result_Model = By.xpath("//*[text() = 'Model']/..//dd");
    private final By result_Color = By.xpath("//*[text() = 'Colour']/..//dd");
    private final By result_Year = By.xpath("//*[text() = 'Year']/..//dd");
    WebSupport support = new WebSupport();

    public void verifyCarSearchResults(String x) throws InterruptedException {
        getExpectedResultSet(x);

        assert getExpectedResultSet(x) != null;

        SearchResult expectedSearchResult = SearchResult.builder()
                .registration(getExpectedResultSet(x).get(0))
                .make(getExpectedResultSet(x).get(1))
                .model(getExpectedResultSet(x).get(2))
                .color(getExpectedResultSet(x).get(3))
                .year(getExpectedResultSet(x).get(4))
                .build();
        assert TestContext.registration.equals(expectedSearchResult.getRegistration());
        assert TestContext.make.equals(expectedSearchResult.getMake());

    }

    public List<String> getExpectedResultSet(String x) throws InterruptedException {
        List<String> actualResultLine = null;
        for (String actualLine : TestContext.fileLines) {
            if (actualLine.startsWith(x)) {
                actualResultLine = Arrays.asList(actualLine.split("\\s*,\\s*"));
            }
        }
        return actualResultLine;
    }

    public void setActualResultSet() throws InterruptedException {
        TestContext.registration = support.getElementValue(result_Registration);
        TestContext.make = support.getElementValue(result_Make);
        TestContext.model = support.getElementValue(result_Model);
        TestContext.color = support.getElementValue(result_Color);
        TestContext.year = support.getElementValue(result_Year);
    }
}
