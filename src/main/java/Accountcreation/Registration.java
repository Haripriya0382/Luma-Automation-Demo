package Accountcreation;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Assert;
import java.time.Duration;
import java.util.List;
public class Registration {

    protected static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://magento.softwaretestingboard.com/");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser and end the WebDriver session
        }
    }

            @Test
            public void testEmptyFieldsValidation() {
                // Navigate to the registration page
                driver.findElement(By.xpath("//a[text()='Create an Account']")).click();

                // Attempt to submit the form with empty fields
                WebElement submitButton = driver.findElement(By.xpath("//button//span[text()='Create an Account']"));
                submitButton.click();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                // Wait and assert for the First Name validation message
                WebElement firstNameValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='firstname-error']")));
                Assert.assertTrue("First name validation not displayed", firstNameValidation.isDisplayed());

                // Wait and assert for the Last Name validation message
                WebElement lastNameValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='lastname-error']")));
                Assert.assertTrue("Last name validation not displayed", lastNameValidation.isDisplayed());

                // Wait and assert for the Email validation message
                WebElement emailValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='email_address-error']")));
                Assert.assertTrue("Email validation not displayed", emailValidation.isDisplayed());

                // Wait and assert for the Password validation message
                WebElement passwordValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='password-error']")));
                Assert.assertTrue("Password validation not displayed", passwordValidation.isDisplayed());

                // Wait and assert for the Password Confirmation validation message
                WebElement confirmPasswordValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='password-confirmation-error']")));
                Assert.assertTrue("Password confirmation validation not displayed", confirmPasswordValidation.isDisplayed());
            }

        @Test
        public void testInvalidEmailValidation() {
            driver.findElement(By.xpath("//a[text()='Create an Account']")).click();

            // Fill other fields with valid data
            driver.findElement(By.xpath("//input[@id='firstname']")).sendKeys("Haripriya");
            driver.findElement(By.xpath("//input[@id='lastname']")).sendKeys("G");

            // Enter invalid email
            driver.findElement(By.xpath("//input[@id='email_address']")).sendKeys("harigmail.com");

            // Attempt to submit the form
            driver.findElement(By.xpath("//button//span[text()='Create an Account']")).click();

            // Use WebDriverWait to wait for the email validation message to appear
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement emailValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='mage-error' and contains(text(), 'Please enter a valid email address')]")));

            // Assert that the email validation message is displayed
            Assert.assertTrue("Email validation not displayed", emailValidation.isDisplayed());
            Assert.assertEquals("Please enter a valid email address (Ex: johndoe@domain.com).", emailValidation.getText().trim());
        }
        @Test
        public void testWeakPasswordValidation() {
            driver.findElement(By.xpath("//a[text()='Create an Account']")).click();

            // Fill other fields with valid data
            driver.findElement(By.xpath("//input[@id='firstname']")).sendKeys("Haripriya");
            driver.findElement(By.xpath("//input[@id='lastname']")).sendKeys("G");
            driver.findElement(By.xpath("//input[@id='email_address']")).sendKeys("haripriya.g@panasatech.com");

            // Enter weak password
            driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123");

            // Attempt to submit the form
            driver.findElement(By.xpath("//button//span[text()='Create an Account']")).click();

            // Use WebDriverWait to wait for the password validation message
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement passwordValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='password-error']")));

            // Assert that the password validation message is displayed
            Assert.assertTrue("Password validation not displayed", passwordValidation.isDisplayed());
            Assert.assertEquals("Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.", passwordValidation.getText());
        }


                    @Test
                    public void testMismatchedPasswordValidation() {
                        driver.findElement(By.xpath("//a[text()='Create an Account']")).click();

                        // Fill other fields with valid data
                        driver.findElement(By.xpath("//input[@id='firstname']")).sendKeys("Haripriya");
                        driver.findElement(By.xpath("//input[@id='lastname']")).sendKeys("G");
                        driver.findElement(By.xpath("//input[@id='email_address']")).sendKeys("haripriya.g@panasatech.com");
                        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Hari@Luma123");

                        // Enter different confirmation password
                        driver.findElement(By.xpath("//input[@id='password-confirmation']")).sendKeys("DifferentPassword");

                        // Submit the form using the correct submit button
                        driver.findElement(By.xpath("//button//span[text()='Create an Account']")).click();
                        // Use WebDriverWait to wait for the validation message
                        // Assert password strength validation
                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    // Assert validation message is displayed
                    WebElement confirmPasswordValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='password-confirmation-error']")));
                    Assert.assertTrue("Password confirmation validation not displayed", confirmPasswordValidation.isDisplayed());
                }


        @Test
        public void testEdgeCases() {
            // Navigate to the registration page
            driver.findElement(By.xpath("//a[text()='Create an Account']")).click();

            // Fill fields with edge case data
            driver.findElement(By.xpath("//input[@id='firstname']")).sendKeys("NameWith1234567890SpecialChars!@#$%^&*()_+");
            driver.findElement(By.xpath("//input[@id='lastname']")).sendKeys("G");
            driver.findElement(By.xpath("//input[@id='email_address']")).sendKeys("rohith.krishnan@panasatech.com");
            driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Hari@Luma123");
            driver.findElement(By.xpath("//input[@id='password-confirmation']")).sendKeys("Hari@Luma123");

            // Attempt to submit the form
            driver.findElement(By.xpath("//button//span[text()='Create an Account']")).click();

            // Use WebDriverWait to wait for the first name validation message
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement firstNameValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'First Name is not valid!')]")));

            // Assert that the first name validation message is displayed
            Assert.assertTrue("First Name validation not displayed", firstNameValidation.isDisplayed());
        }


            @Test
            public void testDuplicateEmailValidation() {
                // Navigate to the registration page
                driver.findElement(By.xpath("//a[text()='Create an Account']")).click();

                // Fill all fields with valid data but with a duplicate email
                driver.findElement(By.xpath("//input[@id='firstname']")).sendKeys("Haripriya");
                driver.findElement(By.xpath("//input[@id='lastname']")).sendKeys("G");
                driver.findElement(By.xpath("//input[@id='email_address']")).sendKeys("haripriya.g@panasatech.com");
                driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Hari@Luma123");
                driver.findElement(By.xpath("//input[@id='password-confirmation']")).sendKeys("Hari@Luma123");

                // Attempt to submit the form
                driver.findElement(By.xpath("//button//span[text()='Create an Account']")).click();

                // Use WebDriverWait to wait for the email validation error message
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement emailValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'There is already an account with this email address')]")));

                // Assert that the email validation message is displayed
                Assert.assertTrue("Duplicate email validation not displayed", emailValidation.isDisplayed());
            }
            @Test
            public void testSuccessfulRegistration() {
                driver.findElement(By.xpath("//a[text()='Create an Account']")).click();

                // Fill all fields with valid data
                driver.findElement(By.xpath("//input[@id='firstname']")).sendKeys("Haripriya");
                driver.findElement(By.xpath("//input[@id='lastname']")).sendKeys("G");
                driver.findElement(By.xpath("//input[@id='email_address']")).sendKeys("ramshad.m@panasatech.com");
                driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Hari@Luma123");
                driver.findElement(By.xpath("//input[@id='password-confirmation']")).sendKeys("Hari@Luma123");

                // Submit the form
                driver.findElement(By.xpath("//button//span[text()='Create an Account']")).click();

                // Use WebDriverWait to wait for the success message to be visible
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'message-success')]")));

                // Assert that the success message is displayed
                Assert.assertTrue("Registration not successful", successMessage.isDisplayed());
            }
        }











