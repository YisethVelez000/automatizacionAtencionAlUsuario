package conexion;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

public class ingresoPruebas {
    private WebDriver driver;

    public ingresoPruebas(WebDriver driver) {
        this.driver = driver;
    }

    public void iniciarSesion(){
        driver.get("http://10.250.3.66:8080/savia/login.faces");
        // Cuando uso dos pantallas
        driver.manage().window().setPosition(new Point(-1600,0));
        esperar(500);
        driver.manage().window().maximize();
        esperar(500);
        driver.findElement(By.cssSelector("input#login\\:usuario")).sendKeys("apuertve");
        esperar(500);
        driver.findElement(By.cssSelector("input#login\\:contrasena")).sendKeys("1018227558iD.");
        esperar(500);
        driver.findElement(By.cssSelector("button[name='login:j_idt23']")).click();
        esperar(2000);

    }
    private void esperar(long tiempo) {
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
