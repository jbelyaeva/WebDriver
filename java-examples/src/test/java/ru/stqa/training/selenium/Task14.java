package ru.stqa.training.selenium;

import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Task14 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);

        driver.get(" http://localhost/litecard/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void testTask14() {
        click(By.xpath("(//a[contains(@href,'AF')])[1]"));

        goToNewWindowAndGoToBack(
            By.xpath("//a[@href='http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2']"));
        goToNewWindowAndGoToBack(
            By.xpath("//a[@href='http://en.wikipedia.org/wiki/ISO_3166-1_alpha-3']"));
        goToNewWindowAndGoToBack(
            By.xpath("//a[@href='https://en.wikipedia.org/wiki/Regular_expression']"));
        goToNewWindowAndGoToBack(By.xpath(
            "//a[@href='http://www.addressdoctor.com/en/countries-data/address-formats.html']"));
        goToNewWindowAndGoToBack(
            By.xpath("//a[@href='https://en.wikipedia.org/wiki/Regular_expression']"));
        goToNewWindowAndGoToBack(By.xpath(
            "//a[@href='https://en.wikipedia.org/wiki/List_of_countries_and_capitals_with_currency_and_language']"));
        goToNewWindowAndGoToBack(
            By.xpath("//a[@href='https://en.wikipedia.org/wiki/List_of_country_calling_codes']"));
    }

    private void goToNewWindowAndGoToBack(By locatorHref) {
        String originalWindow = driver.getWindowHandle();
        Set<String> existingWindows = driver.getWindowHandles();
        click(locatorHref);
        String newWindow = (new WebDriverWait(driver, 10))
            .until((ExpectedCondition<String>) driver -> {
                    Set<String> newWindowsSet = driver.getWindowHandles();
                    newWindowsSet.removeAll(existingWindows);
                    return newWindowsSet.size() > 0 ?
                        newWindowsSet.iterator().next() : null;
                }
            );
        driver.switchTo().window(newWindow);
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    protected void click(By locator) {
        driver.findElement(locator).click();
    }
    
    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
