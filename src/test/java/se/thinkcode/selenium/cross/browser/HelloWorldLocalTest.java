package se.thinkcode.selenium.cross.browser;

import com.saucelabs.common.SauceOnDemandAuthentication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class HelloWorldLocalTest {
    private WebDriver browser;

    public HelloWorldLocalTest(WebDriver browser) {
        this.browser = browser;
    }

    @Before
    public void setUp() {
        browser.get("http://selenium.thinkcode.se/");
    }

    @After
    public void tearDown() {
        browser.quit();
    }

    @Test
    public void hello_world() {
        String expected = "Hello, world!";
        HelloWorldPage helloWorldPage = new HelloWorldPage(browser);

        String actual = helloWorldPage.getHeadline();

        assertThat(actual, is(expected));
    }

    @Parameterized.Parameters
    public static Collection<WebDriver[]> browsers() throws MalformedURLException {
        List<WebDriver[]> browsers = new LinkedList<>();
        WebDriver browser;

        browser = getLocalFirefox();
        browsers.add(new WebDriver[]{browser});

        browser = getLocalChrome();
        browsers.add(new WebDriver[]{browser});

        return browsers;
    }

    private static WebDriver getLocalFirefox() {
        return new FirefoxDriver();
    }

    private static WebDriver getLocalChrome() {
        System.setProperty("webdriver.chrome.driver", "/Users/tsu/tools/chrome-driver/chromedriver");
        return new ChromeDriver();
    }
}
