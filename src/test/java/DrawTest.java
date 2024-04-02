import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pageObject.LandingPage;

import java.time.Duration;
import java.util.List;

public class DrawTest {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/client");

        String productName = "ZARA COAT 3";

        LandingPage landingPage = new LandingPage(driver);
        driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys("easdfdssdfsSSW@drgerh34.rsrg");
        driver.findElement(By.xpath("//input[@id='userPassword']")).sendKeys("easdfdssdfsSSW@drgerh34.rsrg");
        driver.findElement(By.xpath("//input[@id='login']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
        WebElement prod = products.stream()
                .filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName))
                .findFirst()
                .orElse(null);
        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        List<WebElement> cartProducts = driver.findElements(By.xpath("//*[@class='cartSection']/h3"));
        boolean isMatch = cartProducts.stream()
                .anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(isMatch);

        driver.findElement(By.xpath("//*[@class='totalRow']/button")).click();

        Actions actions = new Actions(driver);
        actions.sendKeys(driver.findElement(By.cssSelector(".form-group input")), "india").build().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ta-item list-group-item ng-star-inserted']")));

        driver.findElement(By.xpath("//*[@class='ta-item list-group-item ng-star-inserted'][2]")).click();
        driver.findElement(By.xpath("//*[@class='btnn action__submit ng-star-inserted']")).click();

        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        driver.quit();
    }
}
