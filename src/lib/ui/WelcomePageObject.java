package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomePageObject extends MainPageObject{

    private static final String
    STEP_LEARN_MORE_LINK = "Learn more about Wikipedia",
    STEP_NEW_WAYS_TO_EXPLORE_TEXT = "New way to explore",
    STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK = "Add or edit preferred languages",
    STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "Learn more about date collected",
    NEST_LINK = "Next",
    GET_STARTED_BUTTON = "Get started";

    public WelcomePageObject(AppiumDriver driver){
        super(driver);
    }

     public void waitForLearnMoreLink(){
            this.waitForElementPresent(By.id(STEP_LEARN_MORE_LINK),
                    "Can't find 'Learn more about Wikipedia' link",
                  10);
     }

    public void waitForNewWayToExploreText(){
        this.waitForElementPresent(By.id(STEP_NEW_WAYS_TO_EXPLORE_TEXT),
                "Can't find 'New way to explore' link",
                10);
    }

    public void waitForAddOrEditPreferredLangText(){
        this.waitForElementPresent(By.id(STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK),
                "Can't find 'Add or edit preferred languages' link",
                10);
    }

    public void waitForLearnMoreAboutDateCollectedText(){
        this.waitForElementPresent(By.id(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK),
                "Can't find 'Learn more about date collected' link",
                10);
    }

    public void clickNextButton(){
        this.waitForElementAndClick(By.id(NEST_LINK),
                "Can't find and click 'Next' link",
                10);
    }

    public void clickGetStartedButton(){
        this.waitForElementAndClick(By.id(GET_STARTED_BUTTON),
                "Can't find and click 'Get started' link",
                10);
    }
}
