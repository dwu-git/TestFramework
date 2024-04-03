package testComponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pageObject.LandingPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    public LandingPage landingPage;
    public WebDriver initializeDriver() throws IOException {
        // Properties class
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/GlobalData.properties");
        properties.load(fileInputStream);
        // Checking if System variable(Maven) exists, if not - takes variable from properties(GlobalData.properties)
        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : properties.getProperty("browser");

        if (browserName.contains("chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            WebDriverManager.chromedriver().setup();

            if (browserName.contains("headless"))
                chromeOptions.addArguments("headless");

            driver = new ChromeDriver(chromeOptions);
            driver.manage().window().setSize(new Dimension(1440, 900)); //full screen
        }
        else if (browserName.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        else if (browserName.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir") + "/reports/" + testCaseName + ".png");
        FileUtils.copyFile(source, file);
        return System.getProperty("user.dir") + "/reports/" + testCaseName + ".png";
    }

    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        //System.getProperty("user.dir") - enter the path dynamically
        String jsonContent = FileUtils.readFileToString(new File(filePath),
                StandardCharsets.UTF_8);

        //String to HashMap Jackson Databind
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<>() {
        });
        return data;
    }

    @BeforeMethod(alwaysRun = true)
    public LandingPage launchApplication() throws IOException {
        driver = initializeDriver();
        landingPage = new LandingPage(driver);
        landingPage.goToLandingPage();
        return landingPage;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}
