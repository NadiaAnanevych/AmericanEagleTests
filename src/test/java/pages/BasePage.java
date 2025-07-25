package pages;


import io.qameta.allure.Step;
import org.openqa.selenium.*;
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
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            WebElement shadowHost = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("div.bloomreach-weblayer")));

            wait.until(ExpectedConditions.visibilityOf(shadowHost));

            SearchContext shadowRoot = shadowHost.getShadowRoot();
            WebElement promoBox = shadowRoot.findElement(By.cssSelector(".weblayer--box-promotion-1"));

            WebElement closeButton = promoBox.findElement(By.cssSelector("button.close"));
            wait.until(ExpectedConditions.elementToBeClickable(closeButton));
            closeButton.click();
            wait.until(ExpectedConditions.invisibilityOf(shadowHost));
            System.out.println("Modal was found and closed.");
        } catch (TimeoutException e) {
            System.out.println("Modal layer not present or already closed.");
        } catch (Exception e) {
            System.err.println("Unexpected error while trying to close modal: " + e.getMessage());
        }
    }

    public void safeClick(WebElement element) {
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                return;
            } catch (ElementClickInterceptedException e) {
                System.out.println("Click intercepted, retrying after modal close...");
                closeAdvertsIfExists();
            } catch (Exception e) {
                System.err.println("Click failed: " + e.getMessage());
                throw e;
            }
        }
        throw new RuntimeException("Unable to click after multiple attempts due to modal interference.");
    }

}
