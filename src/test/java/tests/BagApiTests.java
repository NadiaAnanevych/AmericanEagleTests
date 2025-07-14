package tests;

import controllers.BagController;
import dto.BagResponse;
import extensions.GuestTokenExtension;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GuestTokenExtension.class)
@Tag("API tests")
public class BagApiTests {
    private final BagController bag = new BagController();
    private static final String TEST_SKU_ID = "0041890237";

    @DisplayName("Checking adding item to the card")
    @Tags({@Tag("PO"),@Tag("smoke"), @Tag("positive")})
    @Test
    void addItemTest() {
        int qty = 1;

        bag.addItem(TEST_SKU_ID, qty)
                .then()
                .statusCode(202);
    }

    @DisplayName("Checking getting item from the bag")
    @Tags({@Tag("PO"),@Tag("smoke"), @Tag("positive")})
    @Test
    void getItemTest() {
        BagResponse response = bag.getBag();
        assertNotNull(response, "Bag response is not null");

        BagResponse.BagData data = response.getData();
        String expectedId = "o12463916716";
        String actualId = response.getData().getId();
        assertNotNull(data.getId(), "ID shouldn't be null");
        assertEquals(expectedId, actualId);
        assertNotNull(data.getCurrencyCode(), "Currency code shouldn't be null");
    }

    @DisplayName("Checking getting list of items in the bag")
    @Tags({@Tag("P1"),@Tag("smoke"), @Tag("positive")})
    @Test
    void getListOfItemsTest() {
        List<BagResponse.Item> items = bag.getItemsInBagList();

        assertThat(items)
                .as("q-ty of items in the bag")
                .isNotNull()
                .isNotEmpty();

        BagResponse.Item firstItem = items.get(0);
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(firstItem.getItemId()).as("itemId").isNotNull();
        softly.assertThat(firstItem.getProductId()).as("productId").isNotNull();
        softly.assertThat(firstItem.getProductName()).as("productName").isNotNull();
        softly.assertThat(firstItem.getQuantity()).as("quantity").isGreaterThan(0);
        softly.assertThat(firstItem.getPrice()).as("price").isGreaterThanOrEqualTo(0.0);

        softly.assertAll();
    }

    @DisplayName("Checking updating the item in the bag")
    @Tags({@Tag("P0"),@Tag("smoke"), @Tag("positive")})
    @Test
    void updateItemTest(){
        int newQty = 2;
        String itemId = "373d2837-6c8b-4ebd-9a1f-7ebc394373ff";

        bag.updateItem(TEST_SKU_ID, newQty, itemId)
                .then()
                .statusCode(202);
        BagResponse afterUpdate = bag.getBag();

        assertThat(afterUpdate.getData().getItems())
                .as("Bag should contain item with sku %s and quantity %d", TEST_SKU_ID, newQty)
                .anySatisfy(item -> {
                    assertThat(item.getSku()).isEqualTo(TEST_SKU_ID);
                    assertThat(item.getQuantity()).isEqualTo(newQty);
                });
    }

    @DisplayName("Checking deleting the item from the bag")
    @Tags({@Tag("P0"),@Tag("smoke"), @Tag("positive")})
    @Test
    void deleteItemTest() {
        int qty = 1;
        bag.addItem(TEST_SKU_ID, qty)
                .then()
                .statusCode(202);

        List<BagResponse.Item> items = bag.getItemsInBagList();
        assertThat(items).isNotEmpty();
        String itemId = items.get(0).getItemId();

        bag.deleteItem(itemId)
                .then()
                .statusCode(202);

        List<BagResponse.Item> afterDelete = bag.getItemsInBagList();
        assertThat(afterDelete)
                .as("Item should be removed from the bag")
                .noneMatch(item -> item.getItemId().equals(itemId));
    }
}
