package com.solvd.gui.pages.android;

import com.solvd.gui.pages.common.WikipediaHomePageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = WikipediaHomePageBase.class)
public class WikipediaHomePage extends WikipediaHomePageBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    // Mobile specific locators
    @FindBy(css = "#searchIcon, .header-search-label")
    private ExtendedWebElement searchIcon;

    @FindBy(css = "input.search, input[name='search']")
    private ExtendedWebElement searchInput;

    @FindBy(css = "button.search-submit, .search-button")
    private ExtendedWebElement searchButton;

    @FindBy(xpath = "//div[contains(@class, 'header-menu-button')]//label | //a[@id='mw-mf-main-menu-button']")
    private ExtendedWebElement hamburgerMenu;

    @FindBy(xpath = "//a[@data-event-name='menu.random']")
    private ExtendedWebElement randomArticleLink;

    @FindBy(xpath = "//a[contains(@href, '/de.')]")
    private ExtendedWebElement germanLanguageLink;

    @FindBy(css = "#toc, .toc-mobile, .section-heading")
    private ExtendedWebElement tableOfContents;

    @FindBy(css = "footer a, .footer-content a")
    private List<ExtendedWebElement> footerLinks;

    @FindBy(css = "#section_0, h1#section_0")
    private ExtendedWebElement articleTitle;

    @FindBy(css = ".image img, .thumbimage")
    private ExtendedWebElement mainImage;

    @FindBy(css = ".external")
    private List<ExtendedWebElement> externalLinks;

    @FindBy(css = "#ca-history")
    private ExtendedWebElement viewHistoryLink;

    public WikipediaHomePage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
        setPageAbsoluteURL("https://en.m.wikipedia.org");
    }

    @Override
    public void searchFor(String query) {

        if (searchIcon.isElementPresent(3)) {
            searchIcon.click();
        }
        searchInput.type(query);

        if (searchButton.isElementPresent(3)) {
            searchButton.click();
        } else {

        }
    }

    @Override
    public String getArticleTitle() {
        return articleTitle.getText();
    }

    @Override
    public void switchToGerman() {

        if (hamburgerMenu.isElementPresent(3)) {
            hamburgerMenu.click();
            pause(500);

        }
    }

    @Override
    public void openRandomArticle() {
        if (hamburgerMenu.isElementPresent(3)) {
            hamburgerMenu.click();
            pause(1000); // Wait for animation
            randomArticleLink.click();
        }
    }

    @Override
    public boolean isTableOfContentsPresent() {
        // On mobile web, sections are collapsed. We check if the section headers exist.
        return tableOfContents.isElementPresent(3);
    }

    @Override
    public void scrollToFooter() {
        // Simple Javascript scroll for mobile
        if (!footerLinks.isEmpty()) {
            footerLinks.get(0).scrollTo();
        }
    }

    @Override
    public boolean hasExternalLinks() {
        return !externalLinks.isEmpty();
    }

    @Override
    public void openViewHistory() {
        // History is often hard to reach on Mobile Web without logging in or specific menu
        // We might need to append ?action=history to URL or click the "Last edited" text
        ExtendedWebElement lastEdited = findExtendedWebElement(org.openqa.selenium.By.cssSelector(".last-modified-bar"));
        if(lastEdited.isElementPresent(3)){
            lastEdited.click();
        }
    }

    @Override
    public boolean isMainImagePresent() {
        return mainImage.isElementPresent(5);
    }

    @Override
    public String getMainImageSrc() {
        if (isMainImagePresent()) {
            return mainImage.getAttribute("src");
        }
        return "";
    }
}