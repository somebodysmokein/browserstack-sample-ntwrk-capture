package browserstack;

import com.mashape.unirest.http.Unirest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.net.URL;
import java.util.Map;

public class SeleniumTest {
    public WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();

        // Set your access credentials
        caps.setCapability("browserstack.user", System.getenv("BROWSERSTACK_USERNAME"));
        caps.setCapability("browserstack.key", System.getenv("BROWSERSTACK_ACCESS_KEY"));


        // Specify device and os_version for testing
        //caps.setCapability("os", "Windows");
        //caps.setCapability("os_version", "10");
        //caps.setCapability("browser", "Chrome");
        //caps.setCapability("browser_version", "latest-beta");
        caps.setCapability("os_version", "16");
        caps.setCapability("device", "iPhone 14");
        caps.setCapability("browserstack.local", "false");


        // Set other BrowserStack capabilities
        caps.setCapability("project", "First Java Project");
        caps.setCapability("build", "browserstack-build-1");
        caps.setCapability("name", "first_test");
        caps.setCapability("browserstack.networkLogs", "true");
        caps.setCapability("browserstack.enablePasscode", "true");
        try {
            String username = System.getenv("BROWSERSTACK_USERNAME");
            String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
            driver = new RemoteWebDriver(new URL("https://"+username+":"+accessKey+"@hub.browserstack.com/wd/hub"), caps);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        AccessNetworkLogs logs = new AccessNetworkLogs(driver);
        NetworkLogTracker tracker = NetworkLogTracker.getInstance();
        SessionId s =((RemoteWebDriver) driver).getSessionId();
        tracker.setSession(logs.getBuildId(), s.toString());
        driver.quit();
    }



    @AfterSuite
    public void getPerformanceData() throws Exception {
        AsyncTask.pollForCompletion().
                thenApply((result) ->
                {
                    NetworkLogTracker networkData = NetworkLogTracker.getInstance();
                    for (Map.Entry<String, String> item : networkData.getSessionDetails().entrySet()) {

                        NetworkUtils logs = new NetworkUtils();
                        try {
                            logs.getNetworkLogs(item.getValue(), item.getKey());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return "Complete";
                }).get();

        Unirest.shutdown();
    }

    @DataProvider
    public Object[][] dataMethod() {
        return new Object[][] { { "https://reference.medscape.com/viewarticle/985317_4" },
                { "https://reference.medscape.com/viewarticle/984870_2" },
                {"https://reference.medscape.com/viewarticle/984871_3"}};
    }

    }
