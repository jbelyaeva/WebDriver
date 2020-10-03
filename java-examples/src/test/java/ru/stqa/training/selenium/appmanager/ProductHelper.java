package ru.stqa.training.selenium.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductHelper extends HelperBase {

    private WebDriverWait wait;

    public ProductHelper(WebDriver driver) {
        super(driver);
    }


    public void selectAddCart() {
        if (isElementPresent(By.name("options[Size]"))) {
            click(By.name("options[Size]"));
            click(By.xpath("//select[@name='options[Size]']//option[@value='Small']"));
        }
        click(By.name("add_cart_product"));
    }

    public void waitUntilRefreshBacket(int i) {
        WebDriverWait waitCangeCount = new WebDriverWait(driver, 10);
        waitCangeCount.until(ExpectedConditions.textToBe(By.xpath("//span[@class='quantity']"),
            String.valueOf(i)));
    }

    public void selectBacket() {
        click(By.xpath("(//a[@class='link'])[1]"));
    }
}
