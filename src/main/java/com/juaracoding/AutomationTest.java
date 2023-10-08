package com.juaracoding;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AutomationTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\\\Users\\\\LENOVO\\\\Downloads\\\\chromedriver_win32guys\\\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testLogin() {
        // Buka halaman login
        driver.get("https://shop.demoqa.com/login");

        for (int attempt = 1; attempt <= 3; attempt++) {
            // Masukkan username dan password yang benar
            WebElement usernameField = driver.findElement(By.id("username"));
            WebElement passwordField = driver.findElement(By.id("password"));
            WebElement loginButton = driver.findElement(By.id("login"));

            usernameField.sendKeys("hengki"); // Ganti dengan username yang benar
            passwordField.sendKeys("12345678"); // Ganti dengan password yang benar
            loginButton.click();

            // Tunggu sejenak untuk pemeriksaan pesan kesalahan
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Periksa apakah login berhasil
            WebElement errorMessage = driver.findElement(By.xpath("//p[@class='woocommerce-error']"));
            if (errorMessage.isDisplayed()) {
                System.out.println("Login attempt " + attempt + " failed.");
                usernameField.clear();
                passwordField.clear();
            } else {
                System.out.println("Login successful.");
                break;
            }
        }

        WebElement welcomeMessage = driver.findElement(By.xpath("//h2[contains(text(),'Hello')]"));
        Assert.assertTrue(welcomeMessage.isDisplayed(), "Login tidak berhasil.");
    }

    @Test(priority = 2, dependsOnMethods = {"testLogin"})
    public void testAddProductToCart() {
        driver.get("https://shop.demoqa.com/product-category/accessories/");

        WebElement product = driver.findElement(By.xpath("//a[@class='product-title'][1]"));
        product.click();

        WebElement addToCartButton = driver.findElement(By.xpath("//button[contains(text(),'Add to cart')]"));
        addToCartButton.click();

        WebElement cartMessage = driver.findElement(By.xpath("//div[@class='woocommerce-message']"));
        Assert.assertTrue(cartMessage.isDisplayed(), "Produk tidak berhasil ditambahkan ke keranjang.");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
