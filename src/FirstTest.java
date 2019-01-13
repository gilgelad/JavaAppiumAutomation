import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
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
                "Не найдено ни одного результата по значению поиска '" + search_value + "'",
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
}

