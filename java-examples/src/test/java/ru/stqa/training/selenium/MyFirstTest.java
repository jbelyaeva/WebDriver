package ru.stqa.training.selenium;
//первый тест
import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;

import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyFirstTest {

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void start() {
    driver = new ChromeDriver();
  //  driver = new FirefoxDriver();
  //  driver = new InternetExplorerDriver();

    wait = new WebDriverWait(driver, 10);
  }

  @Test
  public void testMyFirstTest() {
    driver.get("http://www.google.com/");
    driver.findElement(By.name("q")).sendKeys("webdriver");
    driver.findElement(By.name("btnK")).click();
    wait.until(titleContains("Google"));
  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }
}
