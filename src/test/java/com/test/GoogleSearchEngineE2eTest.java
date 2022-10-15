package com.test;

import com.test.pages.GoogleSearchResultsPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.qameta.allure.Allure.step;

@Feature("Google search engine")
public class GoogleSearchEngineE2eTest extends BaseE2eTest {

    @Test(description = "Search input quote is displayed in title", dataProvider = "keywords")
    @Story("Google search input")
    public void testGoogleSearchInputReturnsResult(String keyword) {
        SoftAssert softAssert = new SoftAssert();
        step("Searching google using keyword hello world and prompting for results with RETURN key", () -> {
            GoogleSearchResultsPage googleSearchResultsPage = getTestPage().googleSearch(keyword);
            step("Check page title contains search keyword", () -> {
                softAssert.assertTrue(googleSearchResultsPage.getPageTitle().contains(keyword));
            });
        });
        softAssert.assertAll();
    }

    @Test(description = "Google Search Results Second Link Is Displayed", dataProvider = "keywords")
    @Story("Google search input")
    public void testGoogleSearchResultsSecondLinkIsNotDisplayed(String keyword) {
        SoftAssert softAssert = new SoftAssert();
        step("Searching google using keyword hello world and prompting for results with RETURN key", () -> {
            GoogleSearchResultsPage googleSearchResultsPage = getTestPage().googleSearch(keyword);
            step("Check second link in results links is not displayed", () -> {
                softAssert.assertTrue(googleSearchResultsPage.secondLink().isDisplayed());
            });
        });
        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] keywords() {
        return new Object[][]{
                {
                        "hello world"
                },
                {
                        "java programming language"
                },
                {
                        "qa automation"
                }
        };
    }

}
