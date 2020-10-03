package ru.stqa.training.selenium.appmanager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ApplicationManager {

    private final Properties properties;
    public WebDriver driver;
    private NavigationHelper navigationHelper;
    private MainPageHelper mainPageHelper;
    private ProductHelper productHelper;
    private BasketHelper basketHelper;
    private String browser;


    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(
            new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
        if ("".equals(properties.getProperty("selenium.server"))) {
            if (browser.equals(BrowserType.FIREFOX)) {
                driver = new FirefoxDriver();
            } else if (browser.equals(BrowserType.CHROME)) {
                driver = new ChromeDriver();
            } else if (browser.equals(BrowserType.IEXPLORE)) {
                driver = new InternetExplorerDriver();
            }
        } else {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            driver = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")),
                capabilities);
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(properties.getProperty("web.baseUrl"));
        navigationHelper = new NavigationHelper(driver);
        mainPageHelper = new MainPageHelper(driver);
        productHelper = new ProductHelper(driver);
        basketHelper = new BasketHelper(driver);
    }

    public void stop() {
        driver.quit();
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public MainPageHelper mainpage() {
        return mainPageHelper;
    }

    public ProductHelper product() {
        return productHelper;
    }

    public BasketHelper basket() {
        return basketHelper;
    }
}
