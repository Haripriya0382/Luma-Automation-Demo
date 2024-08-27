package Accountcreation;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Duration;
import java.util.List;
import java.util.Locale;

import static Accountcreation.Registration.driver;

public class Men {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    @BeforeEach
    public void setUp() {
        Registration registration = new Registration();
        registration.setUp();
        this.driver = Registration.driver;
        Sign_in signIn = new Sign_in();
        signIn.performSignIn("haripriya.g@panasatech.com", "Hari@Luma123");
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        actions = new Actions(driver);
    }

    @Test
    public void testSelectHoodedStyle() {
        // Navigate to Hoodies & Sweatshirts
        WebElement men = driver.findElement(By.xpath("//span[text()='Men']"));
        men.click();
        WebElement jacket = driver.findElement(By.xpath("//a[text()='Hoodies & Sweatshirts']"));
        jacket.click();

        // Switch to List View
        WebElement listViewButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='List' and contains(@class, 'mode-list')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", listViewButton);

        // Scroll to the bottom
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight);");

        // Sort by Price
        WebElement sorterDropdown = driver.findElement(By.id("sorter"));
        Select select = new Select(sorterDropdown);
        select.selectByValue("price");

        // Wait for the page to reload (if necessary)
        try {
            Thread.sleep(2000);  // Adjust as necessary
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify price sorting
        List<WebElement> priceElements = driver.findElements(By.className("price"));
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        float[] prices = new float[priceElements.size()];
        for (int i = 0; i < priceElements.size(); i++) {
            String priceText = priceElements.get(i).getText().replaceAll("[^\\d.,]", "");
            try {
                Number number = currencyFormat.parse(priceText);
                prices[i] = number.floatValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        boolean isSorted = true;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < prices[i - 1]) {
                isSorted = false;
                break;
            }
        }

        if (isSorted) {
            System.out.println("Prices are sorted in ascending order.");
        } else {
            System.out.println("Prices are not sorted in ascending order.");
        }

        // Select "Bruno Compete Hoodie"
        List<WebElement> purchaseitems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//a[@class='product-item-link']")));
        WebElement itemToClick = null;
        String selecteditem = "Bruno Compete Hoodie";

        for (WebElement purchaseitem : purchaseitems) {
            String itemName = purchaseitem.getText().trim();
            if (itemName.equalsIgnoreCase(selecteditem)) {
                itemToClick = purchaseitem;
                break;
            }
        }

        if (itemToClick != null) {
            itemToClick.click();
            wait.until(ExpectedConditions.urlContains("bruno-compete-hoodie.html"));
            String currenturl = driver.getCurrentUrl();
            Assert.assertTrue("Page does load Bruno Compete Hoodie", currenturl.contains("bruno-compete-hoodie.html"));
        } else {
            Assert.fail("Bruno Compete Hoodie is not found on the page");
        }

        // Select Size "S"
        SelectSizeOption sizeOption = new SelectSizeOption(driver, wait);
        sizeOption.selectSize("S");
        System.out.println("Successfully selected the size 'S'.");

        // Select Color "Gray"
        ColourSelect colourSelect = new ColourSelect(driver, wait);
        colourSelect.selectColor("Black");
        System.out.println("Successfully selected the color 'Black'.");

        // Add to cart
        driver.findElement(By.xpath("//button[@title='Add to Cart']")).click();
    }

    @AfterEach
    public void tearDown() {
      //  driver.quit();
    }
}

class SelectSizeOption {
    private WebDriver driver;
    private WebDriverWait wait;

    public SelectSizeOption(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void selectSize(String size) {
        List<WebElement> sizeOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='swatch-attribute-options clearfix']//div[@role='option']")));

        for (WebElement sizeOption : sizeOptions) {
            if (sizeOption.getText().trim().equalsIgnoreCase(size)) {
                sizeOption.click();
                System.out.println("Successfully selected the size '" + size + "'.");
                break;
            }
        }
    }
}

class ColourSelect {
    private WebDriver driver;
    private WebDriverWait wait;

    public ColourSelect(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void selectColor(String color) {
        // Wait until the color options are visible
        List<WebElement> colorOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='swatch-attribute-options clearfix']/div[@role='option']")));

        WebElement colorToClick = null;

        // Print out the available color options for debugging
        System.out.println("Available color options:");
        for (WebElement colorOption : colorOptions) {
            String colorLabel = colorOption.getAttribute("aria-label").trim();
            System.out.println("Found color: " + colorLabel);

            if (colorLabel.equalsIgnoreCase(color)) {
                colorToClick = colorOption;
                break;
            }
        }

        // Check if the desired color was found
        if (colorToClick != null) {
            colorToClick.click();
            System.out.println("Successfully selected the color: " + color);
        } else {
            // Print error message and fail the test
            System.out.println("Error: Color " + color + " is not found on the page.");
            Assert.fail("Color " + color + " is not found on the page");
        }
    }
}







































