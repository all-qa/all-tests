package com.test;

import com.test.pages.GooglePage;
import com.test.pages.GoogleSearchResultsPage;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.qameta.allure.Allure.step;

@Feature("Google search engine")
public class GoogleSearchEngineE2eTest extends BaseE2eTest {

    @Test(description = "Search input quote is displayed in title", dataProvider = "keywords")
    @AllureId("1")
    @Story("Google search input")
    public void testGoogleSearchInputReturnsResult(String keyword) {
        SoftAssert softAssert = new SoftAssert();
        step("Opening google page", () -> {
                    GooglePage googlePage = getTestPage().openPage();
                    step("Searching google using keyword hello world and prompting for results with RETURN key", () -> {
                        GoogleSearchResultsPage googleSearchResultsPage = googlePage.googleSearch(keyword);
                        step("Check page title conains search keyword", () -> {
                            softAssert.assertTrue(googleSearchResultsPage.getPageTitle().contains(keyword));
                        });
                    });
                    step("Closing google page", () -> getTestPage().closePage());
            });

            softAssert.assertAll();
    }

    @Test(description = "Google Search Results Second Link Is Not Displayed", dataProvider = "keywords")
    @AllureId("2")
    @Story("Google search input")
    void testGoogleSearchResultsSecondLinkisNotDisplayed(String keyword) {
        SoftAssert softAssert = new SoftAssert();
        step("Opening google page", () -> {
            GooglePage googlePage = getTestPage().openPage();
            step("Searching google using keyword hello world and prompting for results with RETURN key", () -> {
                GoogleSearchResultsPage googleSearchResultsPage = googlePage.googleSearch(keyword);
                step("Check second link in results links is not displayed", () -> {
                    softAssert.assertFalse(googleSearchResultsPage.secondLink().isDisplayed());
                });
            });
            step("Closing google page", () -> getTestPage().closePage());
        });
        softAssert.assertAll();
    }

    @DataProvider(parallel = true)
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
