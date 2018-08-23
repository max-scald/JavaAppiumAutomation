import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;
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

        checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"Search…");

        waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java","Can't find search input",
                5);

        waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Can't find 'Object-oriented programming language' topic searching by 'Java'",
                15);
    }

    @Test
    public void testCancelSearch(){
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Can't find 'Search Wikipedia' input",
                5);

        checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"Search…");

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

        checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"Search…");

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

        Assert.assertEquals("We see unexpected title!",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testCencelResultOfsearchSeveralArticles(){
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find search Wikipedia input",
                5);

        checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"Search…");

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

        checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"Search…");


        waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Friendship","Can't find search input",
                5);

        makesSureThatEverySearchResultHasThatWord("Friendship");

    }

    @Test
    public void testSwipeArticle(){
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find search Wikipedia input",
                5);

        waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Appium","Can't find search input",
                5);

        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Can't find 'Appium' article in search",
                5);

        waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
                "Can't find article title",
                15);

        swipeUpToFindElement(By.xpath("//*[@text='View page in browser']"),
                "Can't find the end of the article",
                20);
    }

    @Test
    public void saveFirstArticleToMyList(){
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find search Wikipedia input",
                5);

        waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java","Can't find search input",
                5);

        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Can't find search Wikipedia input",
                5);

        waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
                "Can't find article title",
                15);

        waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Can't find button to open article options",
                5);

        waitForElementAndClick(By.xpath("//*[@text='Add to reading list']"),
                "Can't find option to add article to reading list",
                5);

        waitForElementAndClick(By.id("org.wikipedia:id/onboarding_button"),
                "Can't find 'Got it' tip overlay",
                5);

        waitForElementAndClear(By.id("org.wikipedia:id/text_input"),
                "Can't find input to set name of articles folder",
                5);

        String name_of_folder = "Learning programming";

        waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Can't put text into articles folder input",
                5);

        waitForElementAndClick(By.xpath("//*[@text='OK']"),
                "Can't press OK button",
                5);

        waitForElementAndClick(By.xpath("//*[@class='android.widget.ImageButton']"),
                "Can't close article, can't find X link",
                5);

        waitForElementAndClick(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Can't find navigation button to My lists",
                5);

        waitForElementAndClick(By.xpath("//*[@text='" + name_of_folder + "']"),
                "Can't find creating folder",
                5);

        swipeElementToLeft(By.xpath("//*[@text='Java (programming language)']"),
                "Can't find saved article");

        waitForElementNotPresent(By.xpath("//*[@text='Java (programming language)']"),
                "Can't delete saved article",
                5);

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
        return element;
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

    private void checksForTextInWebElement (By by, String text){
        WebElement element = waitForElementPresent(by,"Web element is not present",5);
        Assert.assertEquals(
                "Error! text [" + text + "] is not present.",
                String.valueOf(element.getAttribute("text")),
                text
        );
    }

    private List<WebElement> makeSureThatSeveralArticlesAreFound(){
        waitForElementPresent(By.xpath("//*[contains(@resource-id,'page_list_item_title')]"),
                "Web element is not present",15);
        List<WebElement> elementsList =
                driver.findElements(By.xpath("//*[contains(@resource-id,'page_list_item_title')]"));
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
        checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"Search…");

        List<WebElement> elementsList = driver.findElements(By.xpath("//*[contains(@text,'" + word + "')]"));
        Assert.assertTrue("List articles is not empty",elementsList.isEmpty());

    }

    private void makesSureThatEverySearchResultHasThatWord(String word){
        List<WebElement> list =  makeSureThatSeveralArticlesAreFound();

        int negativeCount = 0;
        for(WebElement element:list){
            if(!element.getAttribute("text").toLowerCase().contains(word.toLowerCase())){
                negativeCount++;
                System.err.println("Position [" + negativeCount + "] Text [" + element.getAttribute("text") + "] doesn't match [" + word + "]");
            }
        }
        Assert.assertTrue("Sum of negative items [" + negativeCount + "]",negativeCount == 0);
    }

    protected  void swipeUp(int timeOfSwipe){
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action.press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick(){
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes){
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0){
            if(already_swiped > max_swipes){
                waitForElementPresent(by, "Can't find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    protected void swipeElementToLeft(By by,String error_message){
        WebElement element = waitForElementPresent(by, error_message, 10);
        int left_x = element.getLocation().getX();
        int right_x = left_x +element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x,middle_y)
                .waitAction(300)
                .moveTo(left_x,middle_y)
                .release()
                .perform();

    }

}
