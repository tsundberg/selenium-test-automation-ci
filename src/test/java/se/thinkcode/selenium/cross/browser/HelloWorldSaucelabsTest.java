package se.thinkcode.selenium.cross.browser;

import com.saucelabs.common.SauceOnDemandAuthentication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
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
public class HelloWorldSaucelabsTest {
    private WebDriver browser;

    public HelloWorldSaucelabsTest(WebDriver browser) {
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
        WebDriver driver;
        String jobName = "Hello world cross browser";

        driver = getInternetExplorerOnXP(jobName);
        browsers.add(new WebDriver[]{driver});

        driver = getFirefoxOnLinux(jobName);
        browsers.add(new WebDriver[]{driver});

        // driver = getLocalFirefox(jobName);
        // browsers.add(new WebDriver[]{driver});

        return browsers;
    }

    private static WebDriver getInternetExplorerOnXP(String jobName) throws MalformedURLException {
        URL sauceLabsUrl = getSauceLabsUrl();
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability("name", jobName);
        capabilities.setCapability("version", "8.0");
        capabilities.setCapability("platform", Platform.XP);

        return new RemoteWebDriver(sauceLabsUrl, capabilities);
    }

    private static WebDriver getLocalFirefox(String jobName) throws MalformedURLException {
        return new FirefoxDriver();
    }

    private static WebDriver getFirefoxOnLinux(String jobName) throws MalformedURLException {
        URL sauceLabsUrl = getSauceLabsUrl();
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("name", jobName);
        capabilities.setCapability("version", "39.0");
        capabilities.setCapability("platform", Platform.LINUX);

        return new RemoteWebDriver(sauceLabsUrl, capabilities);
    }

    private static URL getSauceLabsUrl() throws MalformedURLException {
        SauceOnDemandAuthentication authentication = getSauceLabsAuthentication();
        String userName = authentication.getUsername();
        String accessKey = authentication.getAccessKey();
        String remoteHub = "@ondemand.saucelabs.com:80/wd/hub";
        return new URL("http://" + userName + ":" + accessKey + remoteHub);
    }

    public static SauceOnDemandAuthentication getSauceLabsAuthentication() {
        String userName = "tsundberg";
        String accessKey = "b6938bf6-61c6-4bde-96bb-c416ea8a3fa4";

        return new SauceOnDemandAuthentication(userName, accessKey);
    }
}
