package ru.stqa.training.selenium.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Task12 {

    public static Properties properties;
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() throws IOException {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);

        properties = new Properties();
        String target = System.getProperty("target", "local");
        properties.load(
            new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
        driver.get("http://localhost/litecard/admin/");

        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void testTask12() throws InterruptedException {
        List<WebElement> mainMenu = driver.findElements(By.xpath("//ul[@id='box-apps-menu']//li"));

        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.visibilityOfAllElements(mainMenu));

        click(By.xpath("//a[contains(@href,'catalog')]"));
        click(By.xpath("//a[@class='button'][2]"));

        //вкладка General

        click(By.name("status"));
        Thread.sleep(100);
        type(By.name("name[en]"), "Cat");
        Thread.sleep(100);
        type(By.name("code"), "A125B");
        Thread.sleep(100);
        click(By.xpath("(//div[@class='input-wrapper'])[2]//input[1]"));
        type(By.name("quantity"), "2,25");
        String filePath =
            properties.getProperty("user.dir") + "/src/test/resources/images/cat.jpg";
        driver.findElement(By.xpath("//input[@type='file']")).sendKeys(filePath);
        type(By.xpath("(//input[@type='date'])[1]"), "12122020");
        type(By.xpath("(//input[@type='date'])[2]"), "12122021");

        //вкладка Information
        click(By.xpath("//a[contains(@href,'information')]"));
        Thread.sleep(200);
        click(By.xpath("//select[@name='manufacturer_id']"));
        click(By.xpath("//select[@name='manufacturer_id']//option[@value='1']"));
        type(By.name("keywords"), "cat, kitten");
        type(By.name("short_description[en]"), "Kopilka cat Black color");

        driver.findElement(By.className("trumbowyg-editor")).click();
        WebElement element = driver.findElement(By.className("trumbowyg-editor"));
        ((JavascriptExecutor) driver).executeScript(
            "var ele=arguments[0]; ele.innerHTML = 'New little kitten. It is black. You will like it.';",
            element);

        type(By.name("head_title[en]"), "Kopilka");
        type(By.name("meta_description[en]"), "Kopilka");

        //вкладка Price
        click(By.xpath("//a[contains(@href,'prices')]"));
        Thread.sleep(200);
        type(By.name("purchase_price"), "5,25");
        click(By.xpath("//select[@name='purchase_price_currency_code']"));
        click(By.xpath("//select[@name='purchase_price_currency_code']//option[@value='USD']"));
        type(By.name("prices[USD]"), "123");
        type(By.name("prices[EUR]"), "90");

        //Сохранить и проверить
        click(By.xpath("//button[@value='Save']"));
        assertThat(isElementPresent(By.xpath("(//a[contains(text(), 'Cat')])[2]")), equalTo(true));
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

    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
