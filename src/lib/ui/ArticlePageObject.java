package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
    TITLE = "org.wikipedia:id/view_page_title_text",
    FOOTER_ELEMENT = "//*[@text='View page in browser']",
    OPTIONS_BUTON = "//android.widget.ImageView[@content-desc='More options']",
    OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
    ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
    MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
    MY_LIST_OK_BUTTON = "//*[@text='OK']",
    PREVIOUSLY_CREATED_LIST = "//*[@resource-id='org.wikipedia:id/item_title' and contains(@text,'{NAME_OF_LIST}')]";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    private static String getListXpathByName(String name_of_list){
        return PREVIOUSLY_CREATED_LIST.replace("{NAME_OF_LIST}", name_of_list);
    }

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(By.id(TITLE),
                "Can't find article title on page!",
                15);
    }

    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter(){
        this.swipeUpToFindElement(By.xpath(FOOTER_ELEMENT),
                "Can't find the end of article",
                20);
    }

    public void closeArticle(){
        driver.navigate().back();
    }

    public void addArticleToMyList(String name_of_list){
        this.waitForElementAndClick(By.xpath(OPTIONS_BUTON),
                "Can't find button to open article options",
                5);

        this.waitForElementAndClick(By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Can't find option to add article to reading list",
                5);

        try {

            this.waitForElementAndClick(By.id(ADD_TO_MY_LIST_OVERLAY),
                    "Can't find 'Got it' tip overlay",
                    5);

            this.waitForElementAndClear(By.id(MY_LIST_NAME_INPUT),
                    "Can't find input to set name of articles folder",
                    5);

            this.waitForElementAndSendKeys(By.id(MY_LIST_NAME_INPUT),
                    name_of_list,
                    "Can't put text into articles folder input",
                    5);

            this.waitForElementAndClick(By.xpath(MY_LIST_OK_BUTTON),
                    "Can't press OK button",
                    5);

        }catch (TimeoutException te){

            String article_xpath = getListXpathByName(name_of_list);
            this.waitForElementAndClick(By.xpath(article_xpath),
                    "Can't find an earlier created folder::" + name_of_list,
                    5);

        }
    }
}
