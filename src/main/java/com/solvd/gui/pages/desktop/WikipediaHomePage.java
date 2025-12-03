package com.solvd.gui.pages.desktop;

import com.solvd.gui.pages.common.WikipediaHomePageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = WikipediaHomePageBase.class)
public class WikipediaHomePage extends WikipediaHomePageBase {

    @FindBy(name = "search")
    public ExtendedWebElement searchInput;

    @FindBy(css = ".cdx-search-input__end-button, #searchButton, button[type='submit']")
    private ExtendedWebElement searchButton;

    @FindBy(css = "#n-randompage a")
    private ExtendedWebElement randomArticleLink;

    @FindBy(css = "a[lang='de'], a[href*='de.wikipedia']")
    private ExtendedWebElement germanLanguageLink;

    @FindBy(css = "#toc, .toc")
    private ExtendedWebElement tableOfContents;

    @FindBy(css = "#footer a")
    private List<ExtendedWebElement> footerLinks;

    @FindBy(css = "h1#firstHeading")
    private ExtendedWebElement articleTitle;

    @FindBy(css = ".infobox img, .thumbimage")
    private ExtendedWebElement mainImage;

    @FindBy(css = ".external, a[rel='nofollow']")
    private List<ExtendedWebElement> externalLinks;

    @FindBy(css = "#ca-history a")
    private ExtendedWebElement viewHistoryLink;

    @FindBy(xpath = "//div[@id='p-lang']")
    private ExtendedWebElement languageLinks;

    @FindBy(id = "mw-navigation")
    private ExtendedWebElement navigationSidebar;

    public WikipediaHomePage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
        setPageAbsoluteURL("https://en.wikipedia.org");
    }

    @Override
    public void searchFor(String query) {

        searchInput.click();
        searchInput.type(query);

        searchButton.click();
    }

    @Override
    public void openRandomArticle() {
        // Scroll to the element first
        randomArticleLink.scrollTo();
        pause(500);
        randomArticleLink.click();
    }

    @Override
    public void switchToGerman() {
        if (germanLanguageLink.isElementPresent(10)) {
            germanLanguageLink.click();
        } else {
            List<WebElement> langLinks = getDriver().findElements(
                    By.cssSelector("a[href*='//de.wikipedia.org']")
            );
            if (!langLinks.isEmpty()) {
                langLinks.get(0).click();
            }
        }
    }

    @Override
    public boolean isTableOfContentsPresent() {
        return tableOfContents.isElementPresent(10);
    }

    @Override
    public void scrollToFooter() {
        if (!footerLinks.isEmpty()) {
            footerLinks.get(0).scrollTo();
            pause(1000);
        } else {

            ((JavascriptExecutor) getDriver()).executeScript(
                    "window.scrollTo(0, document.body.scrollHeight);"
            );
            pause(1000);
        }
    }

    @Override
    public String getArticleTitle() {
        return articleTitle.getText();
    }

    public boolean isMainImagePresent() {
        return mainImage.isElementPresent(10);
    }

    public String getMainImageSrc() {
        return mainImage.getAttribute("src");
    }

    public boolean hasExternalLinks() {
        try {
            // Multiple selectors for external links in Wikipedia
            String[] externalLinkSelectors = {
                    "//a[contains(@class, 'external')]",
                    "//a[contains(@class, 'extiw')]",
                    "//a[@rel='nofollow' and starts-with(@href, 'http')]",
                    "//a[starts-with(@href, 'http') and not(contains(@href, 'wikipedia.org')) and not(contains(@href, 'wikimedia.org'))]"
            };

            int totalLinksFound = 0;

            for (String selector : externalLinkSelectors) {
                List<ExtendedWebElement> links = findExtendedWebElements(By.xpath(selector));
                if (!links.isEmpty()) {
                    System.out.println("Found " + links.size() + " links with selector: " + selector);
                    for (int i = 0; i < Math.min(links.size(), 3); i++) {
                        String href = links.get(i).getAttribute("href");
                        System.out.println("  Link " + (i+1) + ": " + href);
                    }
                    totalLinksFound += links.size();
                }
            }

            System.out.println("Total external links found: " + totalLinksFound);
            return totalLinksFound > 0;

        } catch (Exception e) {
            System.err.println("Error checking external links: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void openExternalLink(int index) {
        if (index < externalLinks.size()) {
            externalLinks.get(index).click();
        }
    }

    public void openViewHistory() {
        viewHistoryLink.click();
    }

    public void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean hasLanguageLinks() {
        try {
            // Check for language links section
            return !findExtendedWebElements(By.xpath("//div[@id='p-lang']//a")).isEmpty();
        } catch (Exception e) {
            System.err.println("Error checking language links: " + e.getMessage());
            return false;
        }
    }

    public int getLanguageLinkCount() {
        try {
            return findExtendedWebElements(By.xpath("//div[@id='p-lang']//a")).size();
        } catch (Exception e) {
            System.err.println("Error counting language links: " + e.getMessage());
            return 0;
        }
    }

    public boolean hasHeadings() {
        try {
            // Check for article headings (h1, h2, h3)
            return !findExtendedWebElements(By.xpath("//div[@id='bodyContent']//h1|//div[@id='bodyContent']//h2|//div[@id='bodyContent']//h3")).isEmpty();
        } catch (Exception e) {
            System.err.println("Error checking headings: " + e.getMessage());
            return false;
        }
    }

    public boolean hasAnyNavigationElements() {
        try {
            // Check for various navigation elements
            String[] navSelectors = {
                    "//*[contains(@class, 'toc')]",
                    "//*[contains(@class, 'navbox')]",
                    "//*[contains(@class, 'sidebar')]",
                    "//*[contains(@class, 'infobox')]"
            };

            for (String selector : navSelectors) {
                if (!findExtendedWebElements(By.xpath(selector)).isEmpty()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error checking navigation: " + e.getMessage());
            return false;
        }
    }

    public boolean hasAnyImages() {
        try {
            return !findExtendedWebElements(By.xpath("//img")).isEmpty();
        } catch (Exception e) {
            System.err.println("Error checking images: " + e.getMessage());
            return false;
        }
    }

    public boolean hasImageContainers() {
        try {
            String[] containerSelectors = {
                    "//*[contains(@class, 'infobox')]",
                    "//*[contains(@class, 'thumb')]",
                    "//*[contains(@class, 'image')]",
                    "//figure"
            };

            for (String selector : containerSelectors) {
                if (!findExtendedWebElements(By.xpath(selector)).isEmpty()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error checking image containers: " + e.getMessage());
            return false;
        }
    }

    public boolean isFooterPresent() {
        try {
            return !findExtendedWebElements(By.id("footer")).isEmpty();
        } catch (Exception e) {
            System.err.println("Error checking footer: " + e.getMessage());
            return false;
        }
    }

    public boolean hasViewHistoryLink() {
        try {
            return !findExtendedWebElements(By.xpath("//a[contains(@href, 'action=history')]")).isEmpty();
        } catch (Exception e) {
            System.err.println("Error checking view history link: " + e.getMessage());
            return false;
        }
    }

    public String getSearchInputValue() {
        try {
            return searchInput.getAttribute("value");
        } catch (Exception e) {
            System.err.println("Error getting search input value: " + e.getMessage());
            return "";
        }
    }

}