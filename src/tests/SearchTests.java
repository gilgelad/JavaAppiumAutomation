package tests;

import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Happy on 22.01.2019.
 */
public class SearchTests extends CoreTestCase {

    @Test
    public void testHomeTask_Ex3_ToLesson_2() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_value = "Java";
        SearchPageObject.typeSearchLine(search_value);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        if (amount_of_search_results == 1) {
            assertFalse("Найден только один результат поиска",
                    amount_of_search_results == 1);
        }
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForEmptyMessageResultElement();
    }

    //TODO: Доработать нормально методы по работе с коллекцией согласно фреймворку как появится время.
    @Test
    public void testHomeTask_Ex4_ToLesson_2() {
        //Ищет какое-то слово
        //Убеждается, что в каждом результате поиска есть это слово.
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        MainPageObject MainPageObject = new MainPageObject(driver);

        List<WebElement> results_list = MainPageObject.waitForElementsPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Can not find elements for List",
                5
        );
        List <String> titles_of_result = new ArrayList<>();
        for (WebElement element:results_list){
            titles_of_result.add(element.findElement(By.id("org.wikipedia:id/page_list_item_title")).getText());
        }
        for (int i = 0; i < titles_of_result.size(); i++){
            assertTrue("There is no 'Java' in the title at line " + i,
                    titles_of_result.get(i).contains("Java")||titles_of_result.get(i).contains("java"));
        }
    }

    @Test
    public void testHomeTask_Ex6_ToLesson_3() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_text_in_wiki = "Java";
        SearchPageObject.typeSearchLine(search_text_in_wiki);
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        MainPageObject MainPageObject = new MainPageObject(driver);
        MainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "We were not able to find the element on this page."
        );
    }

    @Test
    public void testSearch() {

        SearchPageObject SerachPagePbject = new SearchPageObject(driver);

        SerachPagePbject.initSearchInput();
        SerachPagePbject.typeSearchLine("Java");
        SerachPagePbject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Diskography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found too few results.",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testEmountOfEmptySearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line = "sfsfsfsfsfsfs";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }
}
