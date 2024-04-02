package pageObject;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends AbstractComponent {
    WebDriver driver;
    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // PageFactory
    @FindBy(id = "userEmail")
    WebElement userEmail;

    @FindBy(id = "userPassword")
    WebElement userPassword;

    @FindBy(id = "login")
    WebElement submitButton;

    @FindBy(css = "[class*='flyInOut']")
    WebElement errorMessage;

    public ProductCatalog loginApplication(String email, String password) {
        userEmail.sendKeys(email);
        userPassword.sendKeys(password);
        submitButton.click();

        ProductCatalog productCatalog = new ProductCatalog(driver);
        return productCatalog;
    }

    public void goToLandingPage() {
        driver.get("https://rahulshettyacademy.com/client");
    }

    public String getErrorMessage() {
        waitElementToAppear(errorMessage);
        return errorMessage.getText();
    }
}
