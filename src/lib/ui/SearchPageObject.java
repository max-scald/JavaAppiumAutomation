package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import static junit.framework.TestCase.assertTrue;

abstract public class SearchPageObject extends MainPageObject{
    protected static String
                        SEARCH_INIT_ELEMENT,
                        SEARCH_INPUT,
                        SEARCH_CANCEL_BUTTON,
                        SEARCH_RESULT_BY_SUBSTRING_TPL,
                        SEARCH_RESULT_BY_SUBSTRINGS_TITLE_AND_DESCRIPTION_TPL,
                        SEARCH_RESULT_ELEMENT,
                        SEARCH_EMPTY_RESULT_ELEMENT,
                        BUSCKET_BUTTON,
                        YES_BUTTON;


    /*START TEMPLATES METHODS*/
    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }


    public void waitForElementByTitleAndDescription(String title, String description){
        String search_result_xpath = getResultLocatorByTwoParameters(title, description);
        this.waitForElementPresent(search_result_xpath,
                "Can't find and click search result with title [" + title + "] and description [" + description + "]",
                10);
    }

    private String getResultLocatorByTwoParameters(String title, String description){
        return SEARCH_RESULT_BY_SUBSTRINGS_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}", title).replace("{DESCRIPTION}",description);
    }

    /*END TEMPLATES METHODS*/

    private String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    public void initSearchInput(){
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT,
                "Can't find and click search init element",
                5);
        this.waitForElementPresent(SEARCH_INIT_ELEMENT,
                "Can't find search input after clicking search init element");
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(SEARCH_INPUT,
                search_line,
                "Can't find and type into search input",
                5);
    }

    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath,
                "Can't find search result with substring " + substring);
    }

    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON,
                "Can't find search cansel button",
                5);
    }

    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON,
                "Search cansel button is still present",
                5);
    }

    public void clickCancelSearch(){
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON,
                "Can't find and click search cancel button",
                5);
    }

    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath,
                "Can't find and click search result with substring " + substring,
                10);
    }

    public int getAmountOfFoundArticles(){
        this.waitForElementPresent(SEARCH_RESULT_ELEMENT,
                "Can't find nothing by the request",
                15);
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel(){
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT,
                "Can't find empty result element.",
                15);
    }

    public void assertThereIsNoResultOfSearch(){
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT,
                "We supposed not to find any results");
    }

    public List<WebElement> makeSureThatSeveralArticlesAreFound(){
        waitForElementPresent(SEARCH_RESULT_ELEMENT,
                "Web element is not present",15);
        List<WebElement> elementsList =
                driver.findElements(super.getLocatorByString(SEARCH_RESULT_ELEMENT));
        System.out.println("Find:: " + elementsList.size() + " articles");
        assertTrue("Articles less than 2",elementsList.size() > 1);
        return elementsList;
    }

    public void makesSureThatEverySearchResultHasThatWord(String word){
        List<WebElement> list =  makeSureThatSeveralArticlesAreFound();
        int negativeCount = 0;
        String actualText = "";




        for(WebElement element:list){
            if(Platform.getInstance().isAndroid()) {
                actualText = element.getAttribute("text");
            }else if(Platform.getInstance().isIOS()){
                actualText = element.getAttribute("name");
            }

            if(!actualText.toLowerCase().contains(word.toLowerCase())){
                negativeCount++;
                System.err.println("Position [" + negativeCount + "] Text [" + actualText + "] doesn't match [" + word + "]");
            }
        }
        assertTrue("Sum of negative items [" + negativeCount + "]",negativeCount == 0);
    }

    public void сheckThatSearchResultIsMissing(String word){
        try{
            waitForElementAndClick(BUSCKET_BUTTON,
                    "Can't find button 'Basket'",
                    5);
            waitForElementAndClick(YES_BUTTON,
                    "Can't find button 'YES'",
                    5);
        }catch (Exception e){
            e.getMessage();
        }
        if(Platform.getInstance().isAndroid()) {
            checksForTextInWebElement("id:org.wikipedia:id/search_src_text", "Search…");
        }else if(Platform.getInstance().isIOS()) {
            initSearchInput();
        }
            assertTrue( "List articles is not empty",getAmountOfElements(SEARCH_RESULT_BY_SUBSTRING_TPL) == 0);

    }

}
