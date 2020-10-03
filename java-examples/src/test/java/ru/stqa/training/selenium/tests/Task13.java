package ru.stqa.training.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.WebElement;

public class Task13 extends TestBase {

    @Test
    public void testTask13() {
        //положить в корзину 3 раза
        for (int i = 1; i <= 3; i++) {
            app.mainpage().selectProduct();
            app.product().selectAddCart();
            app.product().waitUntilRefreshBacket(i);
            app.goTo().mainPage();
        }
        // удалить из корзины
        app.product().selectBacket();
        for (int i = 1; i <= 3; i++) {
            WebElement table = app.basket().fixedTable();
            app.basket().stopСarouselProductsInBasket();
            app.basket().removeByOneFromBasket();
            app.basket().checkUpdateElement(table);
        }
    }
}
