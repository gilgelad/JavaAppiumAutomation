import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Happy on 02.01.2019.
 */
public class FirstTest extends CoreTestCase {

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



    @Test
    public void testHomeTask_Ex2_ToLesson_2() {
        //Переходим на страницу ввода поиска, чтобы легче было проверять. По заданию этого шага не нужно.
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can not find 'search Wikipedia' input",
                5
        );

        WebElement search_field = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Can not find search field .",
                5
        );

        String search_value = search_field.getAttribute("text");

        assertEquals(
                "We can not compare 'Search…' text in the search field before enter a search value.",
                "Search…",
                search_value
        );
    }

    @Test
    public void testHomeTask_Ex3_ToLesson_2() {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can not find 'search Wikipedia' input",
                5
        );
        //Для получения одного результата поиска введите "dhgfdis"
        String search_value = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_value,
                "Can not find search input.",
                10
        );

        String search_result_value = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        MainPageObject.waitForElementPresent(
                By.xpath(search_result_value),
                "Can not find any search result for '" + search_value + "'",
                15
        );
        int amount_of_search_results = MainPageObject.getAmountOfElemets(
                By.xpath(search_result_value)
        );
        if (amount_of_search_results == 1) {
            assertFalse("Найден только один результат поиска",
                    amount_of_search_results == 1);
        }
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Не найдена кнопка X для отмены результата поиска.",
                0
        );
        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_empty_message"),
                "Не пропали результаты поиска после отмены.",
                10
        );
    }

    @Test
    public void testHomeTask_Ex4_ToLesson_2() {
    //Ищет какое-то слово
    //Убеждается, что в каждом результате поиска есть это слово.
        MainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        MainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "cannot find search input",
                5
        );

        List <WebElement> results_list = MainPageObject.waitForElementsPresent(
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
    public void testHomeTask_Ex5_ToLesson_3() {
    /*Написать тест, который:
    1. Сохраняет две статьи в одну папку
    2. Удаляет одну из статей
    3. Убеждается, что вторая осталась
    4. Переходит в неё и убеждается, что title совпадает*/

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can not find 'search Wikipedia' input",
                5
        );
        String search_text_in_wiki = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_text_in_wiki,
                "Can not find search input.",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Can not find topic about Java 'Object-oriented programming language'",
                5
        );
        //Сохраняем данные статьи для получения значения поля title
        WebElement title_element_1 = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can not find article title.",
                15
        );

        String first_article_title = title_element_1.getAttribute("text");

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Can not find button to open more options.",
                5
        );
        MainPageObject.waitForElementAndClick(
                //By.xpath("//*[@text='Add to reading list']"),
                By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView"),
                "Can not find option to add article to reading list",
                7
        );
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Can not find 'Got it' tip overlay.",
                5
        );
        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Can not find input to set name of articles folder.",
                5
        );

        String name_of_folder = "Home task Ex5";

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Can not put text into articles folder input.",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Can not press OK button.",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Can not close article, can not click 'X' button.",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can not find 'search Wikipedia' input",
                5
        );
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_text_in_wiki,
                "Can not find search input.",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Set of several computer software products and specifications']"),
                "Can not find topic about Java 'Object-oriented programming language'",
                5
        );
        //Сохраняем данные статьи для получения значения поля title
        WebElement title_element_2 = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can not find article title.",
                15
        );

        String second_article_title = title_element_2.getAttribute("text");

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Can not find button to open more options.",
                5
        );
        MainPageObject.waitForElementAndClick(
//                By.xpath("//*[@text='Add to reading list']"),
                By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView"),
                "Can not find option to add article to reading list",
                7
        );
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/item_container"),
                "Can not find created folder.",
                15
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Can not close article, can not click 'X' button.",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Can not find navigation button to 'My Lists'.",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/item_container"),
                "Can not find created folder.",
                5
        );

        //Добавляю рандоммный метод выбора статьи для удаления (для разнообразия).
        Random random = new Random();
        int article_to_delete = random.nextInt(2);
        if (article_to_delete == 0) {
            MainPageObject.swipeElementToLeft(
                    By.xpath("//*[@text='" + first_article_title + "']"),
                    "Не могу найти первую статью для удаления " + first_article_title
            );
            MainPageObject.waitForElementPresent(
                    By.xpath("//*[@text='" + second_article_title + "']"),
                    "Заголовок второй статьи отличается от ожидаемого." + second_article_title,
                    5
            );
        } else if (article_to_delete == 1) {
            MainPageObject.swipeElementToLeft(
                    By.xpath("//*[@text='" + second_article_title + "']"),
                    "Не могу найти вторую статью для удаления " + second_article_title
            );
            MainPageObject.waitForElementPresent(
                    By.xpath("//*[@text='" + first_article_title + "']"),
                    "Заголовок первой статьи отличается от ожидаемого." + first_article_title,
                    5
            );
        }
        int final_count_of_articles = MainPageObject.getAmountOfElemets(By.id("org.wikipedia:id/item_container"));
        assertEquals(
                "Статья не была удалена.",
                1,
                final_count_of_articles
        );
    }

    @Test
    public void testHomeTask_Ex6_ToLesson_3() {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can not find 'search Wikipedia' input",
                5
        );
        String search_text_in_wiki = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_text_in_wiki,
                "Can not find search input.",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Can not find topic about Java 'Object-oriented programming language'",
                5
        );
        MainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "We were not able to find the element on this page."
        );
    }
}

