package pageObject;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AbstractComponent {
    @FindBy(xpath = "//*[@class='cartSection']/h3")
    private List<WebElement> cartProducts;

    @FindBy(xpath = "//*[@class='totalRow']/button")
    private WebElement checkoutButton;

    private WebDriver driver;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean verifyProductDisplay(String productName) {
        boolean isMatch = cartProducts.stream()
                .anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
        return isMatch;
    }

    public CheckoutPage goToCheckout() {
        checkoutButton.click();
        return new CheckoutPage(driver);
    }
}
