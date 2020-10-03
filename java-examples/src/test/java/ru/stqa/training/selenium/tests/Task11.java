package ru.stqa.training.selenium.tests;

import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Task11 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecard/en");
    }

    @Test
    public void testTask11() throws InterruptedException {
        String password = "111111";
        long ms = new Date().getTime();
        String email = "cat" + ms + "@mail.com";

        click(By.xpath("(//a[contains(@href,'create')])[1]"));
        type(By.xpath("//input[@name='tax_id']"), "11111");
        type(By.xpath("//input[@name='company']"), "Itgenio");
        type(By.xpath("//input[@name='firstname']"), "Julia");
        type(By.xpath("//input[@name='lastname']"), "Belyaeva");
        type(By.xpath("//input[@name='postcode']"), "14132");
        type(By.xpath("//input[@name='address1']"), "Peresvet");
        type(By.xpath("//input[@name='address2']"), "Lenina 5-6");
        type(By.xpath("//input[@name='city']"), "Moscow");

        click(By.xpath("//span[contains(@id,'country_code')]"));
        type(By.xpath("//input[contains(@class,'search')]"), "United States");
        click(By.xpath("//span[contains(@class,'results')]//li[1]"));

        type(By.xpath("//input[@name='email']"), email);
        type(By.xpath("//input[@name='phone']"), "+79629881122");
        type(By.xpath("//input[@name='password']"), password);
        type(By.xpath("//input[@name='confirmed_password']"), password);
        click(By.xpath("//button[@name='create_account']"));

        click(By.xpath("//select[@name='zone_code']"));
        Thread.sleep(500);
        click(By.xpath("//select[@name='zone_code']//option[@value='AZ']"));

        type(By.xpath("//input[@name='password']"), password);
        type(By.xpath("//input[@name='confirmed_password']"), password);
        click(By.xpath("//button[@name='create_account']"));
        Thread.sleep(500);
        click(By.xpath("//div[@class='content']//a[contains(@href,'logout')]"));
        type(By.xpath("//input[@name='email']"), email);
        type(By.xpath("//input[@name='password']"), password);
        click(By.xpath("//button[@name='login']"));
        Thread.sleep(500);
        click(By.xpath("//div[@class='content']//a[contains(@href,'logout')]"));

    }

    protected void click(By locator) {
        driver.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        click(locator);
        if (text != null) {
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(text);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
