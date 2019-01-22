package tests;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Random;

/**
 * Created by Happy on 22.01.2019.
 */
public class MyListTests extends CoreTestCase {
    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        String id_of_folder = "org.wikipedia:id/item_container";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderById(id_of_folder);
        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testHomeTask_Ex5_ToLesson_3() {
    /*Написать тест, который:
    1. Сохраняет две статьи в одну папку
    2. Удаляет одну из статей
    3. Убеждается, что вторая осталась
    4. Переходит в неё и убеждается, что title совпадает*/

        SearchPageObject SearchPageObject  = new SearchPageObject (driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        ArticlePageObject.waitForTitleElement();
        String title_element_1 = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Home task Ex5";
        String id_of_folder = "org.wikipedia:id/item_container";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        //Добавляем вторую статью в Мои папки
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Set of several computer software products and specifications");
        ArticlePageObject.waitForTitleElement();
        String title_element_2 = ArticlePageObject.getArticleTitle();
        ArticlePageObject.addArticleToMyListIntoAlreadyExistFolder(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_folder);

        MainPageObject MainPageObject = new MainPageObject(driver);
        //Добавляю рандоммный метод выбора статьи для удаления (для разнообразия).
        Random random = new Random();
        int article_to_delete = random.nextInt(2);
        if (article_to_delete == 0) {
            MainPageObject.swipeElementToLeft(
                    By.xpath("//*[@text='" + title_element_1 + "']"),
                    "Не могу найти первую статью для удаления " + title_element_1
            );
            MainPageObject.waitForElementPresent(
                    By.xpath("//*[@text='" + title_element_2 + "']"),
                    "Заголовок второй статьи отличается от ожидаемого." + title_element_2,
                    5
            );
        } else if (article_to_delete == 1) {
            MainPageObject.swipeElementToLeft(
                    By.xpath("//*[@text='" + title_element_2 + "']"),
                    "Не могу найти вторую статью для удаления " + title_element_2
            );
            MainPageObject.waitForElementPresent(
                    By.xpath("//*[@text='" + title_element_1 + "']"),
                    "Заголовок первой статьи отличается от ожидаемого." + title_element_1,
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
}
