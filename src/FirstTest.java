
import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;


public class FirstTest extends CoreTestCase {
    private MainPageObject mainPageObject;

    protected void setUp() throws Exception{
        super.setUp();
        mainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearch(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testCompareArticleTitle(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        String article_title = articlePageObject.getArticleTitle();

        Assert.assertEquals("We see unexpected title!",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testCencelResultOfsearchSeveralArticles(){
        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find search Wikipedia input",
                5);

        mainPageObject.checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"Search…");

        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Selenium","Can't find search input",
                5);

        mainPageObject.makeSureThatSeveralArticlesAreFound();

        mainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
                "Can't find X to cancel search",
                5);

        mainPageObject.сheckThatSearchResultIsMissing("Selenium");

    }

    @Test
    public void testCheckWordsInSearchInEachResult(){

        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find search Wikipedia input",
                5);

        mainPageObject.checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"Search…");


        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Friendship","Can't find search input",
                5);

        mainPageObject.makesSureThatEverySearchResultHasThatWord("Friendship");

    }

    @Test
    public void testSwipeArticle(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        articlePageObject.swipeToFooter();
    }

    @Test
    public void testSaveFirstArticleToMyList(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();

        String article_title = articlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";

        articlePageObject.addArticleToMyList(name_of_folder);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickToMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openFolderByName(name_of_folder);
        myListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testAmountOfNotEmptySearch(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();

        String search_line = "Linkin Park Diskography";

        searchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = searchPageObject.getAmountOfFoundArticles();

        Assert.assertTrue("We found too few results!",
                amount_of_search_results > 0);
    }

    @Test
    public void testAmountOfEmptySearch(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();

        String search_line = "zxcvasdfqwer";

        searchPageObject.typeSearchLine(search_line);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
    }


    @Test
    public void testChangeScreenOrientationOnSearchResaults(){

        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find search Wikipedia input",
                5);

        String search_line = "Java";

        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Can't find search input",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Can't find 'Object-oriented programming language' topic searching by " + search_line,
                15);

        String title_before_rotation = mainPageObject.waitForElementAndAttribute(By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Can't find title of article",
                15);

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = mainPageObject.waitForElementAndAttribute(By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Can't find title of article",
                15);

         Assert.assertEquals("Article title have been changed after screen rotation",
                 title_before_rotation,
                 title_after_rotation);

        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_second_rotation = mainPageObject.waitForElementAndAttribute(By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Can't find title of article",
                15);

        Assert.assertEquals("Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation);
    }

    @Test
    public void testCheckSearchArticleInBackground(){
        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find search Wikipedia input",
                5);

        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java","Can't find search input",
                5);

        mainPageObject.waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Can't find 'Search Wikipedia' input",
                5);

        driver.runAppInBackground(2);

        mainPageObject.waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Can't find article after returning from background",
                5);
    }

    @Test
    public void testOnSavingTwoArticles(){
        String search_word = "Java";
        String title_first_article = "Java (programming language)";
        String title_second_article = "Java (software platform)";
        String name_of_folder = "Learning programming";

        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find search Wikipedia input",
                5);

        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                search_word,"Can't find search input",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Can't find search Wikipedia input",
                5);

        mainPageObject.waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text' and contains(@text,'" + title_first_article + "')]"),
                "Can't find article title::" + title_first_article,
                15);

        mainPageObject.addArticleToMyList(name_of_folder);

        driver.navigate().back();

        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find search Wikipedia input in second time",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'" + search_word + "')]"),
                "Can't find search '" + search_word + "' in 'Resent searches:'",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[contains(@text,'Set of several computer')]"),
                "Can't find search Wikipedia input",
                5);

        mainPageObject.waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text' and contains(@text,'" + title_second_article + "')]"),
                "Can't find article title::" + title_second_article,
                15);

        mainPageObject.addArticleToMyList(name_of_folder);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text='VIEW LIST']"),
                "Can't find Button 'VIEW LIST'",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text='" + name_of_folder + "']"),
                "Can't find creating folder:: " + name_of_folder,
                5);

        mainPageObject.swipeElementToLeft(By.xpath("//*[@text='" + title_first_article + "']"),
                "Can't find saved article:: " + title_first_article);

        mainPageObject.waitForElementNotPresent(By.xpath("//*[@text='" + title_first_article + "']"),
                "Can't delete saved article:: " + title_first_article,
                5);

        mainPageObject.waitForElementPresent(By.xpath("//*[@text='" + title_second_article + "']"),
                "Can't find saved article:: " + title_second_article,
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text='" + title_second_article + "']"),
                "Can't click on article:: " + title_second_article,
                5);

        mainPageObject.waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text' and contains(@text,'" + title_second_article + "')]"),
                "Can't find article title::" + title_second_article,
                15);
    }

    @Test
    public void testAssertTitle(){
        String search_word = "Java";
        String title_article = "Java (software platform)";

        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find search Wikipedia input",
                5);

        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                search_word,
                "Can't find search input",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[contains(@text,'Set of several computer')]"),
                "Can't find search Wikipedia input",
                5);

        mainPageObject.assertElementPresent(By.id("org.wikipedia:id/view_page_title_text"),"Can't find title of article.");
    }

}
