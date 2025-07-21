package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestUser;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateAccountPage extends BasePage {

    public CreateAccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "svg[data-testid='icon-account']")
    private WebElement accountIcon;
    @FindBy(className = "modal-title")
    private  WebElement modalAccount;
    @FindBy(xpath = "//a[@data-test='register-button']")
    private WebElement createAccountButton;
    @FindBy(xpath = "//h1[text()='Create an Account']")
    private  WebElement createAccountTitle;
    @FindBy(xpath = "//input[@placeholder='Email']")
    private  WebElement inputEmail;
    @FindBy(name = "firstname")
    private  WebElement inputFirstName;
    @FindBy(name = "lastname")
    private  WebElement inputLastName;
    @FindBy(xpath = "//input[@placeholder='Password']")
    private  WebElement inputPassword;
    @FindBy(xpath = "//input[@name='confirm_password']")
    private  WebElement inputConfirmPassword;
    @FindBy(name = "postalCode")
    private  WebElement inputZipCode;
    @FindBy(xpath = "//select[@name='month']")
    private WebElement selectMonth;
    @FindBy(xpath = "//select[@name='day']")
    private WebElement selectDay;
    @FindBy(name = "acceptTerms")
    private WebElement checkboxAcceptTerms;
    @FindBy(xpath = "//button[@name='submit']")
    private WebElement submitAccountButton;
    @FindBy(xpath = "//div[@class='alert-content']")
    private WebElement successfullyCreatedAccountTitle;
    @FindBy(xpath = "//div[contains (@data-test-form-error, 'error.account.password.invalid')]")
    private WebElement errorPassword;
    @FindBy(xpath = "//div[contains (@data-test-form-error, 'error.account.email.invalid')]")
    private WebElement errorEmail;
    @FindBy(name = "signin")
    private WebElement signInButton;
    @FindBy(xpath = "//form[@name='login']//button[@data-test-btn='submit']")
    private WebElement submitSignInButton;
    @FindBy(xpath = "//h2[@class='modal-title' and text()=\"test's Account\"]")
    private WebElement successfulLoginText;





    @Step("click on Account icon")
    public void clickAccountIcon() {
        accountIcon.click();
    }
    @Step("check 'Create Account' button is displayed")
    public Boolean createAccountButtonIsDisplayed() {
        return createAccountButton.isDisplayed();
    }

    @Step("get text in modal menu 'Account'")
    public String getAccountTitleText() {
        return modalAccount.getText();
    }

    @Step("click on 'Create Account' button")
    public void clickCreateAccountButton(){
        createAccountButton.click();
    }

    @Step("get title text for 'Create an account'")
    public String getCreateAccountTitleText() {
        return createAccountTitle.getText();
    }

    @Step("enter email")
    public void inputEmailField(String email) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        wait.until(ExpectedConditions.visibilityOf(inputEmail)).sendKeys(email);
    }

    @Step("enter first name")
    public void inputFirstNameField(String firstName) {
        inputFirstName.sendKeys(firstName);
    }
    @Step("enter last name")
    public void inputLastNameField(String lastName) {
        inputLastName.sendKeys(lastName);
    }

    @Step("enter password")
    public void inputPasswordField(String password) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", inputPassword);
        inputPassword.sendKeys(password);
    }

    @Step("confirm password")
    public void inputConfirmPasswordField(String password) {
        inputConfirmPassword.sendKeys(password);
    }

    @Step("enter Zip code")
    public void inputZipCodeField(String postalCode) {
        inputZipCode.sendKeys(postalCode);
    }
    @Step("choose month from dropdown")
    public void selectMonthFromDropdown(String visibleText) {
        Select select = new Select(selectMonth);
        select.selectByVisibleText(visibleText);
        assertEquals(visibleText, select.getFirstSelectedOption().getText());
    }

    @Step("choose day from dropdown")
    public void selectDayFromDropdown(String value) {
        Select select = new Select(selectDay);
        select.selectByValue(value);
        assertEquals(value, select.getFirstSelectedOption().getDomProperty("value"));
    }

    @Step("Click checkbox 'I Accept'")
    public void acceptTermsAndConditions() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", checkboxAcceptTerms);
        js.executeScript("arguments[0].click();", checkboxAcceptTerms);
    }

    @Step("wait for 'Create Account' button to be clickable")
    public void waitForCreateAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(submitAccountButton));
        //wait.until(driver -> submitAccountButton.isEnabled());
    }



    @Step("click on 'Create Account' button")
    public void scrollAndClickOnSubmitButton() {
        wait.until(ExpectedConditions.visibilityOf(submitAccountButton));

        new Actions(driver)
                .scrollToElement(submitAccountButton)
                .moveToElement(submitAccountButton)
                .pause(Duration.ofSeconds(2))
                .click()
                .perform();
    }


    @Step("fill in 'Create account' form with valid required data")
    public void fillInCreateAccountFormWithValidData(TestUser user, String monthOfBirth, String dayOfBirth) {
        inputEmailField(user.getEmail());
        inputFirstNameField(user.getFirstName());
        inputLastNameField(user.getLastName());
        inputPasswordField(user.getPassword());
        inputConfirmPasswordField(user.getPassword());
        inputZipCodeField(user.getZipCode());
        selectMonthFromDropdown(monthOfBirth);
        selectDayFromDropdown(dayOfBirth);
        acceptTermsAndConditions();
        waitForCreateAccountButton();
        scrollAndClickOnSubmitButton();

    }
    @Step("fill in 'Create account' form with valid required data")
    public void fillInCreateAccountFormWithInvalidData(TestUser user, String email, String password, String monthOfBirth, String dayOfBirth) {
        inputEmailField(email);
        inputFirstNameField(user.getFirstName());
        inputLastNameField(user.getLastName());
        inputPasswordField(password);
        inputConfirmPasswordField(password);
        inputZipCodeField(user.getZipCode());
        selectMonthFromDropdown(monthOfBirth);
        selectDayFromDropdown(dayOfBirth);
        acceptTermsAndConditions();
    }
    @Step("fill in 'Create account' form with valid required data")
    public void fillInCreateAccountFormWithoutBirthDate(TestUser user, String email, String password) {
        inputEmailField(email);
        inputFirstNameField(user.getFirstName());
        inputLastNameField(user.getLastName());
        inputPasswordField(password);
        inputConfirmPasswordField(password);
        inputZipCodeField(user.getZipCode());
        acceptTermsAndConditions();
    }

    @Step("wait for opening created account page and successful created message")
    public void waitForSuccessfulUrlAndMessage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(SUCCESSFUL_CREATED_ACCOUNT_URL));
        wait.until(ExpectedConditions.visibilityOf(successfullyCreatedAccountTitle));

    }

    @Step("get title text for created account")
    public String getSuccessfullyCreatedAccountTitleText() {
        return successfullyCreatedAccountTitle.getText();
    }

    @Step("get text from error caused by using invalid email")
    public String getErrorEmailText() {
        return errorEmail.getText();
    }

    @Step("get error's text after using invalid password")
    public String getErrorsPasswordText() {
        return errorPassword.getText();
    }

    public boolean isTermsCheckboxSelected() {
        return checkboxAcceptTerms.isSelected();
    }

    @Step("Check 'Create Account' button is enabled")
    public Boolean submitAccountButtonIsEnabled() {
        return submitAccountButton.isEnabled();
    }

    @Step("Click submit button")
    public void clickSubmitButton() {
        submitAccountButton.click();
    }
    @Step("Click 'Sign In' button")
    public void clickSignInButton() {
        signInButton.click();
    }
    @Step("Click on 'Sign In' button")
    public void submitSignInButtonClick() {
        submitSignInButton.click();
    }

    @Step("Check successful entered to account")
    public String getSuccessfulEnteredAccountText() {
        return successfulLoginText.getText();
    }


}
