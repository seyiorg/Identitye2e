package pages;

import org.openqa.selenium.By;
import org.picocontainer.annotations.Inject;
import support.WebSupport;

public class Landing {

    private final By enterRegTxtField = By.id("vrm-input");
    private final By freeCarCheckBtn = By.xpath("//button[text() = 'Free Car Check']");
        WebSupport support = new WebSupport();
    Results results = new Results();

    public void carSearch(String carRegNos) throws Exception {
        this.support.completeTextField(enterRegTxtField, carRegNos);
        support.clickOnElement(freeCarCheckBtn);
        results.setActualResultSet();
    }
}
