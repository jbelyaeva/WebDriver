package ru.stqa.training.selenium.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPageHelper extends HelperBase {

    public MainPageHelper(WebDriver driver) {
        super(driver);
    }

    public void selectProduct() {
        click(By.xpath("(//div[@class='content'])[7]//a"));
    }
}
