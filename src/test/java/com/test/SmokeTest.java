package com.test;

import com.test.pages.GooglePage;
import com.test.pages.GoogleSearchResultsPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("smoke test epic")
public class SmokeTest extends BaseTest {

    @Test(dataProvider = "keywords")
    @Story("smoke test for google")
    public void testGoogleSearch_smoke(String keyword) {
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

    @DataProvider
    public Object[][] keywords() {
        return new Object[][] {
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
