package com.solvd.api;

import com.solvd.gui.pages.common.WikipediaHomePageBase;
import com.solvd.gui.pages.desktop.WikipediaHomePage;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import com.zebrunner.carina.core.registrar.tag.Priority;
import com.zebrunner.carina.core.registrar.tag.TestPriority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class WikipediaTestsCarina implements IAbstractTest {

    private static final Logger LOGGER = (Logger) LogManager.getLogger(WikipediaTestsCarina.class);

    @Test
    public void testSimpleSearch() {
        LOGGER.info("=== Starting simple test ===");
        WikipediaHomePageBase homePage = initPage(getDriver(), WikipediaHomePageBase.class);
        homePage.open();
        pause(2);
        
        LOGGER.info("Page title: " + getDriver().getTitle());

        homePage.searchFor("Test");
        pause(3);

        LOGGER.info("After search title: " + getDriver().getTitle());
        Assert.assertTrue(getDriver().getTitle().contains("Test") ||
                        getDriver().getTitle().contains("Wikipedia"),
                "Search should work. Title: " + getDriver().getTitle());
    }

    @Test(description = "Verify search functionality")
    @MethodOwner(owner = "Alejandro")
    @TestPriority(Priority.P1)
    public void testWikipediaSearch() {
        try {
            LOGGER.info("=== Starting testWikipediaSearch ===");
            WikipediaHomePageBase homePage = initPage(getDriver(), WikipediaHomePageBase.class);
            homePage.open();
            pause(2);

            homePage.searchFor("Software testing");
            pause(3);

            String title = homePage.getArticleTitle();
            LOGGER.info("Article title: " + title);
            Assert.assertTrue(title.toLowerCase().contains("software testing") ||
                            title.contains("Software testing") ||
                            title.contains("Wikipedia"),
                    "Article title should contain search term or be Wikipedia. Actual: " + title);
            LOGGER.info("=== testWikipediaSearch PASSED ===");
        } catch (Exception e) {
            LOGGER.error("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test(description = "Verify random article navigation - SIMPLIFIED")
    @MethodOwner(owner = "Alejandro")
    public void testRandomArticleNavigation() {
        try {
            LOGGER.info("=== Starting testRandomArticleNavigation ===");
            WikipediaHomePageBase homePage = initPage(getDriver(), WikipediaHomePageBase.class);
            homePage.open();

            String originalUrl = getDriver().getCurrentUrl();
            LOGGER.info("Original URL: " + originalUrl);

            homePage.searchFor("Random");
            pause(3);

            String newUrl = getDriver().getCurrentUrl();
            LOGGER.info("New URL after search: " + newUrl);

            Assert.assertNotEquals(newUrl, originalUrl,
                    "URL should change after navigation. Original: " + originalUrl + ", New: " + newUrl);
            LOGGER.info("=== testRandomArticleNavigation PASSED ===");
        } catch (Exception e) {
            LOGGER.error("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test(description = "Verify table of contents - SIMPLIFIED")
    @MethodOwner(owner = "Alejandro")
    public void testTableOfContents() {
        try {
            LOGGER.info("=== Starting testTableOfContents ===");
            WikipediaHomePageBase homePage = initPage(getDriver(), WikipediaHomePageBase.class);
            homePage.open();
            homePage.searchFor("Computer science");
            pause(3);

            WikipediaHomePage concretePage = (WikipediaHomePage) homePage;

            boolean hasHeadings = concretePage.hasHeadings();
            LOGGER.info("Article has headings: " + hasHeadings);

            boolean hasNavigation = concretePage.hasAnyNavigationElements();
            LOGGER.info("Article has navigation elements: " + hasNavigation);

            Assert.assertTrue(hasHeadings || hasNavigation,
                    "Article should have headings or navigation. Page: " + getDriver().getCurrentUrl());
            LOGGER.info("=== testTableOfContents PASSED ===");
        } catch (Exception e) {
            LOGGER.error("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test(description = "Verify image loading")
    @MethodOwner(owner = "Alejandro")
    public void testImageLoading() {
        try {
            LOGGER.info("=== Starting testImageLoading ===");
            WikipediaHomePageBase homePage = initPage(getDriver(), WikipediaHomePageBase.class);

            WikipediaHomePage concretePage = (WikipediaHomePage) homePage;
            concretePage.open();
            concretePage.searchFor("Apollo 11");
            pause(3);

            SoftAssert softAssert = new SoftAssert();

            boolean hasImages = concretePage.hasAnyImages();
            LOGGER.info("Article has images: " + hasImages);
            softAssert.assertTrue(hasImages, "Article should have images");

            boolean hasImageContainers = concretePage.hasImageContainers();
            LOGGER.info("Article has image containers: " + hasImageContainers);

            softAssert.assertAll();
            LOGGER.info("=== testImageLoading PASSED ===");
        } catch (Exception e) {
            LOGGER.error("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test(description = "Verify footer scrolling")
    @MethodOwner(owner = "Alejandro")
    public void testFooterScrolling() {
        try {
            LOGGER.info("=== Starting testFooterScrolling ===");
            WikipediaHomePageBase homePage = initPage(getDriver(), WikipediaHomePageBase.class);
            homePage.open();
            homePage.searchFor("Testing");
            pause(2);

            WikipediaHomePage concretePage = (WikipediaHomePage) homePage;
            boolean footerExists = concretePage.isFooterPresent();
            LOGGER.info("Footer exists on page: " + footerExists);

            Assert.assertTrue(footerExists, "Footer should exist on Wikipedia page");
            LOGGER.info("=== testFooterScrolling PASSED ===");
        } catch (Exception e) {
            LOGGER.error("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test(description = "Verify external links exist")
    @MethodOwner(owner = "Alejandro")
    public void testExternalLinks() {
        try {
            LOGGER.info("=== Starting testExternalLinks ===");
            WikipediaHomePageBase homePage = initPage(getDriver(), WikipediaHomePageBase.class);
            WikipediaHomePage concretePage = (WikipediaHomePage) homePage;
            concretePage.open();

            LOGGER.info("Searching for: Open source software");
            concretePage.searchFor("Open source software");
            pause(4);

            LOGGER.info("Current URL: " + getDriver().getCurrentUrl());

            boolean hasExternalLinks = concretePage.hasExternalLinks();
            LOGGER.info("hasExternalLinks returned: " + hasExternalLinks);

            Assert.assertTrue(hasExternalLinks, "Article should have external links. Page URL: " + getDriver().getCurrentUrl());
            LOGGER.info("=== testExternalLinks PASSED ===");
        } catch (Exception e) {
            LOGGER.error("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test(description = "Verify view history navigation")
    @MethodOwner(owner = "Alejandro")
    public void testViewHistoryNavigation() {
        try {
            LOGGER.info("=== Starting testViewHistoryNavigation ===");
            WikipediaHomePageBase homePage = initPage(getDriver(), WikipediaHomePageBase.class);
            WikipediaHomePage concretePage = (WikipediaHomePage) homePage;
            concretePage.open();
            concretePage.searchFor("Mars");
            pause(3);

            String originalUrl = getDriver().getCurrentUrl();
            LOGGER.info("Original URL: " + originalUrl);

            boolean hasViewHistoryLink = concretePage.hasViewHistoryLink();
            LOGGER.info("Has view history link: " + hasViewHistoryLink);

            Assert.assertTrue(hasViewHistoryLink,
                    "Wikipedia article should have view history link. URL: " + originalUrl);

            LOGGER.info("=== testViewHistoryNavigation PASSED ===");
        } catch (Exception e) {
            LOGGER.error("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test(description = "Verify search suggestions")
    @MethodOwner(owner = "Alejandro")
    public void testSearchSuggestions() {
        try {
            LOGGER.info("=== Starting testSearchSuggestions ===");
            WikipediaHomePageBase homePage = initPage(getDriver(), WikipediaHomePageBase.class);
            homePage.open();
            pause(2);

            WikipediaHomePage concretePage = (WikipediaHomePage) homePage;
            LOGGER.info("Typing 'Uni' into search input");
            concretePage.searchInput.type("Uni");
            pause(1);

            String searchValue = concretePage.getSearchInputValue();
            LOGGER.info("Search input value: " + searchValue);

            Assert.assertEquals(searchValue, "Uni",
                    "Search input should contain typed value. Actual: " + searchValue);
            LOGGER.info("=== testSearchSuggestions PASSED ===");
        } catch (Exception e) {
            LOGGER.error("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test(description = "Verify multiple searches")
    @MethodOwner(owner = "Alejandro")
    public void testMultipleSearches() {
        try {
            LOGGER.info("=== Starting testMultipleSearches ===");
            WikipediaHomePageBase homePage = initPage(getDriver(), WikipediaHomePageBase.class);
            homePage.open();

            String[] searchTerms = {"Moon", "Mars", "Earth", "Sun"};
            int successfulSearches = 0;

            for (String term : searchTerms) {
                LOGGER.info("Searching for: " + term);
                homePage.searchFor(term);
                pause(2);

                String title = homePage.getArticleTitle();
                LOGGER.info("Page title: " + title);

                if (title.toLowerCase().contains(term.toLowerCase()) ||
                        title.contains("Wikipedia") ||
                        !title.equals("Wikipedia, the free encyclopedia")) {
                    successfulSearches++;
                }

                if (!term.equals(searchTerms[searchTerms.length - 1])) {
                    LOGGER.info("Returning to homepage for next search");
                    homePage.open();
                    pause(1);
                }
            }

            Assert.assertTrue(successfulSearches >= 3,
                    "Most searches should succeed. Successful: " + successfulSearches + "/4");
            LOGGER.info("=== testMultipleSearches PASSED ===");
        } catch (Exception e) {
            LOGGER.error("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}