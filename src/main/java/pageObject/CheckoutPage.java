package pageObject;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends AbstractComponent {
    @FindBy(css = ".form-group input")
    private WebElement countryField;

    @FindBy(xpath = "//*[@class='ta-item list-group-item ng-star-inserted'][2]")
    private WebElement indiaOption;

    @FindBy(xpath = "//*[@class='btnn action__submit ng-star-inserted']")
    private WebElement placeOrderButton;

    private By dropdownWithCountries = By.xpath("//*[@class='ta-item list-group-item ng-star-inserted']");

    private WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectCountry(String countryName) {
        Actions actions = new Actions(driver);
        actions.sendKeys(countryField, countryName).build().perform();

        waitElementToAppear(dropdownWithCountries);

        indiaOption.click();
    }

    public ConfirmationPage placeOrder() {
        placeOrderButton.click();
        return new ConfirmationPage(driver);
    }

}
