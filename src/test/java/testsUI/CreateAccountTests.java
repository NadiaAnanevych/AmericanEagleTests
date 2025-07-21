package testsUI;


import configs.TestPropertiesConfig;
import org.aeonbits.owner.ConfigFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CreateAccountPage;
import pages.HomePage;
import utils.AccountTestDataGeneration;
import utils.TestUser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pages.BasePage.*;

public class CreateAccountTests extends BaseTest{
    CreateAccountPage createAccountPage;
    TestUser user = AccountTestDataGeneration.generateTestUser();
    TestPropertiesConfig config = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());


    @BeforeEach
    void setupPage() {
        createAccountPage = new CreateAccountPage(driver);
        createAccountPage.openHomePage();
    }



    @DisplayName("Check 'Create an account' url and title text")
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("positive")})
    @Test
    void checkCreateAccountPageUrlAndTitleTest() {
        createAccountPage.clickAccountIcon();
        createAccountPage.clickCreateAccountButton();

        assertAll(
                () -> assertEquals(CREATE_ACCOUNT_URL, createAccountPage.getCurrentUrl()),
                () -> assertEquals("Create an Account", createAccountPage.getCreateAccountTitleText())
        );
;
    }

    @DisplayName("create an account filling in all necessary fields")
    @Tags({@Tag("P0"), @Tag("smoke"), @Tag("positive")})
    @Test void successfulAccountCreationTest() {
        String monthOfBirth = "January";
        String dayOfBirth = "10";
        TestUser user = AccountTestDataGeneration.generateTestUser();
        createAccountPage.clickAccountIcon();
        createAccountPage.clickCreateAccountButton();
        createAccountPage.fillInCreateAccountFormWithValidData(user, monthOfBirth, dayOfBirth);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(createAccountPage.submitAccountButtonIsEnabled())
                .isTrue();
        softly.assertThat(createAccountPage.isTermsCheckboxSelected())
                .isTrue();

        createAccountPage.waitForSuccessfulUrlAndMessage(); //temporary doesn't work - new "Account created!" doesn't open

        assertAll(
                () -> assertEquals(SUCCESSFUL_CREATED_ACCOUNT_URL, createAccountPage.getCurrentUrl()),
                () -> assertEquals("Account created!", createAccountPage.getSuccessfullyCreatedAccountTitleText())
    );
    }

    @DisplayName("trying to create an account with invalid email")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test void createAccountWithInvalidEmailTest() {
        String monthOfBirth = "January";
        String dayOfBirth = "10";
        String invalidEmail = "testcom.ua";
        String password = user.getPassword();
        String expectedErrorMessage = "Please enter a valid email address.";

        createAccountPage.clickAccountIcon();
        createAccountPage.clickCreateAccountButton();
        createAccountPage.fillInCreateAccountFormWithInvalidData(user, invalidEmail, password, monthOfBirth, dayOfBirth);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(createAccountPage.submitAccountButtonIsEnabled())
                .isFalse();
        softly.assertThat(expectedErrorMessage)
                .isEqualTo(createAccountPage.getErrorEmailText());
        softly.assertAll();
    }

    @DisplayName("trying to create an account with invalid password")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void createAccountWithInvalidPassword() {
        String monthOfBirth = "January";
        String dayOfBirth = "10";
        String email = user.getEmail();
        String incorrectPassword = "12345";
        String expectedErrorMessage =
                "Please enter a password that contains letters + numbers and is 8-25 characters long.";

        createAccountPage.clickAccountIcon();
        createAccountPage.clickCreateAccountButton();
        createAccountPage.fillInCreateAccountFormWithInvalidData(user, email, incorrectPassword, monthOfBirth, dayOfBirth);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(createAccountPage.submitAccountButtonIsEnabled())
                .isFalse();
        softly.assertThat(expectedErrorMessage)
                .isEqualTo(createAccountPage.getErrorsPasswordText());
        softly.assertAll();
    }
    @DisplayName("trying to create an account without birth date")
    @Tags({@Tag("P0"), @Tag("negative")})
    @Test
    void createAccountWithoutBirthDate() {
        String email = user.getEmail();
        String password = user.getPassword();

        createAccountPage.clickAccountIcon();
        createAccountPage.clickCreateAccountButton();
        createAccountPage.fillInCreateAccountFormWithoutBirthDate(user, email, password);

        assertThat(createAccountPage.submitAccountButtonIsEnabled())
                .isFalse();

    }

    @Test
    void signInPageTest() {
        createAccountPage.clickAccountIcon();
        createAccountPage.clickSignInButton();
        createAccountPage.inputEmailField(config.getEmail());
        createAccountPage.inputPasswordField(config.getPassword());
        createAccountPage.submitSignInButtonClick();

        assertThat("test's Account")
               .isEqualTo(createAccountPage.getSuccessfulEnteredAccountText());
    }

}
