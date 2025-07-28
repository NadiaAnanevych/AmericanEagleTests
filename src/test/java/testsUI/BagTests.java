package testsUI;



import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BagPage;
import java.time.Duration;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pages.BasePage.*;

@Tag("UI Tests")
public class BagTests  extends BaseTest{
    BagPage bagPage;


    @BeforeEach
    void setupPage() {
        bagPage = new BagPage(driver);
        bagPage.openHomePage();
    }



    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("positive")})
    @DisplayName("Select item from search results and add to the bag")
    void selectFirstItemFromSearchResultsAndAddToTheBag(){
        String textToSearch = "women jeans";

        bagPage.selectFirstItemAndClickOnIt(textToSearch);
        assertThat(bagPage.getPriceOfSelectedItem()).isNotEmpty();
        bagPage.selectFirstAvailableSize();
        bagPage.addItemToTheBag();
        assertEquals("Added to bag!", bagPage.getSuccessfulAddedToTheBagMessage());
        bagPage.clickViewItemInTheBag();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.urlToBe(SHIPPING_BAG_URL));
        assertAll(
                () -> assertEquals(SHIPPING_BAG_URL, bagPage.getCurrentUrl()),
                () -> assertEquals("Shopping Bag", bagPage.getShoppingBagMessage())
        );
    }


    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("positive")})
    @DisplayName("Check item name and quantity match data in the bag")
    void checkItemsNameAndQuantityMatchDataInTheCard(){
        String textToSearch = "women jeans";
        String expectedQuantityOfItem = "1 Item";

        bagPage.selectFirstItemAndClickOnIt(textToSearch);
        bagPage.selectFirstAvailableSize();
        bagPage.addItemToTheBag();
        String catalogItemName = bagPage.getNameOfSelectedItem();
        bagPage.clickViewItemInTheBag();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.urlToBe(SHIPPING_BAG_URL));

        assertThat(bagPage.getCurrentUrl())
                .isEqualTo(SHIPPING_BAG_URL);

        assertThat(bagPage.getQuantityOfItems())
                .contains(expectedQuantityOfItem);
        assertThat(bagPage.getCardNameOfSelectedItem())
                .isEqualTo(catalogItemName);

    }

    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("positive")})
    @DisplayName("Select item from search, add it to the bag and check price")
    void addItemFromSearchAndCheckPriceInTheBag(){
        String textToSearch = "women jeans";
        bagPage.selectFirstItemAndClickOnIt(textToSearch);
        double itemCatalogPrice = bagPage.getItemPrice();
        bagPage.selectFirstAvailableSize();
        bagPage.addItemToTheBag();
        bagPage.clickViewItemInTheBag();
        double productPriceInCart = bagPage.getCardItemPrice();
        double totalProductPriceInCart = Math.round((itemCatalogPrice + bagPage.getShippingPrice()) * 100.0) / 100.0;

        assertAll(
                () -> assertEquals(itemCatalogPrice, productPriceInCart),
                () -> assertEquals(totalProductPriceInCart, bagPage.getSubTotalPrice())
        );
    }

    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("defect"), @Tag("positive")})
    @DisplayName("Select item from catalog, add it to the bag and check price")
    void addItemFromCatalogAndCheckPriceInTheBag(){
        bagPage.moveToWomenCategory();
        bagPage.selectJeansInWomenCategory();
        bagPage.selectFirstItemFromCatalogAndClickOnIt();
        double itemCatalogPrice = bagPage.getItemPrice();
        bagPage.selectFirstAvailableSize();
        bagPage.addItemToTheBag();
        bagPage.clickViewItemInTheBag();
        double productPriceInCart = bagPage.getCardItemPrice();
        double totalProductPriceInCart = Math.round((itemCatalogPrice + bagPage.getShippingPrice()) * 100.0) / 100.0;

        assertAll(
                () -> assertEquals(itemCatalogPrice, productPriceInCart),
                () -> assertEquals(totalProductPriceInCart, bagPage.getSubTotalPrice())
        );
    }

    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("positive")})
    @DisplayName("Select item from search, add it to the bag and update quantity")
    void addItemFromSearchAndUpdateItInTheBag(){
        String textToSearch = "women jeans";
        String quantityOfItem = "1 Item";
        String updatedQuantityOfItem = "2 Item";
        bagPage.selectFirstItemAndClickOnIt(textToSearch);
        bagPage.selectFirstAvailableSize();
        bagPage.addItemToTheBag();
        bagPage.clickViewItemInTheBag();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.urlToBe(SHIPPING_BAG_URL));

        assertThat(bagPage.getQuantityOfItems())
                .contains(quantityOfItem);

        bagPage.clickEditButton();
        bagPage.movingToElementUpdateBagButton();

        assertThat(bagPage.isEditItemModalIsVisible()).isTrue();

        bagPage.changeItemQty();
        bagPage.updateBag();

        assertThat(bagPage.getQuantityOfItems())
                .contains(updatedQuantityOfItem);

    }

    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("defect"), @Tag("positive")})
    @DisplayName("Select item from catalog, add it to the bag and update quantity")
    void addItemFromCatalogAndUpdateItInTheBag(){
        String quantityOfItem = "1 Item";
        String updatedQuantityOfItem = "2 Item";
        bagPage.moveToWomenCategory();
        bagPage.selectJeansInWomenCategory();

        assertEquals("Women's Jeans", bagPage.getWomenPageTitle());
        assertThat(bagPage.getCurrentUrl()).isEqualTo(WOMEN_JEANS_URL);

        bagPage.selectFirstItemFromCatalogAndClickOnIt();
        bagPage.selectFirstAvailableSize();
        bagPage.addItemToTheBag();
        bagPage.clickViewItemInTheBag();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.urlToBe(SHIPPING_BAG_URL));

        assertThat(bagPage.getQuantityOfItems())
                .contains(quantityOfItem);

        bagPage.clickEditButton();
        bagPage.movingToElementUpdateBagButton();

        assertThat(bagPage.isEditItemModalIsVisible()).isTrue();

        bagPage.changeItemQty();
        bagPage.updateBag();

        assertThat(bagPage.getQuantityOfItems())
                .contains(updatedQuantityOfItem);

    }

    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("positive")})
    @DisplayName("Select item from search, add it to the bag and remove item from the bag")
    void addItemFromSearchToTheBagAndRemove(){
        String textToSearch = "women jeans";
        String expectedEmptyBagMessage = "Your bag is empty. Find something you love!";
        bagPage.selectFirstItemAndClickOnIt(textToSearch);
        bagPage.selectFirstAvailableSize();
        bagPage.addItemToTheBag();
        bagPage.clickViewItemInTheBag();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.urlToBe(SHIPPING_BAG_URL));
        bagPage.removeItemFromBag();

        assertThat(expectedEmptyBagMessage)
                .isEqualTo(bagPage.getBagEmptyMessage());

    }


    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("defect"), @Tag("positive")})
    @DisplayName("Select item from catalog, add it to the bag and remove item from the bag")
    void addItemFromCatalogToTheBagAndRemove(){
        String expectedEmptyBagMessage = "Your bag is empty. Find something you love!";
        bagPage.moveToWomenCategory();
        bagPage.selectJeansInWomenCategory();
        bagPage.selectFirstItemFromCatalogAndClickOnIt();
        bagPage.selectFirstAvailableSize();
        bagPage.addItemToTheBag();
        bagPage.clickViewItemInTheBag();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.urlToBe(SHIPPING_BAG_URL));
        bagPage.removeItemFromBag();

        assertThat(expectedEmptyBagMessage)
                .isEqualTo(bagPage.getBagEmptyMessage());

    }

    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("positive")})
    @DisplayName("Select item from search, add it to the bag and go to checkout page")
    void addItemFromSearchToTheBagAndGoToCheckout(){
        String textToSearch = "women jeans";
        String expectedCheckoutTitle = "Checkout";

        bagPage.selectFirstItemAndClickOnIt(textToSearch);
        bagPage.selectFirstAvailableSize();
        bagPage.addItemToTheBag();
        bagPage.clickViewItemInTheBag();
        bagPage.clickCheckoutButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.urlToBe(CHECKOUT_URL));

        assertAll(
                () -> assertEquals(CHECKOUT_URL, bagPage.getCurrentUrl()),
                () -> assertEquals(expectedCheckoutTitle, bagPage.getCheckoutTitle())
        );
    }


    @Test
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("defect"), @Tag("positive")})
    @DisplayName("Select item from catalog, add it to the bag and go to checkout page")
    void addItemFromCatalogToTheBagAndGoToCheckout(){
        String expectedCheckoutTitle = "Checkout";
        bagPage.moveToWomenCategory();
        bagPage.selectJeansInWomenCategory();
        bagPage.selectFirstItemFromCatalogAndClickOnIt();
        bagPage.selectFirstAvailableSize();
        bagPage.addItemToTheBag();
        bagPage.clickViewItemInTheBag();
        bagPage.clickCheckoutButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.urlToBe(CHECKOUT_URL));

        assertAll(
                () -> assertEquals(CHECKOUT_URL, bagPage.getCurrentUrl()),
                () -> assertEquals(expectedCheckoutTitle, bagPage.getCheckoutTitle())
        );
    }

}
