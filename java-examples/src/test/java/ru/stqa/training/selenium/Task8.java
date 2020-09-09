package ru.stqa.training.selenium;

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

public class Task8 {

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void start() {
    driver = new ChromeDriver();
    wait = new WebDriverWait(driver, 10);
  }

  @Test
  public void testTask8() {
    driver.get("http://localhost/litecart/en/");

    List<WebElement> cards = driver.findElements(By.xpath("//li//a[@class='link']"));

    new WebDriverWait(driver, 10)
        .until(ExpectedConditions.visibilityOfAllElements(cards));
    List<WebElement> newSticker = driver.findElements(By.xpath("//div[@class='sticker new']"));
    List<WebElement> saleSticker = driver.findElements(By.xpath("//div[@class='sticker sale']"));
    //стикеров столько же сколько товара
    assertThat(cards.size(), equalTo(newSticker.size() + saleSticker.size()));
    //каждый товар имеет один стикер (одной этой проверки достаточно для Задания 8)
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
