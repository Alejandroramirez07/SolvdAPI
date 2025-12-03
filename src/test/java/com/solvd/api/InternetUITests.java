package com.solvd.api;

import com.zebrunner.carina.core.AbstractTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;

public class InternetUITests extends AbstractTest {
    private static final String BASE_URL = "https://the-internet.herokuapp.com/";

    private WebElement waitForElementVisible(By locator, Duration timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void dragAndDropJS(WebElement source, WebElement target) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String script = "var src=arguments[0], tgt=arguments[1];" +
                "var dataTransfer = {" +
                "  data: {}," +
                "  setData: function(format, data) {" +
                "    this.data[format] = data;" +
                "  }," +
                "  getData: function(format) {" +
                "    return this.data[format];" +
                "  }" +
                "};" +
                "var dragStart = new Event('dragstart', { bubbles: true });" +
                "dragStart.dataTransfer = dataTransfer;" +
                "src.dispatchEvent(dragStart);" +
                "var dragOver = new Event('dragover', { bubbles: true });" +
                "dragOver.dataTransfer = dataTransfer;" +
                "tgt.dispatchEvent(dragOver);" +
                "var drop = new Event('drop', { bubbles: true });" +
                "drop.dataTransfer = dataTransfer;" +
                "tgt.dispatchEvent(drop);" +
                "var dragEnd = new Event('dragend', { bubbles: true });" +
                "dragEnd.dataTransfer = dataTransfer;" +
                "src.dispatchEvent(dragEnd);";
        js.executeScript(script, source, target);
    }

    @Test(description = "Verify successful login using RemoteWebDriver against Standalone Server")
    public void testFormAuthenticationAndTextAssertion() {
        getDriver().get(BASE_URL + "login");
        WebElement usernameField = getDriver().findElement(By.id("username"));
        usernameField.sendKeys("tomsmith");
        WebElement passwordField = getDriver().findElement(By.id("password"));
        passwordField.sendKeys("SuperSecretPassword!");
        getDriver().findElement(By.cssSelector("button[type='submit']")).click();
        WebElement flashMessage = getDriver().findElement(By.id("flash"));
        String expectedText = "You logged into a secure area!";
        assertTrue(flashMessage.getText().contains(expectedText),
                "The flash message should contain the success text: " + expectedText);
        assertTrue(getDriver().getCurrentUrl().contains("/secure"),
                "The current URL should contain '/secure' after successful login.");
    }

    @Test(description = "Verify checkbox state changes and is displayed")
    public void testCheckboxInteractionAndStateAssertion() {
        getDriver().get(BASE_URL + "checkboxes");
        By checkbox1Locator = By.xpath("(//input[@type='checkbox'])[1]");
        WebElement checkbox1 = getDriver().findElement(checkbox1Locator);
        assertFalse(checkbox1.isSelected(),
                "Checkbox 1 should be initially unchecked.");
        checkbox1.click();
        assertTrue(checkbox1.isSelected(),
                "Checkbox 1 should be checked after clicking.");
        By checkbox2Locator = By.xpath("(//input[@type='checkbox'])[2]");
        WebElement checkbox2 = getDriver().findElement(checkbox2Locator);
        assertTrue(checkbox2.isSelected(),
                "Checkbox 2 should be initially checked.");
        checkbox2.click();
        assertFalse(checkbox2.isSelected(),
                "Checkbox 2 should be unchecked after clicking.");
    }

    @Test(description = "Verify file upload functionality")
    public void testFileUpload() {
        getDriver().get(BASE_URL + "upload");
        File dummyFile = new File("temp_upload_file.txt");
        try {
            if (!dummyFile.exists()) {
                assertTrue(dummyFile.createNewFile(), "Could not create dummy file for upload.");
            }
            String absolutePath = dummyFile.getAbsolutePath();
            WebElement fileInput = getDriver().findElement(By.id("file-upload"));
            fileInput.sendKeys(absolutePath);
            getDriver().findElement(By.id("file-submit")).click();
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            WebElement uploadedFileName = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("uploaded-files"))
            );
            assertTrue(uploadedFileName.getText().contains(dummyFile.getName()),
                    "Uploaded file name should match the expected name. Found: " + uploadedFileName.getText());
        } catch (IOException e) {
            throw new RuntimeException("File upload test failed due to IO error: " + e.getMessage(), e);
        } finally {
            if (dummyFile.exists()) {
                dummyFile.delete();
            }
        }
    }

    @Test(description = "Verify selection in a dropdown list")
    public void testDropdownSelection() {
        getDriver().get(BASE_URL + "dropdown");
        WebElement dropdownElement = getDriver().findElement(By.id("dropdown"));
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText("Option 2");
        String selectedOption = dropdown.getFirstSelectedOption().getText();
        assertEquals(selectedOption, "Option 2",
                "The selected option should be 'Option 2'.");
        dropdown.selectByIndex(1);
        selectedOption = dropdown.getFirstSelectedOption().getText();
        assertEquals(selectedOption, "Option 1",
                "The selected option should be 'Option 1'.");
    }

    @Test(description = "Verify hover interaction and text change")
    public void testHoverAndTextAssertion() {
        getDriver().get(BASE_URL + "hovers");
        WebElement image = getDriver().findElement(By.xpath("(//div[@class='figure'])[1]"));
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        WebElement caption = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[@class='figcaption'])[1]"))
        );
        assertFalse(caption.isDisplayed(),
                "Caption should not be visible before hover.");
        Actions actions = new Actions(getDriver());
        actions.moveToElement(image).perform();
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(caption));
        String finalCaptionText = caption.getText();
        assertTrue(finalCaptionText.contains("name: user1"),
                "Caption should contain user info after hover. Found: " + finalCaptionText);
    }

    @Test(description = "Verify context click opens the alert")
    public void testContextClick() {
        getDriver().get(BASE_URL + "context_menu");
        WebElement hotSpot = getDriver().findElement(By.id("hot-spot"));
        Actions actions = new Actions(getDriver());
        actions.contextClick(hotSpot).perform();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText = getDriver().switchTo().alert().getText();
        assertEquals(alertText, "You selected a context menu",
                "Alert text should match the expected context menu message.");
        getDriver().switchTo().alert().accept();
    }

    @Test(description = "Verify drag and drop between two elements")
    public void testDragAndDrop() {
        getDriver().get(BASE_URL + "drag_and_drop");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement columnA = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("column-a"))
        );
        WebElement columnB = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("column-b"))
        );
        assertEquals(columnA.getText().trim(), "A", "Initial header of Column A should be 'A'.");
        assertEquals(columnB.getText().trim(), "B", "Initial header of Column B should be 'B'.");
        dragAndDropJS(columnA, columnB);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String finalTextA = columnA.getText().trim();
        String finalTextB = columnB.getText().trim();
        assertEquals(finalTextA, "B",
                String.format("Final header of Column A should be 'B'. Got: '%s'", finalTextA));
        assertEquals(finalTextB, "A",
                String.format("Final header of Column B should be 'A'. Got: '%s'", finalTextB));
    }

    @Test(description = "Verify dynamic content changes upon refresh")
    public void testDynamicContentChange() {
        getDriver().get(BASE_URL + "dynamic_content");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement firstContent = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content .row .large-10.columns"))
        );
        String initialText = firstContent.getText();
        getDriver().navigate().refresh();
        WebElement refreshedContent = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content .row .large-10.columns"))
        );
        String newText = refreshedContent.getText();
        assertNotEquals(newText, initialText,
                "Content should have changed after page refresh.");
    }

    @Test(description = "Verify element visibility after a wait")
    public void testElementIsHiddenAndBecomesVisible() {
        getDriver().get(BASE_URL + "dynamic_loading/1");
        WebElement startButton = getDriver().findElement(By.xpath("//button"));
        startButton.click();
        By finishTextLocator = By.id("finish");
        WebElement finishElement = waitForElementVisible(finishTextLocator, Duration.ofSeconds(10));
        assertEquals(finishElement.getText(), "Hello World!",
                "The displayed text should be 'Hello World!'");
    }

    @Test(description = "Verify scrolling to a hidden element")
    public void testInfiniteScrollAndElementVisibility() {
        getDriver().get(BASE_URL + "infinite_scroll");
        SoftAssert softAssert = new SoftAssert();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        long initialScrollHeight = (Long) js.executeScript("return document.body.scrollHeight");
        By contentLocator = By.cssSelector(".jscroll-inner > div");
        int initialContentCount = getDriver().findElements(contentLocator).size();
        System.out.println("Initial state - Scroll height: " + initialScrollHeight +
                ", Content items: " + initialContentCount);
        for (int i = 0; i < 5; i++) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            long currentScrollHeight = (Long) js.executeScript("return document.body.scrollHeight");
            int currentContentCount = getDriver().findElements(contentLocator).size();
            System.out.println("After scroll " + (i + 1) + " - " +
                    "Scroll height: " + currentScrollHeight +
                    ", Content items: " + currentContentCount);
        }
        long finalScrollHeight = (Long) js.executeScript("return document.body.scrollHeight");
        int finalContentCount = getDriver().findElements(contentLocator).size();
        System.out.println("Final state - Scroll height: " + finalScrollHeight +
                ", Content items: " + finalContentCount);
        softAssert.assertTrue(finalScrollHeight > initialScrollHeight,
                "Page height should increase after infinite scrolling. " +
                        "Initial: " + initialScrollHeight + ", Final: " + finalScrollHeight);
        softAssert.assertTrue(finalContentCount > initialContentCount,
                "Should have more content items after infinite scrolling. " +
                        "Initial: " + initialContentCount + ", Final: " + finalContentCount);
        int additionalItems = finalContentCount - initialContentCount;
        softAssert.assertTrue(additionalItems >= 3,
                "Should have at least 3 additional content items after scrolling. Found: " + additionalItems);
        softAssert.assertAll();
    }
}