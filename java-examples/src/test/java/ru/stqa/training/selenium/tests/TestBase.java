package ru.stqa.training.selenium.tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.remote.BrowserType;
import ru.stqa.training.selenium.appmanager.ApplicationManager;

public class TestBase {

    protected static final ApplicationManager app
        = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @Before
    public void setUp() throws Exception {
        app.init();
    }


    @After
    public void tearDown() {
        app.stop();
    }
}