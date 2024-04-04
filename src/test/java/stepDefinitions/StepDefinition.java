package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObject.*;
import testComponents.BaseTest;

import java.io.IOException;

public class StepDefinition extends BaseTest {
    public LandingPage landingPage;

    public ProductCatalog productCatalog;

    public ConfirmationPage confirmationPage;

    @Given("I landed on Landing page")
    public void i_landed_on_landing_page() throws IOException {
        landingPage = launchApplication();
    }

    @Given("^Logged in with username (.+) and password (.+)$")
    public void logged_in_with_username_and_password(String username, String password) {
        productCatalog = landingPage.loginApplication(username, password);
    }

    @When("^I add product (.+) to Cart$")
    public void i_add_product_to_cart(String product) {
        productCatalog.addProductToCart(product);
    }

    @And("^Checkout (.+) and submit the order$")
    public void checkout_and_submit_order(String product) {
        CartPage cartPage = productCatalog.goToCartPage();
        boolean isMatch = cartPage.verifyProductDisplay(product);
        Assert.assertTrue(isMatch);

        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("india");

        confirmationPage = checkoutPage.placeOrder();
    }

    @Then("{string} message is displayed on Confirmation page")
    public void message_is_displayed_on_confirmation_page(String message) {
        String confirmationMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmationMessage.equalsIgnoreCase(message));
        driver.quit();
    }

    @Then("{string} message is displayed")
    public void message_is_displayed(String message) {
        Assert.assertEquals(landingPage.getErrorMessage(), message);
        driver.quit();
    }
}
