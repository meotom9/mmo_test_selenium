package mmo.project.utils;

import mmo.project.function.UtilsFunction;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class TestChromeDriver {

    private static final Logger log = LoggerFactory.getLogger(TestChromeDriver.class);

    private static Vector<String> l_user_agent = new Vector();

    public TestChromeDriver() {
        getUserAgent();
    }

    public void getUserAgent() {
        String filePath = "/deployments/user_agent"; // Specify the path to your file

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
//                System.out.println(line); // Process the line (print it in this case)
                l_user_agent.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions (file not found, etc.)
        }
    }

    public static String randomUserAgent() {
        int stt = UtilsFunction.randomRange(0, l_user_agent.size() - 1);
        return l_user_agent.get(stt);
    }

    public void requestAPI(String term, String website) {
        // Set the path to your ChromeDriver executable
//        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        System.setProperty("webdriver.chrome.driver", "/deployments/chromedriver-linux64/chromedriver");
        System.setProperty("webdriver.chrome.logfile", "/deployments/chromedriver.log");
        System.setProperty("webdriver.chrome.verboseLogging", "true");
//        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver-linux64/chromedriver");
//        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver-win64/chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver", "src/main/resources/chrome-headless-shell-win64/chrome-headless-shell.exe");

        // Proxy settings
//        String proxyAddress = "103.167.89.162";
//        int proxyPort = 3128;
//        String username = "trinh_JJkKD";
//        String password = "dyjaou0f";

        // Debugging: Print proxy details
//        System.out.println("Proxy Host: " + proxyAddress);
//        System.out.println("Proxy Port: " + proxyPort);
//        System.out.println("Username: " + username);
//        System.out.println("Password: " + password);

        // Set up ChromeOptions for headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Set headless mode
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("disable-infobars"); // Disable GPU for headless mode
        options.addArguments("--disable-extensions"); // Disable extensions of browser
        options.addArguments("--window-size=480,510"); // Set window size (optional)
        options.addArguments("--user-agent=" + randomUserAgent());
//        options.addArguments("--proxy-server=https://" + username + ":" + password + "@" + proxyAddress + ":" + proxyPort);
//        options.addArguments("--proxy-server=https://" + proxyAddress + ":" + proxyPort);
//        options.addArguments("--proxy-server=sock5://116.111.96.127:10423:trinh_JJkKD:dyjaou0f");

        // Initialize the ChromeDriver
        WebDriver driver = new ChromeDriver(options);
//        WebDriver driver = new ChromeDriver();
        System.out.println("1. Create Chrome Driver done");

        try {
            // Navigate to Google
            driver.get("https://www.google.com");
//            Thread.sleep(50000);

            // Find the search box and enter your query
            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys(term);
            searchBox.submit();
            System.out.println("2. Search done");
//            Thread.sleep(50000);

            // Find the link containing "tieuchuannhat.vn" and click it
            WebElement link = driver.findElement(By.partialLinkText(website));
            Actions actions = new Actions(driver);
            actions.moveToElement(link).click().perform();
            System.out.println("3. Click result website done");

            // Wait for the page to load (you might need to adjust the wait time)
//            Thread.sleep(5000);

            // Get the title of the current page
            String title = driver.getTitle();
            System.out.println("Current page title: " + title);

            // Scroll website
            System.out.println("4.1. Start organic function");
//            ((JavascriptExecutor) driver).executeScript("window.scrollBy(" + UtilsFunction.randomRange(0, 500) + ", " + UtilsFunction.randomRange(500, 1000) + ")");
//            Thread.sleep(UtilsFunction.randomRange(2000, 5000));
//
//            ((JavascriptExecutor) driver).executeScript("window.scrollBy(" + UtilsFunction.randomRange(500, 1000) + ", " + UtilsFunction.randomRange(1500, 2000) + ")");
//            Thread.sleep(UtilsFunction.randomRange(2000, 5000));
//
//            ((JavascriptExecutor) driver).executeScript("window.scrollBy(" + UtilsFunction.randomRange(500, 1000) + ", " + UtilsFunction.randomRange(1500, 2000) + ")");
//
            for (int i = 0; i < 5; i++) {
                System.out.println("Finding Ads index: " + (i + 1));
                boolean flag = findAdsAndClick(driver, i * 500);
                if (flag)
                    break;
            }

            System.out.println("4.2. End organic function");

//            Thread.sleep(5000);
//            Thread.sleep(50000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }

    public boolean findAdsAndClick(WebDriver driver, int index) throws InterruptedException {
        boolean flag = false;
        WebElement link = null;
        try {
            Vector<WebElement> l_link = (Vector) driver.findElements(By.cssSelector("[title=\"Advertisement\"]"));
            for (WebElement we : l_link) {
                int height = we.getSize().getHeight();
                System.out.println("Ads size - Width: " + we.getSize().getWidth() + " - Height: " + we.getSize().getHeight());
                if (height > 100) {
                    System.out.println("Found Ads");
                    link = we;
                    break;
                }
            }
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
        }
        if (link != null) {
//            Thread.sleep(60000);
            Actions actions = new Actions(driver);
            actions.moveToElement(link).click().perform();
            System.out.println("Click Ads Done");
            for (int i = 0; i < 3; i++) {
                scrollWeb(driver, i * 500);
                System.out.println("Scroll in ads: " + (i + 1));
                flag = true;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            scrollWeb(driver, index);
        }
        return flag;
    }

    public void scrollWeb(WebDriver driver, int start) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(" + UtilsFunction.randomRange(start, start + 500) + ", " + UtilsFunction.randomRange(start + 500, start + 1000) + ")");
//        ((JavascriptExecutor) driver).executeScript("window.scrollBy(" + (start + 300) + ", " + (start + 800) + ")");
        try {
            Thread.sleep(UtilsFunction.randomRange(2000, 5000));
//            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void main(String[] args) {
//        new GoogleFunctionUI().requestAPI("\"Top địa chỉ phòng tập gym tại Nghệ An\"", "top10nghean.com");
//    }
}

