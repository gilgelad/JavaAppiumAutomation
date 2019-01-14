import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

/**
 * Created by Happy on 02.01.2019.
 */
public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","6.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","D:\\Java\\Study\\Automation\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can not find 'search Wikipedia' input.",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Can not find search input.",
                5
        );
        waitForElementPresent(
//                "//*[@resource-id='org.wikipedia:id/page_list_item_description']//*[@text='Object-oriented programming language']",
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Can not find 'Object-oriented programming language' topic searching by 'Java'.",
                10
        );
    }

    @Test
    public void testCancelSearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can not find 'search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Can not find search input.",
                5
        );
        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Can not find search field.",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Can not find 'X' button to cancel search.",
                5
        );
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "'X' button is still present on the page.",
                5
        );
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can not find 'search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Can not find search input.",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Can not find topic about Java 'Object-oriented programming language'",
                5
        );
        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can not find article title.",
                10
        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title.",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void homeTask_Ex2_ToLesson_2() {
        //Переходим на страницу ввода поиска, чтобы легче было проверять. По заданию этого шага не нужно.
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can not find 'search Wikipedia' input",
                5
        );

        WebElement search_field = waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Can not find search field .",
                5
        );

        String search_value = search_field.getAttribute("text");

        Assert.assertEquals(
                "We can not compare 'Search…' text in the search field before enter a search value.",
                "Search…",
                search_value
        );
    }

    @Test
    public void homeTask_Ex3_ToLesson_2() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can not find 'search Wikipedia' input",
                5
        );
        //Для получения одного результата поиска введите "dhgfdis"
        String search_value = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_value,
                "Can not find search input.",
                10
        );

        String search_result_value = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        waitForElementPresent(
                By.xpath(search_result_value),
                "Can not find any search result for '" + search_value + "'",
                15
        );
        int amount_of_search_results = getAmountOfElemets(
                By.xpath(search_result_value)
        );
        if (amount_of_search_results == 1) {
            Assert.assertFalse("Найден только один результат поиска",
                    amount_of_search_results == 1);
        }
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Не найдена кнопка X для отмены результата поиска.",
                0
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/search_empty_message"),
                "Не пропали результаты поиска после отмены.",
                10
        );
    }

    @Test
    public void testSwipeArticle() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can not find 'search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "Can not find search input.",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Не найдена указанная статья про Appium.",
                5
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can not find article title.",
                10
        );
        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Can not find the end of the article.",
                20
        );
    }

    @Test
    public void saveFirstArticleToMyList() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can not find 'search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Can not find search input.",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Can not find topic about Java 'Object-oriented programming language'",
                5
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can not find article title.",
                10
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Can not find button to open more options.",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Can not find option to add article to reading list",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Can not find 'Got it' tip overlay.",
                5
        );
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Can not find input to set name of articles folder.",
                5
        );

        String name_of_folder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Can not put text into articles folder input.",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Can not press OK button.",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Can not close article, can not click 'X' button.",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Can not find navigation button to 'My Lists'.",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/item_container"),
                "Can not find created folder.",
                5
        );
        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Can not find saved article."
        );
        waitForElementPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Can not delete saved article.",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can not find 'search Wikipedia' input",
                5
        );

        String search_line = "Linkin Park Diskography";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Can not find search input.",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";

        waitForElementPresent(
                By.xpath(search_result_locator),
                "Can not find anything by the request '" + search_line + "'",
                15
        );

        int amount_of_search_results = getAmountOfElemets(
                By.xpath(search_result_locator)
        );
        Assert.assertTrue(
                "We found too few results.",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testEmountOfEmptySearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can not find 'search Wikipedia' input",
                5
        );
        String search_line = "sfsfsfsfsfsfs";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Can not find search input.",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empry_result_lable = "//*[@text='No results found']";

        waitForElementPresent(
                By.xpath(empry_result_lable),
                "Can not find empty result lable by the request" + search_line,
                15
        );
        assertElementNotPresent(
                By.xpath(search_result_locator),
                "We found some results by request " + search_line
        );
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can not find 'search Wikipedia' input",
                5
        );
        String search_line = "Java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Can not find search input.",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Can not find topic about Java 'Object-oriented programming language' by search "+search_line,
                15
        );

        String title_before_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Can not find title of article.",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Can not find title of article.",
                15
        );
        Assert.assertEquals(
                "Article title has been changed after screen rotation.",
                title_before_rotation,
                title_after_rotation
        );
        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_second_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Can not find title of article.",
                15
        );
        Assert.assertEquals(
                "Article title has been changed after screen rotation.",
                title_before_rotation,
                title_after_second_rotation
        );
    }

    @Test
    public void testSearchArticleInBackground() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can not find 'search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Can not find search input.",
                5
        );
        waitForElementPresent(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Can not find topic about Java 'Object-oriented programming language'",
                5
        );

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Can not find topic about Java 'Object-oriented programming language' after returning from background.",
                5
        );
    }



    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    private int getAmountOfElemets(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElemets(by);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present.";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int)(size.height * 0.8);
        int end_y = (int)(size.height * 0.2);

        action
                .press(x,start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes) {
//        driver.findElements(by);
//        driver.findElements(by).size();
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresent(by, "Can not find element by swiping up. \n" + error_message,0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;

        }
    }

    protected void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10
        );
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(150)
                .moveTo(left_x, middle_y)
                .release().perform();
    }






    //    @Test
//    public void homeTask_Ex3_ToLesson_4() throws InterruptedException {
//        waitForElementAndClick(
//                By.id("org.wikipedia:id/search_container"),
//                "Can not find 'search Wikipedia' input",
//                5
//        );
//        //Для получения одного результата поиска введите "dhgfdis"
//        String search_value = "Java";
//        waitForElementAndSendKeys(
//                By.xpath("//*[contains(@text,'Search…')]"),
//                search_value,
//                "Can not find search input.",
//                10
//        );
//
//        String search_result_value = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
//
//        waitForElementPresent(
//                By.xpath(search_result_value),
//                "Can not find any search result for '" + search_value + "'",
//                15
//        );
//
//        int iter_count = getAmountOfElemets(By.xpath(search_result_value));
//
//        //System.out.println(iter_count);
//
//        for (int i = 0; i < iter_count; i++) {
//        //Получаем значение поля text для топика у конткйнера [i]
//            String search_topic_text_criteria = "" +
//                    "//*[@resource-id='org.wikipedia:id/search_results_list']" +
//                    "/[@resource-id='org.wikipedia:id/page_list_item_container']" +
//                    "/[@resource-id='org.wikipedia:id/page_list_item_title']";
//
//            //Получаем значение поля text для описания к топику у конткйнера [i]
//            String search_description_text_criteria = "" +
//                "//*[@resource-id='org.wikipedia:id/search_results_list']" +
//                "/*[@resource-id='org.wikipedia:id/page_list_item_container'][i]" +
//                "/*[@resource-id='org.wikipedia:id/page_list_item_description']";
//
//            WebElement topic_text = waitForElementPresent(
//                By.xpath(search_topic_text_criteria),
//                    "Не найденно результатов поиска топика в контейнере '" + i + "'",
//                    5
//                );
//            WebElement description_text = waitForElementPresent(
//                By.xpath(search_description_text_criteria),
//                "Не найденно результатов поиска описания топика в контейнере '" + i + "'",
//                5
//            );
//
//            String topic_text_value = topic_text.getAttribute("text");
//            String description_text_value = description_text.getAttribute("text");
//
//            if (topic_text_value.toLowerCase().indexOf(search_value.toLowerCase()) == -1) {
//                Assert.assertEquals(
//                        "Не найдено совпадений значения поиска '" + search_value + "' в названии топика '" + topic_text_value + "'",
//                        search_value,
//                        topic_text_value
//                );
//            } else if (description_text_value.toLowerCase().indexOf(search_value.toLowerCase()) == -1) {
//                Assert.assertEquals(
//                        "Не найдено совпадений значения поиска '" + search_value + "' в названии описания '" + description_text_value + "'",
//                        search_value,
//                        description_text_value
//                );
//            }
//        }
//    }

}

