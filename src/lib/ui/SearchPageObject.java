package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import static junit.framework.TestCase.assertTrue;

public class SearchPageObject extends MainPageObject{
    private static final String
    SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
    SEARCH_INPUT = "//*[contains(@text,'Search…')]",
    SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
    SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='{SUBSTRING}']",
    SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
    SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']",
    SEARCH_RESULT_LIST_OF_ELEMENTS = "//*[contains(@resource-id,'page_list_item_title')]";


    /*START TEMPLATES METHODS*/
    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }
    /*END TEMPLATES METHODS*/

    private String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    public void initSearchInput(){
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT),
                "Can't find and click search init element",
                5);
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT),
                "Can't find search input after clicking search init element");
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT),
                search_line,
                "Can't find and type into search input",
                5);
    }

    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath),
                "Can't find search result with substring " + substring);
    }

    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON),
                "Can't find search cansel button",
                5);
    }

    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON),
                "Search cansel button is still present",
                5);
    }

    public void clickCancelSearch(){
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON),
                "Can't find and click search cancel button",
                5);
    }

    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath),
                "Can't find and click search result with substring " + substring,
                10);
    }

    public int getAmountOfFoundArticles(){
        this.waitForElementPresent(By.xpath(SEARCH_RESULT_ELEMENT),
                "Can't find nothing by the request",
                15);
        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultsLabel(){
        this.waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
                "Can't find empty result element.",
                15);
    }

    public void assertThereIsNoResultOfSearch(){
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT),
                "We supposed not to find any results");
    }

    public List<WebElement> makeSureThatSeveralArticlesAreFound(){
        waitForElementPresent(By.xpath(SEARCH_RESULT_LIST_OF_ELEMENTS),
                "Web element is not present",15);
        List<WebElement> elementsList =
                driver.findElements(By.xpath(SEARCH_RESULT_LIST_OF_ELEMENTS));
        System.out.println("Find:: " + elementsList.size() + " articles");
        assertTrue("Articles less than 2",elementsList.size() > 1);
        return elementsList;
    }

    public void makesSureThatEverySearchResultHasThatWord(String word){
        List<WebElement> list =  makeSureThatSeveralArticlesAreFound();
        int negativeCount = 0;
        for(WebElement element:list){
            if(!element.getAttribute("text").toLowerCase().contains(word.toLowerCase())){
                negativeCount++;
                System.err.println("Position [" + negativeCount + "] Text [" + element.getAttribute("text") + "] doesn't match [" + word + "]");
            }
        }
        assertTrue("Sum of negative items [" + negativeCount + "]",negativeCount == 0);
    }

    public void сheckThatSearchResultIsMissing(String word){
        try{
            waitForElementAndClick(By.id("org.wikipedia:id/recent_searches_delete_button"),
                    "Can't find button 'Basket'",
                    5);
            waitForElementAndClick(By.id("android:id/button1"),
                    "Can't find button 'YES'",
                    5);
        }catch (Exception e){
            e.getMessage();
        }
        checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"Search…");
        assertTrue("List articles is not empty",this.getAmountOfElements(By.xpath("//*[contains(@text,'" + word + "')]")) == 0);
    }

}
