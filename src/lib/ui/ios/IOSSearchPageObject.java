package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class IOSSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "id:Search Wikipedia";
        SEARCH_INPUT = "xpath://XCUIElementTypeNavigationBar[@name='Wikipedia, scroll to top of Explore']";
        SEARCH_CANCEL_BUTTON = "id:Close";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{SUBSTRING}')]";
        SEARCH_RESULT_BY_SUBSTRINGS_TITLE_AND_DESCRIPTION_TPL ="xpath://XCUIElementTypeLink[contains(@name,'{TITLE}') and contains(@name,'{DESCRIPTION}')]";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeLink";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
        BUSCKET_BUTTON = "id:Delete";
        YES_BUTTON = "id:Delete All";
    }

    public IOSSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
