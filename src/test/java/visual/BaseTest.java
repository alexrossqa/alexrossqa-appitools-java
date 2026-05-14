package visual;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseTest {

    protected static ClassicRunner runner;
    protected static BatchInfo batch;
    protected static Configuration eyesConfig;

    protected WebDriver driver;
    protected Eyes eyes;
    protected Properties config;

    @BeforeSuite
    public static void setUpSuite() {
        runner = new ClassicRunner();
        batch = new BatchInfo("Plex Visual Tests");

        eyesConfig = new Configuration();
        eyesConfig.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        eyesConfig.setBatch(batch);
        eyesConfig.setAppName("Plex");
        eyesConfig.setViewportSize(new RectangleSize(1280, 800));
    }

    @BeforeMethod
    public void setUp() throws IOException {
        config = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            config.load(in);
        }

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        authenticatePlex();

        eyes = new Eyes(runner);
        eyes.setConfiguration(eyesConfig);
    }

    @AfterMethod
    public void tearDown() {
        if (eyes != null) eyes.abortIfNotClosed();
        if (driver != null) driver.quit();
    }

    @AfterSuite
    public static void tearDownSuite() {
        TestResultsSummary results = runner.getAllTestResults(false);
        System.out.println(results);
    }

    // Injects the Plex token into localStorage so Selenium's fresh Chrome session is authenticated.
    private void authenticatePlex() {
        String plexUrl = config.getProperty("plex.url");
        String token = config.getProperty("plex.token");
        driver.get(plexUrl + "/web/index.html");
        ((JavascriptExecutor) driver).executeScript(
            "localStorage.setItem('myPlexAccessToken', arguments[0]);", token
        );
        driver.navigate().refresh();
    }
}
