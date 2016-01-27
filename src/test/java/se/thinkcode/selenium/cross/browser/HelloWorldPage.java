package se.thinkcode.selenium.cross.browser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HelloWorldPage {
    private WebDriver browser;

    public HelloWorldPage(WebDriver browser) {
        this.browser = browser;

        String page = browser.getCurrentUrl() + "helloWorld";
        browser.get(page);

        String expectedTitle = "Hello, world";
        String actualTitle = browser.getTitle();

        assertThat(actualTitle, is(expectedTitle));
    }

    public String getHeadline() {
        pause();

        WebElement headLine = browser.findElement(By.id("headline"));

        return headLine.getText();
    }

    private void pause() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
