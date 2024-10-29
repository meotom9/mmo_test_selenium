package mmo.project.function;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Collections;

public class GoogleFunctionUI {

    private static final Logger logger = LogManager.getLogger(GoogleFunctionUI.class);

//    private static ArrayList<String> l_user_agent = new ArrayList();

    private static ArrayList<String> l_window_size = new ArrayList();

    public static WebDriver driver;

//    public GoogleFunctionUI() {
//        getWindowSize();
//    }

//    public void getUserAgent() {
//        String filePath = "/deployments/user_agent"; // Specify the path to your file
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = br.readLine()) != null) {
////                logger.info(line); // Process the line (print it in this case)
//                l_user_agent.add(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace(); // Handle exceptions (file not found, etc.)
//        }
//    }

    public static void getWindowSize() {
        String filePath = "/deployments/window-size"; // Specify the path to your file

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
//                logger.info(line); // Process the line (print it in this case)
                l_window_size.add(line);
            }
            System.out.println("Window size:" + l_window_size.size());
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions (file not found, etc.)
        }
    }

//    public static String randomUserAgent() {
//        int stt = UtilsFunction.randomRange(0, l_user_agent.size() - 1);
//        return l_user_agent.get(stt);
//    }

    public static String randomWindowSize() {
        int stt = UtilsFunction.randomRange(0, l_window_size.size() - 1);
        return l_window_size.get(stt);
    }

    public static boolean requestAPI(String term, String website, String userAgent, String actionMain, String actionAds) {
//    public void requestAPI(String term, String website, String userAgent, String actionMain, String actionAds) {
        // Clear WebDriverManager cache (if needed)
//        WebDriverManager.chromedriver().clearDriverCache();
//        WebDriverManager.chromedriver().clearResolutionCache();
//        // Automatically manage ChromeDriver
//        WebDriverManager.chromedriver().setup();
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
//        logger.info("Proxy Host: " + proxyAddress);
//        logger.info("Proxy Port: " + proxyPort);
//        logger.info("Username: " + username);
//        logger.info("Password: " + password);

        // Set up ChromeOptions for headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--allowed-ips"); // Set Security considerations
        options.addArguments("--headless"); // Set headless mode
//        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"}); // Optional
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--disable-infobars"); // Disable GPU for headless mode
        options.addArguments("--disable-notifications"); // Disable browser notifications
        options.addArguments("--disable-extensions"); // Disable extensions of browser
        options.addArguments("--window-size=" + randomWindowSize()); // Set window size (optional)
        options.addArguments("--user-agent=" + userAgent);
//        options.addArguments("--window-size=480,510"); // Set window size (optional)
//        options.addArguments("--user-agent=Mozilla/5.0 (Linux; Android 10; MED-LX9N; HMSCore 6.14.0.301) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.88 HuaweiBrowser/14.0.5.302 Mobile Safari/537.3");
//        options.addArguments("--proxy-server=https://" + username + ":" + password + "@" + proxyAddress + ":" + proxyPort);
//        options.addArguments("--proxy-server=https://" + proxyAddress + ":" + proxyPort);
//        options.addArguments("--proxy-server=sock5://116.111.96.127:10423:trinh_JJkKD:dyjaou0f");
        // Set the path to the Chrome binary
        options.setBinary("/opt/google/chrome/chrome");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--user-data-dir=/deployments/data/");
//        options.addArguments("--remote-debugging-port=9222");


//        options.addArguments("--headless"); // Set headless mode
//        options.addArguments("--no-sandbox"); // Bypass OS security model
//        options.addArguments("disable-infobars"); // Disable GPU for headless mode
//        options.addArguments("--disable-extensions"); // Disable extensions of browser
//        options.addArguments("--window-size=480,510"); // Set window size (optional)
//        options.addArguments("--user-agent=Mozilla/5.0 (Linux; Android 10; MED-LX9N; HMSCore 6.14.0.301) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.88 HuaweiBrowser/14.0.5.302 Mobile Safari/537.3");


        // Initialize the ChromeDriver
        driver = new ChromeDriver(options);
//        WebDriver driver = new ChromeDriver();
        logger.info("1. Create Chrome Driver done");

        try {
            // Navigate to Google
            driver.get("https://www.google.com");
//            Thread.sleep(50000);

            // Find the search box and enter your query
            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys(term);
            searchBox.submit();
            logger.info("2. Search done");
//            Thread.sleep(50000);
            doAction(driver, "scroll_5_2");

            // Find the link containing "tieuchuannhat.vn" and click it
            ArrayList<WebElement> l_link = (ArrayList) driver.findElements(By.partialLinkText(website));
            LevenshteinDistance lvd = new LevenshteinDistance();
            WebElement cur_link = null;
            int cur_dis = 0;
            for (WebElement link : l_link) {
                int distance = lvd.apply(link.getText(), term);
                if(distance >= cur_dis) {
                    cur_link = link;
                }
            }
            Actions actions = new Actions(driver);
            actions.moveToElement(cur_link).click().perform();
            logger.info("3. Click result website done");

            // Wait for the page to load (you might need to adjust the wait time)
//            Thread.sleep(5000);

            // Get the title of the current page
            String title = driver.getTitle();
            logger.info("Current page title: " + title);

            // Scroll website
            logger.info("4.1. Start organic function");

//            for (int i = 0; i < 5; i++) {
//                logger.info("Finding Ads index: " + (i + 1));
//                boolean flag = findAdsAndClick(driver, i * 500);
//                if (flag)
//                    break;
//            }

            logger.info("4.3. Do action from Main site");
            boolean flag = doAction(driver, actionMain);

            if (flag) {
                logger.info("4.3. Do action from Ads site");
                doAction(driver, actionAds);
            }

            logger.info("4.5. End organic function");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // Close the browser
            if (driver != null) {
                driver.quit();
            }
        }
    }

    public boolean findAdsAndClick(WebDriver driver, int index) throws InterruptedException {
        boolean flag = false;
        WebElement link = null;
        try {
            ArrayList<WebElement> l_link = (ArrayList) driver.findElements(By.cssSelector("[title=\"Advertisement\"]"));
            logger.info("Finding Ads total: " + l_link);
            for (WebElement we : l_link) {
                int height = we.getSize().getHeight();
                logger.info(" - Ads size - Width: " + we.getSize().getWidth() + " - Height: " + we.getSize().getHeight());
                if (height > 100) {
                    logger.info("Found Ads");
                    link = we;
                    break;
                }
            }
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
        }
        if (link != null && link.isEnabled() && link.isDisplayed()) {
            Actions actions = new Actions(driver);
            actions.moveToElement(link).click().perform();
            logger.info("Click Ads Done");
            for (int i = 0; i < 3; i++) {
                scrollWeb(driver, i * 500);
                logger.info("Scroll in ads: " + (i + 1));
                flag = true;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            scrollWeb(driver, index);
        }
        return flag;
    }

    public static void scrollWeb(WebDriver driver, int start) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(" + UtilsFunction.randomRange(start, start + 500) + ", " + UtilsFunction.randomRange(start + 500, start + 1000) + ")");
//        ((JavascriptExecutor) driver).executeScript("window.scrollBy(" + (start + 300) + ", " + (start + 800) + ")");
        try {
            Thread.sleep(UtilsFunction.randomRange(2000, 5000));
//            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        new GoogleFunctionUI().requestAPI("\"Top địa chỉ phòng tập gym tại Nghệ An\"", "top10nghean.com");
//    }

    public static boolean doAction(WebDriver driver, String action) {
        boolean flag = false;
        String[] l_action = action.split(",");
        for (String ac : l_action) {
            String[] l_ac = ac.split("_");
            System.out.println("Do action: " + l_ac[0] + " - Count: " + l_ac[1] + " - Interval: " + l_ac[2]);
            int count = Integer.parseInt(l_ac[1]);
            int wait = Integer.parseInt(l_ac[2]);
            switch (l_ac[0]) {
                case "click": {
                    for (int i = 0; i < count; i++) {
                        ArrayList<WebElement> links = (ArrayList) driver.findElements(By.tagName("a"));
                        System.out.println(" - Action: " + l_ac[0] + " - Count: " + i);
                        Collections.shuffle(links);
                        if (links.get(0) != null) {
                            if (links.get(0).isEnabled() && links.get(0).isDisplayed()) {
                                links.get(0).click();
                            }
                        }
                        try {
                            Thread.sleep(wait * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        driver.navigate().back();
                    }
                    break;
                }
                case "scroll": {
                    for (int i = 0; i < count; i++) {
                        System.out.println(" - Action: " + l_ac[0] + " - Count: " + i);
                        int start = i * 500;
                        ((JavascriptExecutor) driver).executeScript("window.scrollBy(" + UtilsFunction.randomRange(start, start + 500) + ", " + UtilsFunction.randomRange(start + 500, start + 1000) + ")");
                        try {
                            Thread.sleep(UtilsFunction.randomRange(wait * 1000, wait * 1000));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case "sleep": {
                    for (int i = 0; i < count; i++) {
                        System.out.println(" - Action: " + l_ac[0] + " - Count: " + i);
                        try {
                            Thread.sleep(UtilsFunction.randomRange(wait * 1000, wait * 1000));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case "ads": {
                    for (int i = 0; i < count; i++) {
                        System.out.println(" - Action: " + l_ac[0] + " - Count: " + i);
                        WebElement link = null;
                        try {
                            ArrayList<WebElement> l_link = (ArrayList) driver.findElements(By.cssSelector("[title=\"Advertisement\"]"));
                            for (WebElement we : l_link) {
                                int height = we.getSize().getHeight();
                                logger.info("Ads size - Width: " + we.getSize().getWidth() + " - Height: " + we.getSize().getHeight());
                                if (height > 100) {
                                    logger.info("Found Ads");
                                    link = we;
                                    break;
                                }
                            }
                        } catch (NoSuchElementException e) {
                            e.printStackTrace();
                        }
                        if (link != null && link.isEnabled() && link.isDisplayed()) {
                            Actions actions = new Actions(driver);
                            actions.moveToElement(link).click().perform();
                            logger.info("Click Ads Done");
                            flag = true;
                            try {
                                Thread.sleep(wait * 1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        } else {
                            scrollWeb(driver, count);
                        }
                        try {
                            Thread.sleep(wait * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
            }
        }
        return flag;
    }
}
