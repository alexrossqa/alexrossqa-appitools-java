package visual;

import com.applitools.eyes.selenium.fluent.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class PlexVisualTest extends BaseTest {

    /**
     * Positive test: establishes the Applitools baseline on first run.
     * Subsequent runs compare against this snapshot — any unintended change will be flagged.
     */
    @Test(description = "Baseline: film detail page renders correctly")
    public void filmDetailBaseline() {
        navigateToFilm();

        eyes.open(driver, "Plex", "Film Detail - Baseline");
        eyes.check(Target.window().fully().withName("Film detail page"));
        eyes.closeAsync();
    }

    /**
     * Negative test: replaces the film poster via JavaScript to simulate a visual regression.
     * Applitools Visual AI compares against the baseline and flags the diff.
     */
    @Test(description = "Regression: visual diff detected after poster is replaced")
    public void filmDetailPosterChanged() {
        navigateToFilm();

        // Swap the poster src — simulates a broken image path or unintended asset change
        ((JavascriptExecutor) driver).executeScript(
            "var posters = document.querySelectorAll('img[src*=\"/library/metadata\"]');" +
            "if (posters.length > 0) {" +
            "  posters[0].src = 'https://placehold.co/300x450/CC0000/FFFFFF?text=BROKEN';" +
            "}"
        );

        eyes.open(driver, "Plex", "Film Detail - Poster Changed");
        eyes.check(Target.window().fully().withName("Film detail after poster swap"));
        eyes.closeAsync();
    }

    private void navigateToFilm() {
        driver.get(config.getProperty("plex.film.url"));
        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("img[src*='/library/metadata']")
            ));
    }
}
