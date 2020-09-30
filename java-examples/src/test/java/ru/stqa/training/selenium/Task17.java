package ru.stqa.training.selenium;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Task17 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);

        driver.get("http://localhost/litecard/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.get(" http://localhost/litecard/admin/?app=catalog&doc=catalog&category_id=1");
    }

    @Test
    public void testTask17() {
        int countProduct = driver
            .findElements(By.xpath("//tr//td[3]/a[contains(@href,'product_id')]")).size();
        for (int i = 1; i <= countProduct; i++) {
            driver.findElement(By.xpath("(//tr//td[3]/a[contains(@href,'product_id')])[" + i + "]"))
                .click();
            for (LogEntry l : driver.manage().logs().get("browser").getAll()) {
                assertThat(l, equalTo(null));
            }
            driver.navigate().back();
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
