package ru.stqa.training.selenium.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasketHelper extends HelperBase {

    public BasketHelper(WebDriver driver) {
        super(driver);
    }

    public WebElement fixedTable() {
        return driver
            .findElement(By.xpath("//table[@class='dataTable rounded-corners']"));
    }

    public void stopСarouselProductsInBasket() {
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
    }

    public void removeByOneFromBasket() {
        int count = Integer.parseInt((driver.findElement(By.xpath("//input[@name='quantity']"))
            .getAttribute("value")));
        if (count > 1) {
            driver.findElement(By.name("quantity")).sendKeys(Keys.DELETE);
            driver.findElement(By.name("quantity")).sendKeys(String.valueOf(count - 1));
            click(By.name("update_cart_item"));
        } else {
            click(By.name("remove_cart_item"));
        }
    }

}
