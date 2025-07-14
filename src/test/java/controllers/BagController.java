package controllers;

import configs.TestPropertiesConfig;
import dto.AddItemRequest;
import dto.BagResponse;
import dto.UpdateItemRequest;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import support.TokenManager;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class BagController {

    RequestSpecification requestSpecification;
    private static final String ITEMS_ENDPOINT = "/ugp-api/bag/v1/items";
    private static final String BAG_ENDPOINT = "/ugp-api/bag/v1";


    TestPropertiesConfig configProperties = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    public BagController() {
        this.requestSpecification = given()
                .accept(JSON)
                .contentType(JSON)
                .baseUri(configProperties.getApiBaseUrl())
                .header("aesite", "AEO_US")
                .header("x-access-token", TokenManager.getToken())
                .filter(new AllureRestAssured());
    }

    @Step("Add item to bag")
    public Response addItem(String skuId, int quantity) {
        AddItemRequest.Item item = new AddItemRequest.Item(skuId, quantity);
        AddItemRequest request = new AddItemRequest(List.of(item));

        return given(this.requestSpecification)
                .body(request)
                .when()
                .post(ITEMS_ENDPOINT)
                .andReturn();
    }

    @Step("Get bag")
    public BagResponse getBag() {
        return given(this.requestSpecification)
                .when()
                .get(BAG_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(BagResponse.class);
    }
    @Step("Get list of items in bag")
    public List<BagResponse.Item> getItemsInBagList() {
        return getBag()
                .getData()
                .getItems();
    }

    @Step("Update item in bag")
    public Response updateItem(String skuId, int quantity, String itemId) {
        UpdateItemRequest.Item item = new UpdateItemRequest.Item(skuId, quantity, itemId);
        UpdateItemRequest request = new UpdateItemRequest(List.of(item));

        return given(this.requestSpecification)
                .body(request)
                .when()
                .patch(ITEMS_ENDPOINT)
                .andReturn();
    }

    @Step("Delete item in bag")
    public Response deleteItem(String itemId) {

        return given(this.requestSpecification)
                .when()
                .delete(ITEMS_ENDPOINT + "?itemIds=" + itemId)
                .andReturn();
    }
}
