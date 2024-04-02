import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObject.*;
import testComponents.BaseTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PlaceOrderTest extends BaseTest {
    private String userEmail = "easdfdssdfsSSW@drgerh34.rsrg";
    private String userPassword = "easdfdssdfsSSW@drgerh34.rsrg";
    private String productName = "ZARA COAT 3";
    private String country = "India";

    @Test(dataProvider = "getData", groups = {"Purchase"})
    public void placeOrderTest(HashMap<String, String> input) {
        ProductCatalog productCatalog = landingPage.loginApplication(input.get("userEmail"), input.get("userPassword"));
        productCatalog.addProductToCart(input.get("productName"));

        CartPage cartPage = productCatalog.goToCartPage();
        boolean isMatch = cartPage.verifyProductDisplay(input.get("productName"));
        Assert.assertTrue(isMatch);

        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry(country);

        ConfirmationPage confirmationPage = checkoutPage.placeOrder();
        String confirmationMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    }

    @Test(dependsOnMethods = {"placeOrderTest"})
    public void orderHistoryTest() {
        ProductCatalog productCatalog = landingPage.loginApplication(userEmail, userPassword);
        OrderPage orderPage = productCatalog.goToOrderPage();
        Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "/src/test/java/data/Purchase.json");
        return new Object[][] { {data.get(0)}, {data.get(1)} };
    }

//    @DataProvider
//    public Object[][] getData() {
//        return new Object[][] { {userEmail, userPassword, productName}, {"werewrwer@rsg.drg", "sefeFFS4433###", "ZARA COAT 3"} };
//    }

//        HashMap<String, String> map = new HashMap<>();
//        map.put("userEmail", userEmail);
//        map.put("userPassword", userPassword);
//        map.put("productName", productName);
//
//        HashMap<String, String> map1 = new HashMap<>();
//        map1.put("userEmail", "werewrwer@rsg.drg");
//        map1.put("userPassword", "sefeFFS4433###");
//        map1.put("productName", "ZARA COAT 3");
}
