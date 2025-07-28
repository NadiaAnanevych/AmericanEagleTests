package testsUI;

import org.junit.jupiter.api.*;
import pages.HomePage;
import utils.Utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pages.BasePage.BASE_URL_UI;
import static pages.BasePage.WOMEN_JEANS_URL;


@Tag("UI Tests")
public class HomePageTests extends BaseTest{
    HomePage homePage;


    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
        homePage.openHomePage();
    }

    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("positive")})
    @DisplayName("Check HomePage url")
    void checkHomePageUrlAndTitleTest() {
        String title = homePage.getTitle();
        assertEquals(BASE_URL_UI, homePage.getCurrentUrl());
        assertEquals("Go to Shop AE homepage.", title);
    }

    @Test
    @Tags({@Tag("P0"), @Tag("positive")})
    @DisplayName("Logo is displayed")
    void checkLogoIsDisplayedTest(){
        assertThat(homePage.logoIsDisplayed()).isTrue();
    }

    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("positive")})
    @DisplayName("Check Search icon is displayed")
    void checkSearchIconIsDisplayedTest() {
        assertTrue(homePage.searchButtonIsDisplayed());
    }

    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("positive")})
    @DisplayName("Check item can be searched and search results are displayed")
    void checkItemCanBeSearchedAndResultsAreDisplayedTest() {
        String textToSearch = "jeans";
        String formattedAsQueryText = Utils.formatSearchQueryForUrl(textToSearch);
        String expectedUrl = "https://www.ae.com/us/en/s/" + formattedAsQueryText;

        homePage.clickSearchButton();
        assertThat(homePage.isSearchModalVisible()).isTrue();
        homePage.waitForSearchField();
        homePage.sendTextInSearchField(textToSearch);
        homePage.clickSubmitButton();
        assertThat(homePage.getSubtitleAfterSearchText()).contains(textToSearch);
        assertEquals(expectedUrl, homePage.getCurrentUrl());
    }

    @Test
    @Tags({@Tag("P1"), @Tag("negative")})
    @DisplayName("Check message when search non existing item")
    void searchForNonExistingItem() {
        String textToSearch = "1111111";
        String expectedErrorText = "Sorry! We couldn't find a match for " + textToSearch + ".";
        String expectedUrl = BASE_URL_UI + "s/" + textToSearch;

        homePage.clickSearchButton();
        homePage.waitForSearchField();
        homePage.sendTextInSearchField(textToSearch);
        homePage.clickSubmitButton();
        assertThat(homePage.getTextOfErrorSearchMessage())
                .isEqualTo(expectedErrorText);
        assertThat(homePage.getCurrentUrl()).isEqualTo(expectedUrl);
    }


    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("positive")})
    @DisplayName("Logo is displayed")
    void checkAccountIconIsDisplayed() {

        assertTrue(homePage.accountIconIsDisplayed());
    }

    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("positive")})
    @DisplayName("'Sigh in' and 'Create account' button are visible after clicking on Account icon")
    void checkSignInAndCreateAccountButtonAreDisplayedOnAccountModal(){
        homePage.clickAccountIcon();
        assertAll(
                () -> assertTrue(homePage.signInButtonIsDisplayed()),
                () -> assertEquals("Account", homePage.getAccountTitleText()),
                () -> assertTrue(homePage.createAccountButtonIsDisplayed()),
                () -> assertEquals("Account", homePage.getAccountTitleText())
        );
    }

    @Test
    @Tags({@Tag("P1"), @Tag("positive")})
    @DisplayName("Favorites icon is displayed")
    void checkFavoritesIconIsDisplayed() {
        assertTrue(homePage.favoritesIconIsDisplayed());
    }

    @Test
    @Tags({@Tag("P1"), @Tag("positive")})
    @DisplayName("Check 'Favorites' icon can be clicked and 'Favorites' title text is displayed")
    void checkFavoritesIconCanBeClickedAndFavoritesTitleIsDisplayed(){
        homePage.clickFavoritesIcon();
        String actualText = homePage.getFavoritesTitleText();
        assertEquals("Favorites", actualText);
    }

    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("positive")})
    @DisplayName("Bag icon is displayed")
    void checkBagIconIsDisplayed() {
        assertTrue(homePage.bagIconIsDisplayed());
    }

    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("positive")})
    @DisplayName("Check 'Bag' icon can be clicked and 'Shopping bag' title text is displayed")
    void checkBagIconCanBeClickedAndShoppingBagTitleIsDisplayed(){
        homePage.clickBagIcon();
        String actualText = homePage.getBagTitleText();
        assertEquals("Shopping Bag", actualText);
    }

    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("positive")})
    @DisplayName("Select subcategory items from main category")
    void checkItemsCanBeSelectedFromMainCategory(){
        homePage.moveToWomenCategory();
        homePage.selectJeansInWomenCategory();
        assertEquals("Women's Jeans", homePage.getWomenPageTitle());
        assertThat(homePage.getCurrentUrl()).isEqualTo(WOMEN_JEANS_URL);
    }

}
