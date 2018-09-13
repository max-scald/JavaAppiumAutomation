package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {

  static {
      SEARCH_INIT_ELEMENT = "xpath://*[contains(@text,'Search Wikipedia')]";
      SEARCH_INPUT = "xpath://*[contains(@text,'Search…')]";
      SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
      SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='{SUBSTRING}']";
      SEARCH_RESULT_BY_SUBSTRINGS_TITLE_AND_DESCRIPTION_TPL =
                      "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title' and contains (@text,'{TITLE}')]/following-sibling::*[@resource-id='org.wikipedia:id/page_list_item_description' and contains(@text,'{DESCRIPTION}')]";
      SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
      SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']";
      SEARCH_RESULT_LIST_OF_ELEMENTS = "xpath://*[contains(@resource-id,'page_list_item_title')]";
      BUSCKET_BUTTON = "id:org.wikipedia:id/recent_searches_delete_button";
      YES_BUTTON = "id:android:id/button1";
  }
    public AndroidSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
