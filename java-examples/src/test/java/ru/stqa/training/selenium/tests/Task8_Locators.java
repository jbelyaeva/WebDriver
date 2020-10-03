package ru.stqa.training.selenium.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Task8_Locators {

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void start() {
    driver = new ChromeDriver();
    wait = new WebDriverWait(driver, 10);
  }

  @Test
  public void testTask8() {
    driver.get("http://localhost/litecard/en/");

    List<WebElement> cards = driver.findElements(By.xpath("//li[contains(@class,'product')]"));

    new WebDriverWait(driver, 10)
        .until(ExpectedConditions.visibilityOfAllElements(cards));

    //проверка на то, что каждый товар имеет стикер и он единственный
    for (int i = 1; i < cards.size(); i++) {
      List<WebElement> stickers = driver.findElements(
          By.xpath("(//li//a[@class='link'])[" + i + "]//div[contains(@class,'sticker')]"));
      assertThat(stickers.size(), equalTo(1));
    }
  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }
}
