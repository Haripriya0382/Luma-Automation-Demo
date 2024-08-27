package Accountcreation;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static Accountcreation.Registration.driver;

public class Sign_in {

    // Method to perform the sign-in process
    public void performSignIn(String email, String password) {
        // Navigate directly to the sign-in page
        WebElement signInLink = Registration.driver.findElement(By.cssSelector("li.authorization-link a"));
        signInLink.click();

        // Fill in the sign-in form with existing username and password
        WebElement emailField = Registration.driver.findElement(By.xpath("//input[@id='email']"));
        emailField.sendKeys(email);

        WebElement passwordField = Registration.driver.findElement(By.xpath("//input[@id='pass']"));
        passwordField.sendKeys(password);

        // Submit the sign-in form
        WebElement signInButton = Registration.driver.findElement(By.xpath("//button[@id='send2']"));
        signInButton.click();

        // Wait for the welcome message to appear
        WebDriverWait wait = new WebDriverWait(Registration.driver, Duration.ofSeconds(10));
        WebElement welcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='logged-in']")));

        // Construct the expected welcome text
        String expectedWelcomeText = "Welcome, Haripriya G!";

        // Assert that the welcome message contains the correct name
        Assert.assertTrue("Welcome message does not contain the correct name", welcomeMessage.getText().contains(expectedWelcomeText));
    }

    @Test
    public void testSignInWithLogoVerification() {
        // Create an instance of the Registration class
        Registration registration = new Registration();

        // Call the setUp method to initialize the browser
        Registration.setUp();

        try {
            // Call the sign-in method
            performSignIn("haripriya.g@panasatech.com", "Hari@Luma123");

            // Additional checks like logo verification
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='logo']")));
            Assert.assertTrue(logo.isDisplayed());
            System.out.println("Logo is present on the page.");

        } finally {
            // Ensure the tearDown method is called to close the browser after testing is complete
            // Registration.tearDown();
        }
    }

    @Test
    public void whatsnew() {
        // Setup the browser and navigate to the target page
        Registration registration = new Registration();
        Registration.setUp();

        try {
            // Sign in
            Sign_in signin = new Sign_in();
            signin.performSignIn("haripriya.g@panasatech.com", "Hari@Luma123");

            // Navigate to "What's New"
            WebElement whatsNewLink = driver.findElement(By.xpath("//a[@id='ui-id-3']"));
            whatsNewLink.click();
            driver.manage().window().fullscreen();

            // Wait until the categories menu is visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> categoryList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='categories-menu']//a")));

            // Print the list of categories
            System.out.println("List of categories:");
            for (WebElement categoryItem : categoryList) {
                System.out.println(categoryItem.getText().trim());
            }

            // Look for the "Hoodies & Sweatshirts" item
            String selectedItem = "Hoodies & Sweatshirts";
            WebElement itemToClick = null;

            for (WebElement item : categoryList) {
                String itemName = item.getText().trim();
                if (itemName.equalsIgnoreCase(selectedItem)) {
                    itemToClick = item;
                    break;
                }
            }

            // Click the stored item if found
            if (itemToClick != null) {
                itemToClick.click();

                // Verify that the URL or the page content is correct
                wait.until(ExpectedConditions.urlContains("hoodies-and-sweatshirts-women.html"));

                String currentUrl = driver.getCurrentUrl();
                Assert.assertTrue("The Hoodies & Sweatshirts page did not load correctly", currentUrl.contains("hoodies-and-sweatshirts-women.html"));
                System.out.println("Clicked on 'Hoodies & Sweatshirts' and navigated to the correct page. URL: " + currentUrl);
            } else {
                Assert.fail("The item 'Hoodies & Sweatshirts' was not found in the category list.");
            }

            // Scroll down to the carousel and click on an image to open it
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            driver.findElement(By.xpath("//img[@alt='Selene Yoga Hoodie']")).click();

            // Wait for the "Next" arrow and click it twice
            WebElement nextArrow = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".fotorama__arr--next")));
            js.executeScript("arguments[0].click();", nextArrow);
            js.executeScript("arguments[0].click();", nextArrow);

            // Wait for the "Previous" arrow and click it
            WebElement previousArrow = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".fotorama__arr--prev")));
            js.executeScript("arguments[0].click();", previousArrow);
            js.executeScript("arguments[0].click();", previousArrow);
            System.out.println("Successfully navigated the carousel.");

            SelectSizeOption sizeOption = new SelectSizeOption();
            sizeOption.selectSizeS() ;  // Call the method to select size "S"
            System.out.println("Successfully selected the size 'S'.");
            driver.findElement(By.xpath("//button[@type='submit']")).click();

        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
        } finally {
            // Close the browser only if testing is complete
            // driver.quit();
        }
    }

    public class SelectSizeOption {

        // Method to select the "S" size option
        public void selectSizeS() {
            // Wait until the size options are visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> sizeOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='swatch-attribute size']//div[@role='option']")));

            // Loop through the options to find and select "S"
            for (WebElement sizeOption : sizeOptions) {
                String size = sizeOption.getText().trim();
                if (size.equalsIgnoreCase("S")) {
                    sizeOption.click();
                    System.out.println("Successfully selected the size 'S'.");
                    break;
                }
            }
        }
    }


}






