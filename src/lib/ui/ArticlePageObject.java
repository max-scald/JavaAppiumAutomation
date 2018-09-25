package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
    TITLE,
    FOOTER_ELEMENT,
    OPTIONS_BUTON,
    OPTIONS_ADD_TO_MY_LIST_BUTTON,
    ADD_TO_MY_LIST_OVERLAY,
    MY_LIST_NAME_INPUT,
    MY_LIST_OK_BUTTON,
    PREVIOUSLY_CREATED_LIST,
    CLOSE_ARTICLE_BUTTON;

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    private static String getListXpathByName(String name_of_list){
        return PREVIOUSLY_CREATED_LIST.replace("{NAME_OF_LIST}", name_of_list);
    }

    public WebElement waitForTitleElement(){

        return this.waitForElementPresent(TITLE,
                "Can't find article title on page!",
                15);
    }

    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        if(Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        }else{
            return title_element.getAttribute("name");
        }
    }

    public void swipeToFooter(){
        if(Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(FOOTER_ELEMENT,
                    "Can't find the end of article",
                    40);
        }else {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT,
                    "Can't find the end of article",
                    40);
        }
    }

    public void closeArticle(){
        if(Platform.getInstance().isAndroid()){
            driver.navigate().back();
        }else {
            try{
                this.waitForElementAndClick("id:places auth close",
                        "Can't find and click 'X' on PopUp window",
                        5);
            }catch (Exception e){

            }
            this.waitForElementAndClick(CLOSE_ARTICLE_BUTTON,
                    "Can't find and click 'Back' button",
                    5);
        }
    }

    public void addArticleToMyList(String name_of_list){
        this.waitForElementAndClick(OPTIONS_BUTON,
                "Can't find button to open article options",
                5);

        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Can't find option to add article to reading list",
                5);

        try {

            this.waitForElementAndClick(ADD_TO_MY_LIST_OVERLAY,
                    "Can't find 'Got it' tip overlay",
                    5);

            this.waitForElementAndClear(MY_LIST_NAME_INPUT,
                    "Can't find input to set name of articles folder",
                    5);

            this.waitForElementAndSendKeys(MY_LIST_NAME_INPUT,
                    name_of_list,
                    "Can't put text into articles folder input",
                    5);

            this.waitForElementAndClick(MY_LIST_OK_BUTTON,
                    "Can't press OK button",
                    5);

        }catch (TimeoutException te){

            String article_xpath = getListXpathByName(name_of_list);
            this.waitForElementAndClick(article_xpath,
                    "Can't find an earlier created folder::" + name_of_list,
                    5);

        }
    }

    public void addArticleToMySaved(){
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Can't find option to add article to reading list",
                5);
    }
}
