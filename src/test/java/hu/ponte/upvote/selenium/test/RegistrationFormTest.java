package hu.ponte.upvote.selenium.test;

import hu.ponte.upvote.selenium.environment.EnvironmentManager;
import hu.ponte.upvote.selenium.environment.RunEnvironment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationFormTest {

    @BeforeEach
    public void startBrowser() {
        EnvironmentManager.initWebDriver();
    }

    @Test
    public void demo(){

        WebDriver driver = RunEnvironment.getWebDriver();

        driver.get("http://localhost:3000/registration");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.id("firstName")).sendKeys("Andras");
        String firstName = driver.findElement(By.id("firstName")).getAttribute("value");
        assertEquals(firstName, "Andras");

        driver.findElement(By.id("lastName")).sendKeys("Hadobas");
        String lastName = driver.findElement(By.id("lastName")).getAttribute("value");
        assertEquals(lastName, "Hadobas");

        driver.findElement(By.id("userName")).sendKeys("andras.hadobas");
        String userName = driver.findElement(By.id("userName")).getAttribute("value");
        assertEquals(userName, "andras.hadobas");

        driver.findElement(By.id("email")).sendKeys("andras.hadobas@gmail.com");
        String eMail = driver.findElement(By.id("email")).getAttribute("value");
        assertEquals(eMail, "andras.hadobas@gmail.com");

        driver.findElement(By.id("password")).sendKeys("AH2019@progmasters");
        String password = driver.findElement(By.id("password")).getAttribute("value");
        assertEquals(password, "AH2019@progmasters");

        driver.findElement(By.id("retypedPassword")).sendKeys("AH2019@progmasters");
        String retypedPassword = driver.findElement(By.id("retypedPassword")).getAttribute("value");
        assertEquals(retypedPassword, "AH2019@progmasters");

        driver.findElement(By.id("registrationButton")).click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        EnvironmentManager.shutDownDriver();
    }
}
