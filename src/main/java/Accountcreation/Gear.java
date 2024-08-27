package Accountcreation;

import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Gear {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    public void setup(){
        Registration registration = new Registration();
        registration.setUp();
        this.driver=Registration.driver;
        Sign_in signIn= new Sign_in();
        signIn.performSignIn("haripriya.g@panasatech.com","Hari@Luma");


    }
}
