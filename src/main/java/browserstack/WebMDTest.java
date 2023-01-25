package browserstack;

import org.testng.annotations.Test;

public class WebMDTest extends SeleniumTest{

    @Test(dataProvider = "dataMethod")
    public void testAds(String data) throws InterruptedException {
        System.out.println("Searching for url "+data+" ...");
        driver.get(data);
        Thread.sleep(10000);
    }
}
