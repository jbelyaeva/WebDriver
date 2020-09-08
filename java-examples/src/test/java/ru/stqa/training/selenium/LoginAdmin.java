package ru.stqa.training.selenium;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginAdmin {

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void start() {
    driver = new ChromeDriver();
    //driver = new FirefoxDriver();
    wait = new WebDriverWait(driver, 10);
  }

  @Test
  public void testLoginAdmin() {
    driver.get("http://localhost/litecart/admin/login.php");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();
    wait.until(titleContains("My Store"));
  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }
}
