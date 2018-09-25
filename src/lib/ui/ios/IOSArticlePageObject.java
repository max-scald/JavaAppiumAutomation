package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class IOSArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "id:Java (programming language)";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
//        PREVIOUSLY_CREATED_LIST = "xpath://*[@resource-id='org.wikipedia:id/item_title' and contains(@text,'{NAME_OF_LIST}')]";
    }

    public IOSArticlePageObject(AppiumDriver driver) {
        super(driver);
    }
}
