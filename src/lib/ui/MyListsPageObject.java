package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import sun.applet.Main;

/**
 * Created by Happy on 21.01.2019.
 */
public class MyListsPageObject extends MainPageObject {

    private static final String
        FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
        ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']",
        FOLDER_BYID_TPL = "{FOLDER_ID}";

    private static String getFolderByXpathByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    private static String getSavedArticleXpathByID(String folder_id) {
        return FOLDER_BYID_TPL.replace("{FOLDER_ID}", folder_id);
    }

    public MyListsPageObject (AppiumDriver driver) {
        super(driver);
    }

//    public void openFolderByName(String name_of_folder) {
//        String folder_name_xpath = getFolderByXpathByName(name_of_folder);
//        this.waitForElementAndClick(
////                By.id("org.wikipedia:id/item_container"),
//                By.xpath(folder_name_xpath),
//                "Can not find folder by Name " + name_of_folder,
//                5
//        );

    public void openFolderById(String id_of_folder) {
        String folder_id_xpath = getSavedArticleXpathByID(id_of_folder);
        this.waitForElementAndClick(
//                By.id("org.wikipedia:id/item_container"),
                By.id(folder_id_xpath),
                "Can not find folder by Name " + id_of_folder,
                5
        );
    }


    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(By.xpath(article_xpath), "Can not find saved article by title " + article_title, 15);
    }

    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(By.xpath(article_xpath), "Saved article still present with title " + article_title, 15);
    }

    public void swipeByArticleToDelete(String article_title) {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.swipeElementToLeft(
                By.xpath(article_xpath),
                "Can not find saved article."
        );
        this.waitForArticleToDisappearByTitle(article_title);
    }
}
