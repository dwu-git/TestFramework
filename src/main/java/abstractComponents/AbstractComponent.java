package abstractComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.CartPage;
import pageObject.OrderPage;

import java.time.Duration;

public class AbstractComponent {
    @FindBy(css = "[routerlink*='cart']")
    private WebElement cartHeaderButton;

    @FindBy(css = "[routerlink*='myorders']")
    private WebElement orderHeaderButton;

    WebDriver driver;

    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitElementToAppear(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    public void waitElementToAppear(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitElementToDisappear(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOf(webElement));
    }

    public CartPage goToCartPage() {
        cartHeaderButton.click();
        return new CartPage(driver);
    }

    public OrderPage goToOrderPage() {
        orderHeaderButton.click();
        return new OrderPage(driver);
    }
}
