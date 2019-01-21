package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * Created by Happy on 21.01.2019.
 */
public class NavigationUI extends MainPageObject {

    private static final String
        MY_LIST_LINK = "//android.widget.FrameLayout[@content-desc='My lists']";

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void clickMyLists() {
        this.waitForElementAndClick(
                By.xpath(MY_LIST_LINK),
                "Can not find navigation button to 'My Lists'.",
                5
        );
    }
}
