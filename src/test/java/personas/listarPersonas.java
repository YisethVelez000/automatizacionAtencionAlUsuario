package personas;

import conexion.Conexion_BD;
import conexion.ingresoPruebas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.time.Duration;
import java.util.List;

public class listarPersonas {
    private WebDriver driver;
    private Connection conexion;
    private ingresoPruebas ingreso;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\apuertav\\IdeaProjects\\AtencionAlUsuario\\src\\test\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        ingreso = new ingresoPruebas(driver);

        configurarConexion();
    }

    private void configurarConexion() {
        Conexion_BD conexionBD = new Conexion_BD();
        conexion = conexionBD.conectar();

        if (conexion != null) {
            System.out.println("Conexión exitosa!");
        } else {
            System.out.println("Error al conectar a la base de datos");
        }
    }

    private long esperar(long tiempo) {
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return tiempo;
    }

    @Test
    public void listarPersonas() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        ingreso.iniciarSesion();
        driver.get("http://10.250.3.66:8080/savia/atencionusuario/personas.faces");

        String query = "SELECT documento  FROM aus_personas ap ORDER BY RAND() LIMIT 1 ";
        String documento = "";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                documento = resultSet.getString("documento");
            }
        } catch (Exception e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }

        driver.findElement(By.cssSelector("#frmPersonas\\:tablaRegistros\\:j_idt53")).click();
        driver.findElement(By.cssSelector("#frmPersonas\\:tablaRegistros\\:j_idt53")).sendKeys(documento);

        driver.findElement(By.cssSelector("#frmPersonas\\:j_idt42")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        esperar(2000);

        driver.findElement(By.cssSelector("#frmPersonas\\:tablaRegistros\\:j_idt53")).clear();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmPersonas\\:tablaRegistros\\:j_idt56")).click();

        esperar(200);

        List<WebElement> estados = driver.findElements(By.cssSelector("#frmPersonas\\:tablaRegistros\\:j_idt56_items > li"));
        int indexEstado = (int) (Math.random() * estados.size());

        estados.get(indexEstado).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        esperar(1000);

        driver.findElement(By.cssSelector("#frmPersonas\\:tablaRegistros\\:j_idt56")).click();

        estados.get(0).click();

        String nombres = "";

        query = "SELECT nombres  FROM aus_personas ap ORDER BY RAND() LIMIT 1 ";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                nombres = resultSet.getString("nombres");
            }
        } catch (Exception e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }

        driver.findElement(By.cssSelector("#frmPersonas\\:tablaRegistros\\:j_idt61")).click();
        driver.findElement(By.cssSelector("#frmPersonas\\:tablaRegistros\\:j_idt61")).sendKeys(nombres);

        driver.findElement(By.cssSelector("#frmPersonas\\:j_idt42")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        esperar(2000);

        driver.findElement(By.cssSelector("#frmPersonas\\:tablaRegistros\\:j_idt61")).clear();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));


        String apellidos = "";
        query = "SELECT apellidos  FROM aus_personas ap ORDER BY RAND() LIMIT 1 ";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                apellidos = resultSet.getString("apellidos");
            }
        } catch (Exception e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }

        driver.findElement(By.cssSelector("#frmPersonas\\:tablaRegistros\\:j_idt64")).click();
        driver.findElement(By.cssSelector("#frmPersonas\\:tablaRegistros\\:j_idt64")).sendKeys(apellidos);

        driver.findElement(By.cssSelector("#frmPersonas\\:j_idt42")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        esperar(2000);

        driver.findElement(By.cssSelector("#frmPersonas\\:tablaRegistros\\:j_idt64")).clear();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmPersonas\\:tablaRegistros\\:j_idt67")).click();
        esperar(200);

        List<WebElement> tiposDocumento = driver.findElements(By.cssSelector("#frmPersonas\\:tablaRegistros\\:j_idt67_items > li"));
        int indexTipoDocumento = (int) (Math.random() * tiposDocumento.size());

        tiposDocumento.get(indexTipoDocumento).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        esperar(1000);

        driver.findElement(By.cssSelector("#frmPersonas\\:tablaRegistros\\:j_idt67")).click();

        tiposDocumento.get(0).click();

    }
}
