import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception{
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","C:\\project\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void firstTest(){
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find search Wikipedia input",
                5);

        checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"text","Search…");


        waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java","Can't find search input",
                5);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Can't find 'Object-oriented programming language' topic searching by 'Java'",
                15);
    }

    @Test
    public void testCancelSearch(){
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Can't find 'Search Wikipedia' input",
                5);

        checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"text","Search…");

        waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java","Can't find search input",
                5);

        waitForElementAndClear(By.id("org.wikipedia:id/search_src_text"),
                "Can't find search field",
                5);

        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
                "Can't find X to cancel search",
                5);


        waitForElementNotPresent(By.id("org.wikipedia:id/search_close_btn"),
                "X is slill present on the page",
                5);
    }

    @Test
    public void testCompareArticleTitle(){
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find search Wikipedia input",
                5);

        checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"text","Search…");

        waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java","Can't find search input",
                5);

        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Can't find search Wikipedia input",
                5);

       WebElement title_element = waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
                "Can't find article title",
                15);

       String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title!",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testCencelResultOfsearchSeveralArticles(){
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find search Wikipedia input",
                5);

        checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"text","Search…");


        waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Selenium","Can't find search input",
                5);

        makeSureThatSeveralArticlesAreFound();

        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
                "Can't find X to cancel search",
                5);

        сheckThatSearchResultIsMissing("Selenium");

    }

    @Test
    public void checkWordsInSearchInEachResult(){

        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find search Wikipedia input",
                5);

        checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"text","Search…");


        waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Friendship","Can't find search input",
                5);

        makesSureThatEverySearchResultHasThatWord("Friendship");

    }



    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return  element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return  element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    private void checksForTextInWebElement (By by, String typeAtribute, String text){
        WebElement element = waitForElementPresent(by,"Web element is not present",5);
        Assert.assertEquals(
                "Error! text [" + text + "] is not present.",
                String.valueOf(element.getAttribute(typeAtribute)),
                text
        );
    }

    private List<WebElement> makeSureThatSeveralArticlesAreFound(){
        waitForElementPresent(By.xpath("//*[contains(@resource-id,'page_list_item_title')]"),
                "Web element is not present",15);
        List<WebElement> elementsList = driver.findElements(By.xpath("//*[contains(@resource-id,'page_list_item_title')]"));
        System.out.println("Find:: " + elementsList.size() + " articles");
        Assert.assertTrue("Articles less than 2",elementsList.size() > 1);

        return elementsList;
    }

    private void сheckThatSearchResultIsMissing(String word){
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
        checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"text","Search…");

        List<WebElement> elementsList = driver.findElements(By.xpath("//*[contains(@text,'" + word + "')]"));
        Assert.assertTrue("List articles is not empty",elementsList.isEmpty());

    }

    private void makesSureThatEverySearchResultHasThatWord(String word){
        List<WebElement> list = makeSureThatSeveralArticlesAreFound();
        for (WebElement element:list){
            System.out.println("Find::" + element.getAttribute("text"));
            Assert.assertTrue("There is no such word in this element",element.getAttribute("text").toUpperCase().contains(word.toUpperCase()));
        }
    }
}
