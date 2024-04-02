import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.CartPage;
import pageObject.ProductCatalog;
import testComponents.BaseTest;
import testComponents.Retry;

public class ErrorValidationsTest extends BaseTest {
    @Test(groups = {"ErrorHandling"}, retryAnalyzer = Retry.class)
    public void loginApplicationWithInvalidCredentialsTest() {
        String invalidUserEmail = "invalid@qwerty.com";
        String invalidUserPassword = "invalid";

        landingPage.loginApplication(invalidUserEmail, invalidUserPassword);
        Assert.assertEquals(landingPage.getErrorMessage(), "BBaaadddIncorrect email or password.");
    }

    @Test
    public void productErrorValidationTest() {
        String userEmail = "werewrwer@rsg.drg";
        String userPassword = "sefeFFS4433###";
        String productName = "ZARA COAT 3";

        ProductCatalog productCatalog = landingPage.loginApplication(userEmail, userPassword);
        productCatalog.addProductToCart(productName);

        CartPage cartPage = productCatalog.goToCartPage();
        boolean isMatch = cartPage.verifyProductDisplay("invalid product name");
        Assert.assertFalse(isMatch);
    }
}
