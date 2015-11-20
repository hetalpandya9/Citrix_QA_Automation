package test.citrix;

import org.junit.After;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Base webDriver test class responsible for pre and post work for test executions
 *
 * @author Hetal Pandya
 */
public class BaseWebDriverTest {

    public WebDriver driver;
    public String baseUrl;
    public String myWebinarsPageUrl;

    static Properties config = new Properties();

    /**
     * Load configuration from a file before executing any tests
     */
    @BeforeClass
    public static void loadData() throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("config.properties");
        config.load(stream);
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
    }

    /**
     * Initialize members required to execute the tests.
     */
    public void setUp() throws Exception {
        driver = config.getProperty("driver").equals("Firefox") ? new FirefoxDriver() : new ChromeDriver();
        baseUrl = config.getProperty("baseUrl");
        myWebinarsPageUrl = baseUrl + "/webinars.tmpl";
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * Quit webDriver after every test execution
     */
    @After
    public void quit() {
        driver.quit();
    }
}
