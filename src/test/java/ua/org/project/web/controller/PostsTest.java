package ua.org.project.web.controller;

import com.thoughtworks.selenium.SeleneseTestBase;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by dmitriy on 5/24/15.
 */
public class PostsTest extends SeleneseTestBase{

    public static final String USERNAME = "admin";
    public static final String PASSWORD = "admin";

    @Before
    public void setUp() throws Exception {
        WebDriver driver = new FirefoxDriver();
        String baseUrl = "http://localhost:8080/";
        selenium = new WebDriverBackedSelenium(driver, baseUrl);
    }

    @Test
    public void authorisation(){
        selenium.open("http://localhost:8080/");
        selenium.type("loginform:email", USERNAME);
        selenium.type("loginform:password", PASSWORD);
        selenium.click("id=loginform:loginButton");
        selenium.waitForPageToLoad("30000");
    }
}

