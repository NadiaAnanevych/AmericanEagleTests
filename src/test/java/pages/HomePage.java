package pages;


import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage{
    @FindBy(xpath = "//a[@data-testid='aerie-logo']")
    private WebElement logo;
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
    @FindBy(xpath = "//h1[@class='qa-search-match-error _search-match-error_10fauf']")
    private WebElement failedSearchMessage;
    @FindBy(css = "svg[data-testid='icon-account']")
    private WebElement accountIcon;
    @FindBy(className = "modal-title")
    private  WebElement modalAccount;
    @FindBy(name = "signin")
    private  WebElement signinButton;
    @FindBy(xpath = "//a[@data-test='register-button']")
    private WebElement createAccountButton;
    @FindBy(css = "svg[data-testid='icon-favorites']")
    private WebElement favoritesIcon;
    @FindBy(xpath = "//h1[text()='Favorites']")
    private WebElement favoritesText;
    @FindBy(xpath = "//a[contains(@class, 'bag-button')]")
    private  WebElement bagIcon;
    @FindBy(xpath = "//h1[text()='Shopping Bag']")
    private WebElement bagText;
    @FindBy(xpath = "//li[@data-test='top-link-wrapper']//a[contains(text(),'Women')]")
    private WebElement womenCategory;
    @FindBy(xpath = "//div[h6[text()='Categories']]//a[@href='/us/en/c/women/bottoms/jeans/cat6430042?pagetype=plp']")
    private WebElement womenJeansLocator;
    @FindBy(xpath ="//h1[contains(text(), \"Women's Jeans\")]")
    private WebElement womenJeansTitle;


    public HomePage(WebDriver driver) {
        super(driver);

    }


    @Step("check logo is displayed")
    public Boolean logoIsDisplayed() {
        return logo.isDisplayed();
    }

    @Step("check 'search' button is displayed")
    public Boolean searchButtonIsDisplayed() {
        return searchButton.isDisplayed();
    }

    @Step("click 'search' button")
    public void clickSearchButton() {
       searchButton.click();
    }

    @Step("check if 'Search' modal is visible")
    public boolean isSearchModalVisible(){
        return searchModal.isDisplayed();
    }

    @Step("wait until 'Search' field becomes clickable")
    public void waitForSearchField(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
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

    @Step("get text of the subtitle")
    public String getSubtitleAfterSearchText(){
        return subtitleAfterSearch.getText();
    }
    @Step("get text of the message that appears if item wasn't found")
    public String getTextOfErrorSearchMessage(){
        return failedSearchMessage.getText();
    }

    @Step("check account icon is displayed")
    public Boolean accountIconIsDisplayed(){
        return accountIcon.isDisplayed();
    }

    @Step("click on Account icon")
    public void clickAccountIcon() {
        accountIcon.click();
    }

    @Step("check 'Sign in' button is displayed")
    public Boolean signInButtonIsDisplayed() {
        return signinButton.isDisplayed();
    }

    @Step("check 'Create Account' button is displayed")
    public Boolean createAccountButtonIsDisplayed() {
        return createAccountButton.isDisplayed();
    }

    @Step("Get text in modal menu 'Account'")
    public String getAccountTitleText() {
        return modalAccount.getText();
    }

    @Step("Check 'Favorites' icon is displayed")
    public Boolean favoritesIconIsDisplayed() {
        return favoritesIcon.isDisplayed();
    }

    @Step("Click 'Favorites' icon")
    public void clickFavoritesIcon() {
        favoritesIcon.click();
    }

    @Step("Get text in menu 'Favorites'")
    public String getFavoritesTitleText() {
        return favoritesText.getText();
    }

    @Step("Check 'Bag' icon is displayed")
    public Boolean bagIconIsDisplayed() {
        return bagIcon.isDisplayed();
    }

    @Step("Click 'Bag' icon")
    public void clickBagIcon() {
        bagIcon.click();
    }

    @Step("Get text in menu 'Shopping bag'")
    public String getBagTitleText() {
        return bagText.getText();
    }

    @Step("move to 'women' category")
    public void moveToWomenCategory() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", womenCategory);
        ((JavascriptExecutor) driver).executeScript(
                "var evObj = document.createEvent('MouseEvents');" +
                        "evObj.initEvent('mouseover', true, true);" +
                        "arguments[0].dispatchEvent(evObj);", womenCategory);
    }

    /*@Step("move to 'women' category")
    public void moveToWomenCategory() {
            Actions actions = new Actions(driver);
            actions.moveToElement(womenCategory).pause(Duration.ofSeconds(2)).perform();
        }*/

    @Step("select 'jeans' in Women category")
    public void selectJeansInWomenCategory() {
        womenJeansLocator.click();
    }

    @Step("get text for Women jeans title")
    public String getWomenPageTitle() {
        wait.until(ExpectedConditions.visibilityOf(womenJeansTitle));
        return womenJeansTitle.getText();
    }

}
