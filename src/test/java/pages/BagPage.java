package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BagPage extends BasePage{

    public BagPage(WebDriver driver) {
        super(driver);
    }
    @FindBy(xpath = "//button[@data-test-btn='search-cta']")
    private WebElement searchButton;
    @FindBy(xpath = "//div[@class='modal-content _modal-content_1vao1q']")
    private WebElement searchModal;
    @FindBy(xpath = "//input[@name = \"search\"]")
    private WebElement searchInputField;
    @FindBy(xpath = "//button[@data-test-btn='submit']")
    private WebElement submitButton;
    @FindBy(xpath = "//div[@data-testid='search-results']")
    private WebElement subtitleAfterSearch;
    @FindBy(xpath = "(//div[contains(@class, 'results-list') and contains(@class, 'qa-search-results-list')]/*)[1]")
    private WebElement firstItemFromResults;
    @FindBy(xpath = "//div[@class='modal-body']//div[contains(@class, 'added-to-bag-product')]//div[contains (@class, 'title')]")
    private WebElement nameOfSelectedItem;
    @FindBy(xpath = "//h3[@data-test-cart-item-name]//a")
    private WebElement cardNameOfSelectedItem;
    @FindBy(xpath = "//div[@data-testid = \"sale-price\"]")
    private WebElement priceOfSelectedItem;
    @FindBy(xpath = "//div[contains(@class, '_savings-container_')]//span[@data-test-cart-item-sale-price]")
    private WebElement cardPriceOfSelectedItem;
    @FindBy(xpath =  "//div[@data-test-size-quantity-selector]")
    private WebElement sizeContainer;
    @FindBy(xpath = "//div[@data-test-select-custom='size']//div[@data-test-dropdown-toggle]")
    private WebElement sizeDropdown;
    @FindBy(xpath = "//ul[contains(@class, 'dropdown-menu')]//li[not(contains(@class, 'visually-disabled'))][1]/a")
    private WebElement sizeOfSelectedItem;
    @FindBy(css = "ul.dropdown-menu li:not(.visually-disabled)")
    private List<WebElement> availableSizes;
    @FindBy(name = "addToBag")
    private WebElement addToBagButton;
    @FindBy(xpath = "//h2[text()='Added to bag!']")
    private WebElement successfulAddedToTheBagMessage;
    @FindBy(name = "viewBag")
    private WebElement viewBagButton;
    @FindBy(xpath = "//h1[contains(normalize-space(.), 'Shopping Bag')]")
    private WebElement shoppingBagMessage;
    @FindBy(xpath = "//h2[@data-test-items-qty-msg]")
    private WebElement quantityOfItems;
    @FindBy(name = "editCommerceItem")
    private WebElement editButton;
    @FindBy(xpath = "//button[text()='Update Bag']")
    private WebElement updateBagButton;
    @FindBy(name = "removeCommerceItem")
    private WebElement removeButton;
    @FindBy(xpath = "//h2[text()='Your bag is empty. Find something you love!']")
    private WebElement emptyBagMessage;
    @FindBy(xpath = "//span[text()='Checkout']")
    private WebElement checkoutTitle;
    @FindBy(xpath = "//a[text()='Women']")
    private WebElement womenCategory;
    @FindBy(xpath = "//div[h6[text()='Categories']]//a[@href='/us/en/c/women/bottoms/jeans/cat6430042?pagetype=plp']")
    private WebElement womenJeansLocator;
    @FindBy(xpath ="//h1[contains(text(), \"Women's Jeans\")]")
    private WebElement womenJeansTitle;
    @FindBy(xpath =  "(//div[@class='results-list qa-results-list']//div[contains (@class, 'product-tile')])[2]")
    private WebElement firstItemFromCatalog;
    @FindBy(xpath = "//button[@data-test-btn = 'go2checkout']")
    private WebElement checkoutButton;
    @FindBy (xpath = "//div[@data-id='modalSidetrayQuickview']")
    private WebElement editItemModal;
    @FindBy (css = "div[data-test-product-prices] div.product-sale-price")
    private WebElement itemPrice;
    @FindBy (xpath = "//span[@data-test-cart-item-sale-price]")
    private WebElement itemPriceCard;
    @FindBy(xpath = "//div[@data-testid='row-shipping-value']")
    private WebElement shippingPrice;
    @FindBy(xpath = "//div[@data-testid='row-total-value']")
    private WebElement subTotalPrice;



    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
    Actions actions = new Actions(driver);

    @Step("click 'search' button")
    public void clickSearchButton() {
        searchButton.click();
    }

    @Step("wait until 'Search' field becomes clickable")
    public void waitForSearchField(){
        wait.until(ExpectedConditions.elementToBeClickable(searchInputField));
    }

    @Step("input text in 'search' field")
    public void sendTextInSearchField(String textToSearch) {
        searchInputField.sendKeys(textToSearch);
    }

    @Step("click 'submit' button from 'search' line")
    public void clickSubmitButton() {
        submitButton.click();
    }

    @Step("select first item from search results and click on it")
    public void selectFirstItemAndClickOnIt(String textToSearch){
        clickSearchButton();
        waitForSearchField();
        sendTextInSearchField(textToSearch);
        clickSubmitButton();
        wait.until(ExpectedConditions.elementToBeClickable(firstItemFromResults));
        firstItemFromResults.click();
    }

    @Step("get name of selected item in 'Added to bag' modal")
    public String getNameOfSelectedItem(){
        closeAdvertsIfExists();
        wait.until(ExpectedConditions.visibilityOf(nameOfSelectedItem));
        return nameOfSelectedItem.getText();
    }

    @Step("get card name of selected item")
    public String getCardNameOfSelectedItem(){
        return cardNameOfSelectedItem.getText();
    }

    @Step("get price of selected item")
    public String getPriceOfSelectedItem(){
        return priceOfSelectedItem.getText();
    }

    @Step("select first available size")
    public void selectFirstAvailableSize(){
        actions
                .scrollToElement(sizeContainer)
                .pause(Duration.ofSeconds(7))
                .perform();
        wait.until(ExpectedConditions.elementToBeClickable(sizeDropdown));
        sizeDropdown.click();
        wait.until(ExpectedConditions.visibilityOfAllElements(availableSizes));
        if (!availableSizes.isEmpty()) {
            availableSizes.get(0).click();
        } else {
            throw new RuntimeException("No available sizes found");
        }
    }

    @Step("add item to the bag")
    public void addItemToTheBag(){
        addToBagButton.click();
    }

    @Step("successful added to the bag message")
    public String getSuccessfulAddedToTheBagMessage(){
        return successfulAddedToTheBagMessage.getText();
    }

    @Step("view item in the bag")
    public void clickViewItemInTheBag(){
        viewBagButton.click();
    }

    @Step("shopping bag message")
    public String getShoppingBagMessage(){
        return shoppingBagMessage.getText();
    }

    @Step("q-ty of items")
    public String getQuantityOfItems(){
        return quantityOfItems.getText();
    }

    @Step("click on Edit button")
    public void clickEditButton(){
        actions
                .scrollToElement(editButton)
                .pause(Duration.ofSeconds(2))
                .perform();
        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        editButton.click();
    }

    @Step("check that 'Edit Item' modal is visible")
    public boolean isEditItemModalIsVisible(){
        return editItemModal.isDisplayed();
    }

    @Step("Move to 'Update Bag' button")
    public void movingToElementUpdateBagButton() {
        wait.until(ExpectedConditions.visibilityOf(updateBagButton));
        new Actions(driver)
                .moveToElement(updateBagButton)
                .pause(Duration.ofSeconds(2))
                .perform();
    }

    @Step("scroll and increase item qty")
    public void changeItemQty() {
        By addItem = By.xpath("//button[@aria-label='increase']");
        WebElement addItemButton = wait.until(ExpectedConditions.elementToBeClickable(addItem));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", addItemButton);
        js.executeScript("arguments[0].click();", addItemButton);
    }


    @Step("update bag")
    public void updateBag() {
        updateBagButton.click();
    }

    @Step("remove item from bag")
    public void removeItemFromBag(){
        actions
                .scrollToElement(removeButton)
                .pause(Duration.ofSeconds(2))
                .perform();
        wait.until(ExpectedConditions.elementToBeClickable(removeButton));
        removeButton.click();
    }


    @Step("get bag empty message")
    public String getBagEmptyMessage() {
        wait.until(ExpectedConditions.visibilityOf(emptyBagMessage));
        return emptyBagMessage.getText();
    }

    @Step("click 'checkout'")
    public void clickCheckoutButton(){
        actions
                .scrollToElement(checkoutButton)
                .pause(Duration.ofSeconds(2))
                .perform();
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        checkoutButton.click();
    }

    @Step ("get 'checkout' title")
    public String getCheckoutTitle() {
        wait.until(ExpectedConditions.visibilityOf(checkoutTitle));
        return checkoutTitle.getText();
    }

    @Step("move to 'women' category")
    public void moveToWomenCategory() {
        actions.moveToElement(womenCategory).pause(Duration.ofSeconds(2)).perform();
    }

    @Step("select 'jeans' in Women category")
    public void selectJeansInWomenCategory() {
        womenJeansLocator.click();
    }

    @Step("get text for Women jeans title")
    public String getWomenPageTitle() {
        wait.until(ExpectedConditions.visibilityOf(womenJeansTitle));
        return womenJeansTitle.getText();
    }

    @Step("select first available item from catalog results")
    public void selectFirstItemFromCatalogAndClickOnIt(){
        actions
                .scrollToElement(firstItemFromCatalog)
                .pause(Duration.ofSeconds(4))
                .perform();
        wait.until(ExpectedConditions.elementToBeClickable(firstItemFromCatalog));
        firstItemFromCatalog.click();
    }

    @Step("get item price")
    public double getItemPrice(){
    String itemPriceText = wait.until(ExpectedConditions.visibilityOf(itemPrice))
                .getText()
                .replace("Now", "")
                .replace("$", "")
                .replace(",", "")
                .trim();
        return Double.parseDouble(itemPriceText);
        }

    @Step("get item price in the bag")
    public double getCardItemPrice() {
        String itemPriceCardText = wait.until(ExpectedConditions.visibilityOf(itemPriceCard))
                .getText()
                .replace("$", "")
                .replace(",", "")
                .trim();
        return Double.parseDouble(itemPriceCardText);
    }

    @Step("get shipping price in the bag")
    public double getShippingPrice() {
        String priceText = wait.until(ExpectedConditions.visibilityOf(shippingPrice)).getText();

        if (priceText.equals("Free")) {
            return 0;
        } else {
            return Double.parseDouble(priceText
                    .replace("$", "")
                    .replace(",", "")
                    .trim());
        }
    }

    @Step("Get the total price in cart")
    public double getSubTotalPrice() {
        String priceText = wait.until(ExpectedConditions.visibilityOf(subTotalPrice))
                .getText()
                .replace("$", "")
                .replace(",", "")
                .trim();
        return Double.parseDouble(priceText);
    }

}
