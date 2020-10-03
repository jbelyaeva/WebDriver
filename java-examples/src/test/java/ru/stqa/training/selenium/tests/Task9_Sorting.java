package ru.stqa.training.selenium.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Task9_Sorting {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);

        driver.get(" http://localhost/litecard/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test //сортировка стран
    public void testTask9_1() {

/*Берем список стран из ui. Делаем его копию, которую отсортируем по алфавиту. Сравним поэлементно список и
* отсортированную копию. Если список отсортирован по алфавиту - списки (список и копия) будут идентичны.*/

        List<WebElement> countries = driver.findElements(By.xpath("//tr[@class='row']//td[5]"));
        List<String> listOld = new ArrayList<>();
        for (int i = 0; i <= countries.size() - 1; i++) {
            String text = countries.get(i).getText();
            listOld.add(i, text);
        }
        List<String> listNew = listOld;
        listNew.sort(String.CASE_INSENSITIVE_ORDER);

        for (int i = 0; i <= countries.size() - 1; i++) {
            assertThat(listOld.get(i), equalTo(listNew.get(i)));
        }
    }

    @Test //сортировка зон на странице страны
    public void testTask9_2() {
        List<WebElement> zones = driver.findElements(By.xpath("//tr[@class='row']//td[6]"));
        List<String> listOld = new ArrayList<>();
        for (int i = 0; i <= zones.size() - 1; i++) {
            if (!driver.findElement(By.xpath("(//tr[@class='row']//td[6])[" + (i + 1) + "]"))
                .getText().equals("0")) {
                driver.findElement(By.xpath("(//tr[@class='row']//td[5]//a)[" + (i + 1) + "]"))
                    .click();
                List<WebElement> countryZone = driver
                    .findElements(By.xpath("//table[@id='table-zones']//td[3]"));
                for (int j = 0; j <= countryZone.size() - 1; j++) {
                    String text = countryZone.get(j).getText();
                    String atrName = driver.
                        findElement(
                            By.xpath("(//table[@id='table-zones']//td[3]//input)[" + (j + 1) + "]"))
                        .getAttribute("name");
                    if (!atrName.equals("zone[name]")) {
                        listOld.add(j, text);
                    }
                }
                List<String> listNew = listOld;
                listNew.sort(String.CASE_INSENSITIVE_ORDER);

                for (int n = 0; n <= listOld.size() - 1; n++) {
                    assertThat(listOld.get(n), equalTo(listNew.get(n)));
                }
                driver.get(" http://localhost/litecard/admin/?app=countries&doc=countries");
            }
        }

    }

    @Test //сортировка зон
    public void testTask9_3() {
        driver.get("http://localhost/litecard/admin/?app=geo_zones&doc=geo_zones");
        List<WebElement> countries = driver.findElements(By.xpath("//tr[@class='row']//td[3]")); //zones
        List<String> listOld = new ArrayList<>();

        for (int i = 0; i <= countries.size() - 1; i++) {
            driver.findElement(By.xpath("(//tr[@class='row']//td[3]//a)[" + (i + 1) + "]"))
                .click();

            List<WebElement> countryZone = driver
                .findElements(By.xpath("//select[contains(@name,'zone_code')]//option[@selected='selected']"));
            for (int j = 0; j <= countryZone.size() - 1; j++) {
                String text = countryZone.get(j).getText();
                    listOld.add(j, text);
            }
            List<String> listNew = listOld;
            listNew.sort(String.CASE_INSENSITIVE_ORDER);

            for (int n = 0; n <= listOld.size() - 1; n++) {
                assertThat(listOld.get(n), equalTo(listNew.get(n)));
            }
            driver.get("http://localhost/litecard/admin/?app=geo_zones&doc=geo_zones");
        }
        }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
