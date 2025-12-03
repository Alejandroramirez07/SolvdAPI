package com.solvd.gui.pages.common;

import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;

public abstract class WikipediaHomePageBase extends AbstractPage {

    public WikipediaHomePageBase(WebDriver driver) {
        super(driver);
    }

    public abstract void searchFor(String query);
    public abstract String getArticleTitle();
    public abstract void switchToGerman();
    public abstract void openRandomArticle();
    public abstract boolean isTableOfContentsPresent();
    public abstract void scrollToFooter();
    public abstract boolean hasExternalLinks();
    public abstract void openViewHistory();
    public abstract boolean isMainImagePresent();
    public abstract String getMainImageSrc();
}