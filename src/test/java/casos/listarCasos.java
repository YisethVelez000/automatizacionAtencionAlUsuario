package casos;

import conexion.Conexion_BD;
import conexion.ingresoPruebas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.time.Duration;
import java.util.List;

public class listarCasos {
    private WebDriver driver;
    private ingresoPruebas ingreso;
    private Connection conexion;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\apuertav\\IdeaProjects\\AtencionAlUsuario\\src\\test\\resources\\chromedriver.exe");
        driver = new org.openqa.selenium.chrome.ChromeDriver();
        ingreso = new ingresoPruebas(driver);

        configurarConexionBD();
    }

    private void configurarConexionBD(){
        Conexion_BD conexionDB = new Conexion_BD();
        conexion = conexionDB.conectar();

        if (conexion != null) {
            System.out.println("Conexion a la base de datos exitosa");
        } else {
            System.err.println("Error al establecer la conexión a la base de datos. ");
        }
    }

    private void esperar(long tiempo) {
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listarCasos(){
        ingreso.iniciarSesion();
        esperar(2000);
        driver.get("http://10.250.3.66:8080/savia/atencionusuario/casos.faces");
        esperar(200);
        numeroSolicitud();
        esperar(200);
        serviciosResultos();
        esperar(200);
        radicado();
        esperar(200);
        fechaCreacionCaso();
        esperar(200);
        tipoDocumento();
        esperar(200);
        numeroDocumento();
        esperar(200);
        nombres();
        esperar(200);
        apellidos();
        esperar(200);
        estadoSolicitud();
        esperar(200);
    }

    private void numeroSolicitud() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        int numeroSolictud = (int) (Math.random() * 1000000) + 1;
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt72")).sendKeys(String.valueOf(numeroSolictud));
        esperar(200);
        driver.findElement(By.id("frmCasos:j_idt56")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        esperar(2000);
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt72")).clear();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
    }

    private void serviciosResultos() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt74")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        esperar(2000);
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt74")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

    }

    private void radicado() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String query = "SELECT radicado  FROM aus_casos ac WHERE radicado != \"0\" ORDER BY RAND() LIMIT 1";
        String radicado = "";
        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                radicado = resultSet.getString("radicado");
                System.out.println("Radicado: " + radicado);
            }
        } catch (Exception e) {
            System.err.println("Error al consultar la base de datos: " + e.getMessage());
        }

        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt80")).sendKeys(radicado);
        driver.findElement(By.id("frmCasos:j_idt56")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        esperar(2000);
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt80")).clear();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
    }

    private void fechaCreacionCaso() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt82")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt82")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
    }

    private void tipoDocumento(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt85")).click();
        int index = (int) (Math.random() * 15) + 1;
        esperar(1000);
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt85_"+index)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        esperar(2000);
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt85")).click();
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt85_0")).click();
    }

    private void numeroDocumento(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String numeroDocumento = "";
        String query = "SELECT numero_documento FROM aseg_afiliados WHERE mae_estado_afiliacion_valor = \"Activo\" ORDER BY RAND() LIMIT 1";
        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                numeroDocumento = resultSet.getString("numero_documento");
                System.out.println("Numero de documento: " + numeroDocumento);
            }
        } catch (Exception e) {
            System.err.println("Error al consultar la base de datos: " + e.getMessage());
        }

        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt90")).sendKeys(numeroDocumento);
        driver.findElement(By.id("frmCasos:j_idt56")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        esperar(2000);
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt90")).clear();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
    }

    private void nombres(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String query = "SELECT CONCAT(primer_nombre, ' ',segundo_nombre) as nombres FROM aseg_afiliados WHERE mae_estado_afiliacion_valor = \"Activo\" ORDER BY RAND() LIMIT 1";
        String nombres = "";
        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                nombres = resultSet.getString("nombres");
                System.out.println("Nombres: " + nombres);
            }
        } catch (Exception e) {
            System.err.println("Error al consultar la base de datos: " + e.getMessage());
        }
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt93")).sendKeys(nombres);
        driver.findElement(By.id("frmCasos:j_idt56")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        esperar(2000);
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt93")).clear();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
    }

    private void apellidos(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String query = "SELECT CONCAT(primer_apellido, ' ',segundo_apellido) as apellidos FROM aseg_afiliados WHERE mae_estado_afiliacion_valor = \"Activo\" ORDER BY RAND() LIMIT 1";
        String apellidos = "";
        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                apellidos = resultSet.getString("apellidos");
                System.out.println("Apellidos: " + apellidos);
            }
        } catch (Exception e) {
            System.err.println("Error al consultar la base de datos: " + e.getMessage());
        }

        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt96")).sendKeys(apellidos);
        driver.findElement(By.id("frmCasos:j_idt56")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        esperar(2000);
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt96")).clear();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));


    }

    private void estadoSolicitud(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt99")).click();
        esperar(2000);
        int index = (int) (Math.random() * 7) + 0;
        List<WebElement> estados = driver.findElements(By.cssSelector("ui-selectcheckboxmenu-item.ui-selectcheckboxmenu-list-item.ui-corner-all.ui-selectcheckboxmenu-checked"));
        estados.getFirst().click();
    }
}
