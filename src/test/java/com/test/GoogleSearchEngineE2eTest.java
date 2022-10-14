package com.test;

import com.test.pages.GooglePage;
import com.test.pages.GoogleSearchResultsPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Feature("Google search engine")
public class GoogleSearchEngineE2eTest extends BaseE2eTest {

    @Test(dataProvider = "keywords")
    @Story("Google search input")
    public void testGoogleSearchInputReturnsResult(String keyword) {
        try {
            GooglePage googlePage = getTestPage().openPage();
            GoogleSearchResultsPage googleSearchResultsPage = googlePage.googleSearch(keyword);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(googleSearchResultsPage.getPageTitle().contains(keyword));
            softAssert.assertAll();
        } finally {
            getTestPage().closePage();
        }
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
