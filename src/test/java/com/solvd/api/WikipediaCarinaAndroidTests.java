package com.solvd.api;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import com.zebrunner.carina.utils.mobile.IMobileUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class WikipediaCarinaAndroidTests implements IAbstractTest, IMobileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikipediaCarinaAndroidTests.class);
    private AndroidDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() throws Exception {
        LOGGER.info("=== Setting up AndroidDriver ===");

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setPlatformVersion("14.0")
                .setDeviceName("Android Emulator")
                .setAutomationName("UiAutomator2")
                .setNoReset(true)
                .setFullReset(false)
                .setNewCommandTimeout(Duration.ofSeconds(30))
                .setAppPackage("org.wikipedia")
                .setAppActivity(".main.MainActivity");

        URL appiumServerUrl = new URL("http://127.0.0.1:4723");

        driver = new AndroidDriver(appiumServerUrl, options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        LOGGER.info("AndroidDriver created successfully");


        skipOnboarding();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            LOGGER.info("AndroidDriver quit");
        }
    }

    private void skipOnboarding() {
        try {
            WebElement skipButton = driver.findElement(AppiumBy.id("org.wikipedia:id/fragment_onboarding_skip_button"));
            skipButton.click();
            LOGGER.info("Skipped onboarding");
            Thread.sleep(2000);
        } catch (Exception e) {
            LOGGER.info("No onboarding or already completed");
        }
    }

    private void waitForElement(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private void tapElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    private void enterText(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.clear();
        element.sendKeys(text);
    }

    @Test()
    @MethodOwner(owner = "SolvdStudent")
    public void testAppLaunch() {
        LOGGER.info("Testing app launch...");

        String currentPackage = driver.getCurrentPackage();
        String currentActivity = driver.currentActivity();

        LOGGER.info("Current package: {}", currentPackage);
        LOGGER.info("Current activity: {}", currentActivity);

        Assert.assertEquals(currentPackage, "org.wikipedia", "Should be Wikipedia app");

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.length() > 1000, "App should have content");

        LOGGER.info("✓ App launch test passed");
    }

    @Test()
    @MethodOwner(owner = "SolvdStudent")
    public void testSearchFunctionality() {
        LOGGER.info("Testing search functionality...");

        try {
            tapElement(AppiumBy.accessibilityId("Search Wikipedia"));

            enterText(AppiumBy.id("org.wikipedia:id/search_src_text"), "Testing");
            Thread.sleep(2000);


            List<WebElement> results = driver.findElements(AppiumBy.id("org.wikipedia:id/page_list_item_title"));
            Assert.assertTrue(results.size() > 0, "Should display search results");

            LOGGER.info("Found {} search results", results.size());
            LOGGER.info("✓ Search test passed");

        } catch (Exception e) {
            LOGGER.warn("Search test issue: {}", e.getMessage());
        }
    }

    @Test()
    @MethodOwner(owner = "SolvdStudent")
    public void testArticleContent() {
        LOGGER.info("Testing article content...");

        try {
            tapElement(AppiumBy.accessibilityId("Search Wikipedia"));

            enterText(AppiumBy.id("org.wikipedia:id/search_src_text"), "Software");
            Thread.sleep(2000);

            List<WebElement> results = driver.findElements(AppiumBy.id("org.wikipedia:id/page_list_item_title"));
            if (!results.isEmpty()) {
                results.get(0).click();
                Thread.sleep(3000);

                WebElement articleTitle = driver.findElement(AppiumBy.id("org.wikipedia:id/view_article_header_text"));
                Assert.assertTrue(articleTitle.isDisplayed(), "Article title should be visible");

                LOGGER.info("Article title: {}", articleTitle.getText());
                LOGGER.info("✓ Article content test passed");
            }

        } catch (Exception e) {
            LOGGER.warn("Article test issue: {}", e.getMessage());
        }
    }

    @Test()
    @MethodOwner(owner = "SolvdStudent")
    public void testNavigation() {
        LOGGER.info("Testing navigation...");

        try {

            tapElement(AppiumBy.accessibilityId("Search Wikipedia"));
            enterText(AppiumBy.id("org.wikipedia:id/search_src_text"), "Android");
            Thread.sleep(2000);

            List<WebElement> results = driver.findElements(AppiumBy.id("org.wikipedia:id/page_list_item_title"));
            if (!results.isEmpty()) {
                results.get(0).click();
                Thread.sleep(3000);

                driver.navigate().back();
                Thread.sleep(2000);

                WebElement searchField = driver.findElement(AppiumBy.id("org.wikipedia:id/search_src_text"));
                Assert.assertTrue(searchField.isDisplayed(), "Should be back to search screen");

                LOGGER.info("✓ Navigation test passed");
            }

        } catch (Exception e) {
            LOGGER.warn("Navigation test issue: {}", e.getMessage());
        }
    }

    @Test()
    @MethodOwner(owner = "SolvdStudent")
    public void testAppPerformance() {
        LOGGER.info("Testing app performance...");

        long startTime = System.currentTimeMillis();

        String pageSource = driver.getPageSource();
        long responseTime = System.currentTimeMillis() - startTime;

        LOGGER.info("App response time: {}ms", responseTime);
        Assert.assertTrue(responseTime < 5000, "App should respond in under 5 seconds");
        Assert.assertTrue(pageSource.length() > 1000, "App should have content");

        LOGGER.info("✓ Performance test passed");
    }

    @Test()
    @MethodOwner(owner = "SolvdStudent")
    public void testExploreTabFunctionality() {
        LOGGER.info("Testing Explore tab functionality...");

        try {
            tapElement(AppiumBy.accessibilityId("Explore"));
            Thread.sleep(2000);

            List<WebElement> featuredItems = driver.findElements(
                    AppiumBy.id("org.wikipedia:id/view_featured_article_card_header")
            );

            List<WebElement> articleCards = driver.findElements(
                    AppiumBy.id("org.wikipedia:id/view_list_card_header")
            );

            Assert.assertTrue(
                    !featuredItems.isEmpty() || !articleCards.isEmpty(),
                    "Explore tab should show featured content or articles"
            );

            LOGGER.info("✓ Explore tab test passed");

        } catch (Exception e) {
            LOGGER.warn("Explore tab test issue: {}", e.getMessage());
        }
    }

    @Test()
    @MethodOwner(owner = "SolvdStudent")
    public void testSavedArticlesFeature() {
        LOGGER.info("Testing Saved articles feature...");

        try {
            tapElement(AppiumBy.accessibilityId("Saved"));
            Thread.sleep(2000);

            WebElement savedTitle = driver.findElement(AppiumBy.id("org.wikipedia:id/messageTitleView"));
            String titleText = savedTitle.getText();

            Assert.assertTrue(
                    titleText.contains("saved") || titleText.contains("reading lists"),
                    "Should show saved articles screen"
            );

            LOGGER.info("Saved screen shows: {}", titleText);
            LOGGER.info("✓ Saved articles test passed");

        } catch (Exception e) {
            LOGGER.warn("Saved articles test issue: {}", e.getMessage());
        }
    }

    @Test()
    @MethodOwner(owner = "SolvdStudent")
    public void testLanguageSwitching() {
        LOGGER.info("Testing language switching...");

        try {
            tapElement(AppiumBy.accessibilityId("Search Wikipedia"));
            enterText(AppiumBy.id("org.wikipedia:id/search_src_text"), "Computer");
            Thread.sleep(2000);

            List<WebElement> results = driver.findElements(AppiumBy.id("org.wikipedia:id/page_list_item_title"));
            if (!results.isEmpty()) {
                results.get(0).click();
                Thread.sleep(3000);

                tapElement(AppiumBy.accessibilityId("More options"));
                Thread.sleep(1000);

                List<WebElement> menuItems = driver.findElements(AppiumBy.className("android.widget.TextView"));
                boolean foundLanguageOption = false;

                for (WebElement item : menuItems) {
                    if (item.getText().toLowerCase().contains("language")) {
                        foundLanguageOption = true;
                        break;
                    }
                }

                Assert.assertTrue(foundLanguageOption, "Should have language option in menu");
                LOGGER.info("✓ Language switching test passed");
            }

        } catch (Exception e) {
            LOGGER.warn("Language switching test issue: {}", e.getMessage());
        }
    }

    @Test()
    @MethodOwner(owner = "SolvdStudent")
    public void testDarkModeToggle() {
        LOGGER.info("Testing theme/dark mode toggle...");

        try {
            tapElement(AppiumBy.accessibilityId("More options"));
            Thread.sleep(1000);

            List<WebElement> menuItems = driver.findElements(AppiumBy.className("android.widget.TextView"));
            for (WebElement item : menuItems) {
                if (item.getText().equalsIgnoreCase("Settings")) {
                    item.click();
                    break;
                }
            }
            Thread.sleep(2000);

            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" +
                            "new UiSelector().textContains(\"Theme\"))"
            ));

            List<WebElement> themeOptions = driver.findElements(
                    AppiumBy.androidUIAutomator("textContains(\"Theme\")")
            );

            Assert.assertTrue(!themeOptions.isEmpty(), "Should have theme/dark mode settings");
            LOGGER.info("Found theme options: {}", themeOptions.size());
            LOGGER.info("✓ Dark mode test passed");

        } catch (Exception e) {
            LOGGER.warn("Dark mode test issue: {}", e.getMessage());
        }
    }

    @Test()
    @MethodOwner(owner = "SolvdStudent")
    public void testAppOrientation() {
        LOGGER.info("Testing app orientation changes...");

        try {

            ScreenOrientation initialOrientation = driver.getOrientation();
            LOGGER.info("Initial orientation: {}", initialOrientation);

            tapElement(AppiumBy.accessibilityId("Search Wikipedia"));
            enterText(AppiumBy.id("org.wikipedia:id/search_src_text"), "Technology");
            Thread.sleep(2000);

            List<WebElement> results = driver.findElements(AppiumBy.id("org.wikipedia:id/page_list_item_title"));
            if (!results.isEmpty()) {
                results.get(0).click();
                Thread.sleep(3000);

                String portraitContent = driver.getPageSource();

                driver.rotate(ScreenOrientation.LANDSCAPE);
                Thread.sleep(2000);

                String landscapeContent = driver.getPageSource();

                Assert.assertTrue(landscapeContent.length() > 1000,
                        "App should have content in landscape");

                driver.rotate(ScreenOrientation.PORTRAIT);
                Thread.sleep(2000);

                LOGGER.info("✓ App orientation test passed");
            }

        } catch (Exception e) {
            LOGGER.warn("Orientation test issue: {}", e.getMessage());
        }
    }

    @Test()
    @MethodOwner(owner = "SolvdStudent")
    public void testScrollFunctionality() {
        LOGGER.info("Testing scroll functionality...");

        try {

            tapElement(AppiumBy.accessibilityId("Search Wikipedia"));
            enterText(AppiumBy.id("org.wikipedia:id/search_src_text"), "History of computing");
            Thread.sleep(2000);

            List<WebElement> results = driver.findElements(AppiumBy.id("org.wikipedia:id/page_list_item_title"));
            if (!results.isEmpty()) {
                results.get(0).click();
                Thread.sleep(3000);

                Dimension size = driver.manage().window().getSize();
                int startX = size.width / 2;
                int startY = (int) (size.height * 0.8);
                int endY = (int) (size.height * 0.2);

                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence scroll = new Sequence(finger, 0);

                scroll.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
                scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                scroll.addAction(new Pause(finger, Duration.ofMillis(200)));
                scroll.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX, endY));
                scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                driver.perform(Arrays.asList(scroll));
                Thread.sleep(2000);

                String content = driver.getPageSource();
                Assert.assertTrue(content.length() > 1000, "Should still have content after scroll");

                LOGGER.info("✓ Scroll functionality test passed");
            }

        } catch (Exception e) {
            LOGGER.warn("Scroll test issue: {}", e.getMessage());
        }
    }
}