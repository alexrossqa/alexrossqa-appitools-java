package visual;

import com.applitools.eyes.selenium.fluent.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class PlexVisualTest extends BaseTest {

    @Test(description = "Baseline: login page renders correctly")
    public void loginPageBaseline() {
        driver.get("https://demo.applitools.com");
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.presenceOfElementLocated(By.id("log-in")));

        eyes.open(driver, "Demo App", "Login Page - Baseline");
        eyes.check(Target.window().fully().withName("Login page"));
        eyes.closeAsync();
    }

    @Test(description = "Regression: visual diff detected after button colour changes")
    public void loginPageRegression() {
        driver.get("https://demo.applitools.com");
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.presenceOfElementLocated(By.id("log-in")));

        ((JavascriptExecutor) driver).executeScript(
            "document.getElementById('log-in').style.backgroundColor = '#FF0000';" +
            "document.getElementById('log-in').style.color = '#FFFFFF';"
        );

        eyes.open(driver, "Demo App", "Login Page - Baseline");
        eyes.check(Target.window().fully().withName("Login page after regression"));
        eyes.closeAsync();
    }
}
