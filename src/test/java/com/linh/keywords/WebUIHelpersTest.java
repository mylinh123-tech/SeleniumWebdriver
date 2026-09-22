package com.linh.keywords;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/** Browser-free contract checks; run sequentially because WebUI owns a static driver. */
@Test(singleThreaded = true)
public class WebUIHelpersTest {
    private static final By FIELD = By.id("field");

    private static <T> T stub(Class<T> type, InvocationHandler handler) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, handler));
    }

    private static void useElements(WebElement... elements) {
        new WebUI(stub(WebDriver.class, (proxy, method, args) -> switch (method.getName()) {
            case "findElement" -> elements[0];
            case "findElements" -> List.of(elements);
            case "toString" -> "WebUI test driver";
            default -> throw new UnsupportedOperationException(method.getName());
        }));
    }

    @Test
    public void testReplaceAppendAndReadCurrentValue() {
        StringBuilder value = new StringBuilder("old");
        WebElement input = stub(WebElement.class, (proxy, method, args) -> switch (method.getName()) {
            case "isDisplayed", "isEnabled" -> true;
            case "clear" -> { value.setLength(0); yield null; }
            case "sendKeys" -> {
                for (CharSequence keys : (CharSequence[]) args[0]) value.append(keys);
                yield null;
            }
            case "getDomProperty" -> value.toString();
            case "toString" -> "input";
            default -> throw new UnsupportedOperationException(method.getName());
        });
        useElements(input);
        WebUI.setText(FIELD, "new");
        WebUI.appendText(FIELD, " suffix");
        Assert.assertEquals(WebUI.getInputValue(FIELD), "new suffix", "Replacement must remove old content, append must preserve it");
        WebUI.setText(FIELD, "");
        Assert.assertEquals(WebUI.getInputValue(FIELD), "", "Empty text must clear existing content");
    }

    @Test
    public void testCheckboxLabelIsIdempotent() {
        AtomicBoolean selected = new AtomicBoolean(false);
        AtomicInteger clicks = new AtomicInteger();
        WebElement checkbox = stub(WebElement.class, (proxy, method, args) -> switch (method.getName()) {
            case "isSelected" -> selected.get();
            case "toString" -> "hidden checkbox";
            default -> throw new UnsupportedOperationException(method.getName());
        });
        WebElement label = stub(WebElement.class, (proxy, method, args) -> switch (method.getName()) {
            case "isDisplayed", "isEnabled" -> true;
            case "click" -> { selected.set(!selected.get()); clicks.incrementAndGet(); yield null; }
            default -> throw new UnsupportedOperationException(method.getName());
        });
        By labelBy = By.id("label");
        new WebUI(stub(WebDriver.class, (proxy, method, args) -> {
            if (method.getName().equals("findElement")) return FIELD.equals(args[0]) ? checkbox : label;
            throw new UnsupportedOperationException(method.getName());
        }));
        WebUI.setCheckbox(FIELD, labelBy, true);
        WebUI.setCheckbox(FIELD, labelBy, true);
        Assert.assertEquals(clicks.get(), 1, "Setting the same state twice must not toggle again");
        WebUI.setCheckbox(FIELD, labelBy, false);
        Assert.assertFalse(selected.get(), "Label click must support clearing a hidden checkbox");
    }

    @Test
    public void testInvisibleWaitChecksAllMatches() {
        WebElement hidden = stub(WebElement.class, (proxy, method, args) -> false);
        WebElement visible = stub(WebElement.class, (proxy, method, args) -> true);
        useElements(hidden, visible);
        Assert.expectThrows(TimeoutException.class, () -> WebUI.waitForElementInvisible(FIELD, 0));
        useElements(hidden);
        WebUI.waitForElementInvisible(FIELD, 0);
        useElements();
        WebUI.waitForElementInvisible(FIELD, 0);
        Assert.assertFalse(WebUI.isElementPresent(FIELD), "An absent element must not be reported as present");
    }

    @Test
    public void testStateCheckDoesNotHideBrokenSession() {
        new WebUI(stub(WebDriver.class, (proxy, method, args) -> {
            throw new NoSuchSessionException("Session closed");
        }));
        Assert.expectThrows(NoSuchSessionException.class, () -> WebUI.isElementVisible(FIELD));
    }
}
