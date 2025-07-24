package pages;


import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    public static final String BASE_URL_UI = "https://www.ae.com/us/en/";
    public static final String WOMEN_JEANS_URL = BASE_URL_UI + "c/women/bottoms/jeans/cat6430042?pagetype=plp";
    public static final String CREATE_ACCOUNT_URL = BASE_URL_UI + "myaccount/create-account";
    public static final String SUCCESSFUL_CREATED_ACCOUNT_URL = BASE_URL_UI + "myaccount/real-rewards/account-summary";
    public static final String SHIPPING_BAG_URL = BASE_URL_UI + "cart";
    public static final String CHECKOUT_URL = BASE_URL_UI + "checkout";

    @FindBy(xpath = "//a[@data-testid='ae-logo']")
    private WebElement title;
    @FindBy(css = "button[data-test-btn='close']")
    private WebElement closeButton;
    @FindBy(css = "div[data-id='modalFlyoutOnboarding'][data-modal-show='true']")
    private WebElement modalLocator;




    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    @Step("Get current url")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("open home page")
    public void openHomePage(){
        driver.get(BASE_URL_UI);
    }

    @Step("get title")
    public String getTitle() {
        return title.getText();
    }

    @Step ("close adverts window")
    public void closeAdvertsIfExists(){
        try {
            wait.until(ExpectedConditions.visibilityOf(modalLocator));

            if (modalLocator.isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(closeButton));
                closeButton.click();
                System.out.println("Modal was closed..");
            }
        } catch (Exception e) {
            System.out.println("Unexpected error while trying to close modal: " + e.getMessage());
        }
    }

}
