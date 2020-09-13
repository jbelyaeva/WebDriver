package ru.stqa.training.selenium;

import static java.lang.Integer.parseInt;
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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Task10_PageProduct_FFox {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart/en/");
    }

    @Test //совпадает название
    public void testTask10_1() {
        String nameProduct = driver.findElement(By.xpath("(//div[@class='name'])[1]")).getText();
        driver.findElement(By.xpath("(//li[contains(@class,'product')])[1]")).click();
        String nameProductOnPage = driver.findElement(By.xpath("//h1")).getText();
        assertThat(nameProduct, equalTo(nameProductOnPage));
    }

    @Test //совпадает цена
    public void testTask10_2() {
        List<WebElement> cards = driver.findElements(By.xpath("//li[contains(@class,'product')]"));

        //найдем первый товар со скидкой, возьмем по нему данные и затем перейдем в его карточку
        for (int i = 1; i < cards.size(); i++) {
            String atr = driver
                .findElement(By.xpath("(//li[contains(@class,'product')])[" + i + "]"))
                .getAttribute("textContent");
            String[] getSale = atr.split("\n");
            String Sale = getSale[4].trim();
            if (Sale.equals("Sale")) {
                String prices = getSale[8];
                String[] price = prices.split(" ");
                String priceOld = price[16];
                String priceNew = price[17];
                driver.findElement(By.xpath("(//li[contains(@class,'product')])[" + i + "]"))
                    .click();
                String priceOldInCard = driver.findElement(By.xpath("//s[@class='regular-price']"))
                    .getText();
                String priceNewInCard = driver
                    .findElement(By.xpath("//div[@class='price-wrapper']//strong")).getText();
                assertThat(priceOld, equalTo(priceOldInCard));
                assertThat(priceNew, equalTo(priceNewInCard));
                return;
            }
        }
    }

    @Test //обычная цена серая и зачеркнутая
    public void testTask10_3() {
        List<WebElement> cards = driver.findElements(By.xpath("//li[contains(@class,'product')]"));

        //найдем первый товар со скидкой, возьмем по нему данные и затем перейдем в его карточку
        for (int i = 1; i < cards.size(); i++) {
            String atr = driver
                .findElement(By.xpath("(//li[contains(@class,'product')])[" + i + "]"))
                .getAttribute("textContent");
            String[] getSale = atr.split("\n");
            String Sale = getSale[4].trim();
            if (Sale.equals("Sale")){
                checkOnGray(By.xpath("(//li[contains(@class,'product')])[" + i + "]//s"));
           //     checkOnLine(By.xpath("(//li[contains(@class,'product')])[" + i + "]//s"));
                driver.findElement(By.xpath("(//li[contains(@class,'product')])[" + i + "]"))
                    .click();
                checkOnGray(By.xpath("//div[@class='price-wrapper']//s"));
             //   checkOnLine(By.xpath("//div[@class='price-wrapper']//s"));
                return;
            }
        }
    }

    @Test //акционная цена жирная и красная
    public void testTask10_4() {
        List<WebElement> cards = driver.findElements(By.xpath("//li[contains(@class,'product')]"));

        //найдем первый товар со скидкой, возьмем по нему данные и затем перейдем в его карточку
        for (int i = 1; i < cards.size(); i++) {
            String atr = driver
                .findElement(By.xpath("(//li[contains(@class,'product')])[" + i + "]"))
                .getAttribute("textContent");
            String[] getSale = atr.split("\n");
            String Sale = getSale[4].trim();
            if (Sale.equals("Sale")){
                checkOnRed(By.xpath("(//li[contains(@class,'product')])[" + i + "]//strong"));
             //   checkOnLineBold(By.xpath("(//li[contains(@class,'product')])[" + i + "]//strong"));
                driver.findElement(By.xpath("(//li[contains(@class,'product')])[" + i + "]"))
                    .click();
                checkOnRed(By.xpath("//div[@class='price-wrapper']//strong"));
               // checkOnLineBold(By.xpath("//div[@class='price-wrapper']//strong"));
                return;
            }
        }
    }

    @Test //акционная цена крупнее, чем обычная
    public void testTask10_5() {
        List<WebElement> cards = driver.findElements(By.xpath("//li[contains(@class,'product')]"));

        for (int i = 1; i < cards.size(); i++) {
            String atr = driver
                .findElement(By.xpath("(//li[contains(@class,'product')])[" + i + "]"))
                .getAttribute("textContent");
            String[] getSale = atr.split("\n");
            String Sale = getSale[4].trim();
            if (Sale.equals("Sale")) {
                checkFont(By.xpath("(//li[contains(@class,'product')])[" + i + "]//s"),
                    By.xpath("(//li[contains(@class,'product')])[" + i + "]//strong"));
                driver.findElement(By.xpath("(//li[contains(@class,'product')])[" + i + "]"))
                    .click();
                checkFont(By.xpath("//div[@class='price-wrapper']//s"),
                    By.xpath("//div[@class='price-wrapper']//strong"));
                return;
            }
        }
    }


    private void checkOnGray(By locator) {
        String color = driver.findElement(locator).getCssValue("color");
        String[] elementsColor = color.split("[a-z]");
        String rgbOld = elementsColor[3];//(119,119,119,1)
        String[] rgbNew = rgbOld.split("[(,)]");
        int r = parseInt(rgbNew[1].trim());
        int g = parseInt(rgbNew[2].trim());
        int b = parseInt(rgbNew[3].trim());
        assertThat((r == g && r == b && g == b), equalTo(true));
    }

    private void checkOnRed(By locator) {
        String color = driver.findElement(locator).getCssValue("color");
        String[] elementsColor = color.split("[a-z]");
        String rgbOld = elementsColor[3];//(119,119,119,1)
        String[] rgbNew = rgbOld.split("[(,)]");
        int g = parseInt(rgbNew[2].trim());
        int b = parseInt(rgbNew[3].trim());
        assertThat((g == 0 && b == 0), equalTo(true));
    }

    private void checkOnLine(By locator) {
        String styleElement = driver.findElement(locator).getCssValue("font");
        String[] getStyle = styleElement.split("[0-9,()]");
        String style = getStyle[0].substring(0, 12);
        assertThat(style, equalTo("line-through"));
    }

    private void checkOnLineBold(By locator) {
        String styleElement = driver.findElement(locator).getCssValue("font-weight");
        assertThat(styleElement, equalTo("700"));
    }

    private void checkFont(By locatorOld, By locatorNew) {
        String styleOld = driver.findElement(locatorOld).getCssValue("font-size");
        String[] styleOldPrice = styleOld.split("[a-z]");
        double fontOld = Double.parseDouble((styleOldPrice[0]));
        String styleNew = driver.findElement(locatorNew).getCssValue("font-size");
        String[] styleNewPrice = styleNew.split("[a-z]");
        double fontNew = Double.parseDouble((styleNewPrice[0]));
        assertThat((fontNew > fontOld), equalTo(true));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
