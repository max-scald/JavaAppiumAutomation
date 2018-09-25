package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

abstract public class NavigationUI extends MainPageObject {
    protected static String MY_LIST_LINK; // = "xpath://android.widget.FrameLayout[@content-desc='My lists']";

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void clickToMyLists() {
        this.waitForElementAndClick(MY_LIST_LINK,
                "Can't find navigation button to My lists",
                5);
    }
}
