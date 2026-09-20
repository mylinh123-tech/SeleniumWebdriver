package com.linh.keywords;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class WebUI {
    private static WebDriver driver;
    public WebUI (WebDriver driver)
    {
        WebUI.driver = driver;
    }
    private static int TIMEOUT = 10;
    private static double STEP_TIME = 0.5;
    private static int PAGE_LOAD_TIMEOUT = 20;
    //Wait for Element

    public static void waitForElementVisible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable error) {
            logConsole("Timeout waiting for the element Visible. " + by.toString());
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
    }

    public static void waitForElementVisible(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable error) {
            logConsole("Timeout waiting for the element Visible. " + by.toString());
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
    }

    public static void waitForElementPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            logConsole("Element not exist. " + by.toString());
            Assert.fail("Element not exist. " + by.toString());
        }
    }

    public static void waitForElementPresent(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            logConsole("Element not exist. " + by.toString());
            Assert.fail("Element not exist. " + by.toString());
        }
    }

    public static void waitForElementClickable(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Throwable error) {
            logConsole("Timeout waiting for the element ready to click. " + by.toString());
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
        }
    }

    public static void waitForElementClickable(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Throwable error) {
            logConsole("Timeout waiting for the element ready to click. " + by.toString());
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
        }
    }
    //Chờ đợi trang load xong mới thao tác
    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(PAGE_LOAD_TIMEOUT), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //Wait for Javascript to load
        ExpectedCondition < Boolean > jsLoad = new ExpectedCondition< Boolean >() {
            @Override
            public Boolean apply(WebDriver driver) {
                return js.executeScript("return document.readyState").toString().equals("complete");
            }
        };

        //Check JS is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

        //Wait Javascript until it is Ready!
        if (!jsReady) {
            //System.out.println("Javascript is NOT Ready.");
            //Wait for Javascript to load
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                error.printStackTrace();
                Assert.fail("FAILED. Timeout waiting for page load.");
            }
        }

        //document.readyState = complete chỉ nói HTML đã tải xong,
        //các request ajax chạy sau đó vẫn có thể đang vẽ lại giao diện nên phải chờ tiếp.
        waitForJQueryLoad();
        waitForAngularLoad();
    }

    /**
     * Chờ jQuery chạy xong toàn bộ request ajax đang treo (jQuery.active == 0).
     * Cần thiết với các thành phần tự vẽ lại DOM như Datatable, nếu không
     * các element tìm được trước đó sẽ bị stale khi bảng vẽ lại.
     */
    public static void waitForJQueryLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        try {
            wait.until(_driver -> (Boolean)((JavascriptExecutor) driver)
                    .executeScript(
                            "return window.jQuery == undefined || jQuery.active == 0"));
        } catch (Exception ignored) {}
    }

    public static void waitForAngularLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        try {
            wait.until(_driver -> (Boolean)((JavascriptExecutor) driver)
                    .executeScript(
                            "return window.getAllAngularTestabilities ? " +
                                    "window.getAllAngularTestabilities()" +
                                    ".every(x=>x.isStable()) : true"));
        } catch (Exception ignored) {}
    }

    public static void waitForCurrentURLContains (String url){
        logConsole("Current URL: "+ driver.getCurrentUrl());
        logConsole(" Wait for current url contains:"+ url);
        WebDriverWait wait  = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
        wait.until(ExpectedConditions.urlContains(url));
    }

    public static void sleep(double second) {
        try {
            Thread.sleep((long)(1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void logConsole(Object message) {
        System.out.println(message);
    }

    public static WebElement getWebElement(By by) {
        return driver.findElement(by);
    }

    public static List< WebElement > getWebElements(By by) {
        return driver.findElements(by);
    }

    /**
     * Verify if a web element is present (findElements.size > 0).
     *
     * @param by Represent a web element as the By object
     * @return true/false
     */
    public static boolean checkElementExist(By by) {
        boolean result = false;

        List < WebElement > elementList = getWebElements(by);
        if (elementList.size() > 0) {
            System.out.println("✅ Element " + by + " existing.");
            result = true;
        } else {
            System.out.println("❌ Element " + by + " NOT exists.");
            result = false;
        }
        return result;
    }

    // Hàm kiểm tra sự tồn tại của phần tử với lặp lại nhiều lần dùng FluentWait
    public static boolean checkElementExist(By by, int maxRetries, int waitTimeMillis) {
        System.out.println("Kiểm tra tồn tại phần tử với retry: " + by);

        long totalTimeoutMillis = (long) maxRetries * waitTimeMillis;

        try {
            // FluentWait tương tự như vòng lặp của bạn nhưng hiệu quả hơn,
            // không block thread của hệ thống bằng Thread.sleep().
            Wait< WebDriver > wait = new FluentWait< >(driver)
                    .withTimeout(Duration.ofMillis(totalTimeoutMillis)) // Tổng thời gian chờ tối đa
                    .pollingEvery(Duration.ofMillis(waitTimeMillis)) // Tần suất lặp lại (Polling)
                    .ignoring(NoSuchElementException.class) // Tiếp tục lặp nếu không tìm thấy element
                    .ignoring(StaleElementReferenceException.class); // Tiếp tục lặp nếu element bị thay đổi

            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));

            if (element != null) {
                System.out.println("✅ Tồn tại phần tử: " + by);
                return true;
            }
        } catch (TimeoutException e) {
            System.out.println("❌ Không tìm thấy phần tử sau " + maxRetries + " lần thử.");
            return false;
        }
        return false;
    }

    public static void openURL(String url) {
        driver.get(url);
        sleep(STEP_TIME);
        logConsole("Open URL:  " + url);
    }

    public static void clickElement(By by) {
        waitForElementClickable(by);
        sleep(STEP_TIME);
        // Tim lai element truoc khi click, thu lai neu node bi thay doi luc do
        retryUntil (_driver ->{
            _driver.findElement(by).click();
            return true;
        });

        logConsole("Click on element " + by);
    }

    public static void clickElement(By by, int timeout) {
        waitForElementClickable(by, timeout);
        sleep(STEP_TIME);
        retryUntil (_driver ->{
            _driver.findElement(by).click();
            return true;
        });
        logConsole("Click on element " + by);
    }

    /** Replaces existing content. Use appendText to preserve existing text. */
    public static void setText(By by, String value) {
        java.util.Objects.requireNonNull(value, "value");
        WebElement element = newWait(TIMEOUT).until(ExpectedConditions.elementToBeClickable(by));
        element.clear();
        element.sendKeys(value);
        logConsole("Set text on element " + by);
    }

    private static WebDriverWait newWait(int timeoutSeconds) {
        if (timeoutSeconds < 0) {
            throw new IllegalArgumentException("timeoutSeconds must be non-negative");
        }
        WebDriverWait wait = new WebDriverWait(java.util.Objects.requireNonNull(driver, "Initialize WebUI with a driver first"),
                Duration.ofSeconds(timeoutSeconds));
        wait.ignoring(StaleElementReferenceException.class);
        return wait;
    }

    public static void clearText(By by) {
        newWait(TIMEOUT).until(ExpectedConditions.elementToBeClickable(by)).clear();
    }

    /** Appends without clearing. Keystrokes are not retried to avoid duplicate input. */
    public static void appendText(By by, String value) {
        java.util.Objects.requireNonNull(value, "value");
        newWait(TIMEOUT).until(ExpectedConditions.elementToBeClickable(by)).sendKeys(value);
    }

    public static void setSecretText(By by, String value) {
        setText(by, value);
    }

    public static String getInputValue(By by) {
        return getElementProperty(by, "value");
    }

    public static String getElementProperty(By by, String property) {
        return newWait(TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(by)).getDomProperty(property);
    }

    /** Snapshot checks; any configured implicit wait still applies. */
    public static boolean isElementPresent(By by) {
        return !driver.findElements(by).isEmpty();
    }

    public static boolean isElementVisible(By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public static boolean isElementEnabled(By by) {
        try {
            return driver.findElement(by).isEnabled();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public static boolean isElementSelected(By by) {
        try {
            return driver.findElement(by).isSelected();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /** Waits until every matching element is hidden or absent. */
    public static void waitForElementInvisible(By by) {
        waitForElementInvisible(by, TIMEOUT);
    }

    public static void waitForElementInvisible(By by, int timeoutSeconds) {
        newWait(timeoutSeconds).until(d -> d.findElements(by).stream().noneMatch(WebElement::isDisplayed));
    }

    public static void waitForText(By by, String expected) {
        newWait(TIMEOUT).until(ExpectedConditions.textToBe(by, expected));
    }

    public static void waitForTextContains(By by, String expected) {
        newWait(TIMEOUT).until(ExpectedConditions.textToBePresentInElementLocated(by, expected));
    }

    public static void waitForValue(By by, String expected) {
        newWait(TIMEOUT).until(d -> java.util.Objects.equals(d.findElement(by).getDomProperty("value"), expected));
    }

    public static void waitForAttribute(By by, String attribute, String expected) {
        newWait(TIMEOUT).until(ExpectedConditions.attributeToBe(by, attribute, expected));
    }

    public static void waitForAttributeContains(By by, String attribute, String expected) {
        newWait(TIMEOUT).until(ExpectedConditions.attributeContains(by, attribute, expected));
    }

    public static void setCheckbox(By by, boolean checked) {
        setCheckbox(by, by, checked);
    }

    /** clickTarget may be a label for a visually hidden native checkbox. */
    public static void setCheckbox(By checkbox, By clickTarget, boolean checked) {
        WebElement element = newWait(TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(checkbox));
        if (element.isSelected() != checked) {
            newWait(TIMEOUT).until(ExpectedConditions.elementToBeClickable(clickTarget)).click();
        }
        newWait(TIMEOUT).until(ExpectedConditions.elementSelectionStateToBe(checkbox, checked));
    }

    public static void acceptAlert() {
        newWait(TIMEOUT).until(ExpectedConditions.alertIsPresent()).accept();
    }

    public static void dismissAlert() {
        newWait(TIMEOUT).until(ExpectedConditions.alertIsPresent()).dismiss();
    }

    public static String getAlertText() {
        return newWait(TIMEOUT).until(ExpectedConditions.alertIsPresent()).getText();
    }

    public static void switchToFrame(By by) {
        newWait(TIMEOUT).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
    }

    public static void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public static void pressKey(By by, Keys key) {
        newWait(TIMEOUT).until(ExpectedConditions.elementToBeClickable(by)).sendKeys(key);
    }

    public static void pressKey(Keys key) {
        new Actions(driver).sendKeys(key).perform();
    }

    /** Required hover: propagates failures instead of silently returning false. */
    public static void hover(By by) {
        WebElement element = newWait(TIMEOUT).until(ExpectedConditions.visibilityOfElementLocated(by));
        new Actions(driver).moveToElement(element).perform();
    }

    public static void setTextAndKey(By by, String value, Keys key) {
        waitForPageLoaded();
        waitForElementVisible(by);
        getWebElement(by).sendKeys(value, key);
        logConsole("Set text and key on element " + by);
    }

    public static String getElementText(By by) {
        waitForElementVisible(by);
        sleep(STEP_TIME);
        logConsole("Get text of element " + by);
        String text = getWebElement(by).getText();
        logConsole("==> TEXT: " + text);
        return text; //Trả về một giá trị kiểu String
    }

    public static String getElementAttribute(By by, String attributeName) {
        waitForElementVisible(by);
        logConsole("Get attribute of element "+attributeName+" of element" + by);
        String value = getWebElement(by).getAttribute(attributeName);
        logConsole("==> Attribute value: " + value);
        return value;
    }
    public  static String getCurrentURL(){
        logConsole("Current URL: "+ driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }
    public static String getElementCssValue(By by, String cssPropertyName) {
        waitForElementVisible(by);
        System.out.println("Get CSS value " + cssPropertyName + " of element " + by);
        String value = getWebElement(by).getCssValue(cssPropertyName);
        System.out.println("==> CSS value: " + value);
        return value;
    }

    private static void retryUntil(Function<WebDriver, Boolean> action) {
        Wait<WebDriver> retryWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(TIMEOUT))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementClickInterceptedException.class);

        retryWait.until(action::apply);
    }

    private static void waitForJQueryLoad(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        try {
            wait.until(_driver -> (Boolean) ((JavascriptExecutor) driver)
                    .executeScript(
                            "return window.jQuery == undefined || jQuery.active == 0"));
        } catch (Exception ignored) {
        }
    }

    private static void waitForAngularLoad(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        try {
            wait.until(_driver -> (Boolean) ((JavascriptExecutor) driver)
                    .executeScript(
                            "return window.getAllAngularTestabilities ? " +
                                    "window.getAllAngularTestabilities()" +
                                    ".every(x=>x.isStable()) : true"));
        } catch (Exception ignored) {
        }
    }



    public static void smartWait() {
        waitForPageLoaded();
        sleep(STEP_TIME);
    }

    public static void scrollToElement(By by) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(false);", getWebElement(by));
    }

    public static void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(false);", element);
    }

    public static void scrollToElementAtTop(By by) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", getWebElement(by));
    }

    public static void scrollToElementAtBottom(By by) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(false);", getWebElement(by));
    }

    public static void scrollToElementAtTop(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToElementAtBottom(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(false);", element);
    }

    /**
     * Scroll an element into the visible area of the browser window. (at CENTER)
     *
     * @param by Represent a web element as the By object
     */
    public static void scrollToElementAtCenter(By by) {
        smartWait();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: " +
                        "'center', inline: 'center'});",
                getWebElement(by));
        logConsole("Scroll to element completely centered: " + by);
    }

    /**
     * Scroll an element into the visible area of the browser window. (at CENTER)
     *
     * @param webElement Represent a web element as the By object
     */
    public static void scrollToElementAtCenter(WebElement webElement) {
        smartWait();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: " +
                        "'center', inline: 'center'});",
                webElement);
        logConsole("Scroll to element completely centered: " + webElement);
    }

    public static void scrollToPosition(int X, int Y) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(" + X + "," + Y + ");");
    }

    public static boolean moveToElement(By by) {
        try {
            Actions action = new Actions(driver);
            action.moveToElement(getWebElement(by)).release(getWebElement(by)).build().perform();
            return true;
        } catch (Exception e) {
            logConsole(e.getMessage());
            return false;
        }
    }

    public static boolean moveToOffset(int X, int Y) {
        try {
            Actions action = new Actions(driver);
            action.moveByOffset(X, Y).build().perform();
            return true;
        } catch (Exception e) {
            logConsole(e.getMessage());
            return false;
        }
    }

    public static boolean hoverElement(By by) {
        try {
            Actions action = new Actions(driver);
            action.moveToElement(getWebElement(by)).perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean mouseHover(By by) {
        try {
            Actions action = new Actions(driver);
            action.moveToElement(getWebElement(by)).perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean dragAndDrop(By fromElement, By toElement) {
        try {
            Actions action = new Actions(driver);
            action.dragAndDrop(getWebElement(fromElement), getWebElement(toElement)).perform();
            //action.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            logConsole(e.getMessage());
            return false;
        }
    }

    public static boolean dragAndDropElement(By fromElement, By toElement) {
        try {
            Actions action = new Actions(driver);
            action.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            logConsole(e.getMessage());
            return false;
        }
    }

    public static boolean dragAndDropOffset(By fromElement, int X, int Y) {
        try {
            Actions action = new Actions(driver);
            //Tính từ vị trí click chuột đầu tiên (clickAndHold)
            action.clickAndHold(getWebElement(fromElement)).pause(1).moveByOffset(X, Y).release().build().perform();
            return true;
        } catch (Exception e) {
            logConsole(e.getMessage());
            return false;
        }
    }

    public static boolean pressENTER() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean pressESC() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean pressF11() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_F11);
            robot.keyRelease(KeyEvent.VK_F11);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param by truyền vào đối tượng element dạng By
     * @return Tô màu viền đỏ cho Element trên website
     */
    public static WebElement highLightElement(By by) {
        // Tô màu border ngoài chính element chỉ định - màu đỏ (có thể đổi màu khác)
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", getWebElement(by));
            sleep(1);
        }
        return getWebElement(by);
    }

    public static boolean verifyEquals(Object actual, Object expected) {
        waitForPageLoaded();
        System.out.println("Verify equals: " + actual + " and " + expected);
        boolean check = actual.equals(expected);
        return check;
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        waitForPageLoaded();
        System.out.println("Assert equals: " + actual + " and " + expected);
        if (actual.equals(expected)){
            logConsole("✅ Equals");
        }else
        {logConsole("❌ Not equals");}

        Assert.assertEquals(actual, expected, message);
    }

    public static boolean verifyContains(String actual, String expected) {
        waitForPageLoaded();
        System.out.println("Verify contains: " + actual + " and " + expected);
        boolean check = actual.contains(expected);
        return check;
    }

    public static void assertContains(String actual, String expected, String message) {
        waitForPageLoaded();
        System.out.println("Assert contains: " + actual + " and " + expected);
        boolean check = actual.contains(expected);
        Assert.assertTrue(check, message);
    }
}
