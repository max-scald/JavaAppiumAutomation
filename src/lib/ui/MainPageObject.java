package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageObject {
    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver){
        this.driver = driver;
    }

    public WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by, error_message, 5);
    }

    public WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return  element;
    }

    public boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void checksForTextInWebElement(By by, String text){
        WebElement element = waitForElementPresent(by,"Web element is not present",5);
        Assert.assertEquals(
                "Error! text [" + text + "] is not present.",
                String.valueOf(element.getAttribute("text")),
                text
        );
    }

    public List<WebElement> makeSureThatSeveralArticlesAreFound(){
        waitForElementPresent(By.xpath("//*[contains(@resource-id,'page_list_item_title')]"),
                "Web element is not present",15);
        List<WebElement> elementsList =
                driver.findElements(By.xpath("//*[contains(@resource-id,'page_list_item_title')]"));
        System.out.println("Find:: " + elementsList.size() + " articles");
        Assert.assertTrue("Articles less than 2",elementsList.size() > 1);

        return elementsList;
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

        List<WebElement> elementsList = driver.findElements(By.xpath("//*[contains(@text,'" + word + "')]"));
        Assert.assertTrue("List articles is not empty",elementsList.isEmpty());
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

    public void swipeUpToFindElement(By by, String error_message, int max_swipes){
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

    public void swipeElementToLeft(By by, String error_message){
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

    private Boolean waitUntilElementDisappears(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public int getAmountOfElements(By by){
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(By by, String error_message){
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0){
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public String waitForElementAndAttribute(By by, String attribute, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    public void addArticleToMyList(String name_of_folder){
        waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Can't find button to open article options",
                5);

        waitForElementAndClick(By.xpath("//*[@text='Add to reading list']"),
                "Can't find option to add article to reading list",
                5);

        try {

            waitForElementAndClick(By.id("org.wikipedia:id/onboarding_button"),
                    "Can't find 'Got it' tip overlay",
                    5);

            waitForElementAndClear(By.id("org.wikipedia:id/text_input"),
                    "Can't find input to set name of articles folder",
                    5);

            waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"),
                    name_of_folder,
                    "Can't put text into articles folder input",
                    5);

            waitForElementAndClick(By.xpath("//*[@text='OK']"),
                    "Can't press OK button",
                    5);

        }catch (TimeoutException te){

            waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/item_title' and contains(@text,'" + name_of_folder + "')]"),
                    "Can't find an earlier created folder::" + name_of_folder,
                    5);
        }
    }

    public void assertElementPresent(By by, String error_message){
        if (getAmountOfElements(by) == 0){
            String default_message = "An element '" + by.toString() + "' supposed to be present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }
}
