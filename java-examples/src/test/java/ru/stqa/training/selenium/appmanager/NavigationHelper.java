package ru.stqa.training.selenium.appmanager;

import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void mainPage() {
        driver.get("http://localhost/litecard/en/");
    }
}
