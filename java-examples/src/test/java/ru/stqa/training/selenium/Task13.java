package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Task13 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecard/en/");
    }

    @Test
    public void testTask13() {
        //положить в корзину
        for (int i = 1; i <= 3; i++) {
            click(By.xpath("(//div[@class='content'])[7]//a"));
            if (isElementPresent(By.name("options[Size]"))) {
                click(By.name("options[Size]"));
                click(By.xpath("//select[@name='options[Size]']//option[@value='Small']"));
            }
            click(By.name("add_cart_product"));

            WebDriverWait waitCangeCount = new WebDriverWait(driver, 10);
            waitCangeCount.until(ExpectedConditions.textToBe(By.xpath("//span[@class='quantity']"),
                String.valueOf(i)));

            driver.get("http://localhost/litecard/en/");
        }
        // удалить из корзины
        click(By.xpath("(//a[@class='link'])[1]"));
        for (int i = 1; i <= 3; i++) {
            WebElement table = driver
                .findElement(By.xpath("//table[@class='dataTable rounded-corners']"));
            // остановить карусель
            if (isElementPresent(By.xpath("(//ul[@class='shortcuts']//a)[1]"))) {
                WebElement smallPicture = driver
                    .findElement(By.xpath("(//ul[@class='shortcuts']//a)[1]"));

                WebDriverWait element = new WebDriverWait(driver, 10);
                element
                    .until(ExpectedConditions.elementToBeClickable(smallPicture));
                Actions actions = new Actions(driver);
                actions.moveToElement(smallPicture).build().perform();
                smallPicture.click();
            }

            //если количество товара >1 , то удалить через кнопку update
         int count = Integer.parseInt((driver.findElement(By.xpath("//input[@name='quantity']"))
                .getAttribute("value")));
            if (count > 1) {
                driver.findElement(By.name("quantity")).sendKeys(Keys.DELETE);
                driver.findElement(By.name("quantity")).sendKeys(String.valueOf(count - 1));
                click(By.name("update_cart_item"));
            } else {
                click(By.name("remove_cart_item"));
            }

            WebDriverWait waitRefreshTable = new WebDriverWait(driver, 10);
            waitRefreshTable
                .until(ExpectedConditions.stalenessOf(table));
        }
    }

    protected void click(By locator) {
        driver.findElement(locator).click();
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
