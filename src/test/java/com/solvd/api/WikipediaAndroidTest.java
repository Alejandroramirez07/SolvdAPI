package com.solvd.api;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class WikipediaAndroidTest {

    @Test
    public void testWikipediaMobileLoad() throws MalformedURLException, InterruptedException {
        System.out.println("=== Wikipedia Mobile Test ===");
        System.out.println("Setting up Android driver...");

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

        System.out.println("Creating AndroidDriver...");
        System.out.println("Attempting to launch: org.wikipedia/.main.MainActivity");

        AndroidDriver driver = new AndroidDriver(appiumServerUrl, options);

        try {
            System.out.println("SUCCESS! Wikipedia app launched!");
            System.out.println("Session ID: " + driver.getSessionId());
            System.out.println("Current Package: " + driver.getCurrentPackage());
            System.out.println("Current Activity: " + driver.currentActivity());

            Thread.sleep(5000);

            System.out.println("\n=== Device Information ===");
            System.out.println("Device Name: " + driver.getCapabilities().getCapability("deviceName"));
            System.out.println("Platform Version: " + driver.getCapabilities().getCapability("platformVersion"));
            System.out.println("Device UDID: " + driver.getCapabilities().getCapability("deviceUDID"));

            System.out.println("Available contexts: " + driver.getContextHandles());

            try {
                String pageSource = driver.getPageSource();
                System.out.println("\nPage source length: " + pageSource.length() + " characters");
                System.out.println("App is responsive and ready for testing!");
            } catch (Exception e) {
                System.out.println("Could not get page source, but app is running: " + e.getMessage());
            }

            System.out.println("\n=== Test Completed Successfully ===");

        } catch (Exception e) {
            System.out.println("Error during test: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {

            if (driver != null) {
                driver.quit();
                System.out.println("Driver quit successfully.");
            }
        }
    }
}