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

import java.awt.event.WindowAdapter;
import java.lang.ref.PhantomReference;
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
        origen();
        esperar(200);
        riesgoDeVida();
        esperar(200);
        responsable();
        esperar(200);
        responsableCasoCerrado();
        esperar(200);
        fechaHoraResponsable();
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
        int cantidad = (int) (Math.random() * 5) + 1;
        List<WebElement> elementosLi = driver.findElements(By.cssSelector("div#frmCasos\\:tablaRegistros\\:j_idt99_panel ul.ui-selectcheckboxmenu-items li.ui-selectcheckboxmenu-item"));
        for (int i = 0; i < cantidad; i++) {
            int index = (int) (Math.random() * elementosLi.size());
            elementosLi.get(index).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        }

        WebElement primerElemento = driver.findElement(By.cssSelector("div.ui-chkbox.ui-widget"));
        primerElemento.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
    }

    private void origen(){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt103")).click();
        esperar(3000);
        int cantidad = (int) (Math.random() * 5) + 1;
        List<WebElement> elementosLi = driver.findElements(By.cssSelector("div#frmCasos\\:tablaRegistros\\:j_idt103_panel ul.ui-selectcheckboxmenu-items li.ui-selectcheckboxmenu-item"));
        for (int i = 0; i < cantidad; i++) {
            int index = (int) (Math.random() * elementosLi.size());
            elementosLi.get(index).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
            elementosLi.get(index).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        }


    }

    private void riesgoDeVida(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt107")).click();
        esperar(2000);
        int cantidad = (int) (Math.random() * 5) + 1;
        List<WebElement> elementosLi = driver.findElements(By.cssSelector("div#frmCasos\\:tablaRegistros\\:j_idt107_panel ul.ui-selectcheckboxmenu-items li.ui-selectcheckboxmenu-item"));
        for (int i = 0; i < cantidad; i++) {
            int index = (int) (Math.random() * elementosLi.size());
            elementosLi.get(index).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
            elementosLi.get(index).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        }

    }

    private void responsable(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String query= "SELECT c.gn_usuarios_responsable_id, p.nombre  FROM aus_casos c JOIN gn_usuarios p ON c.gn_usuarios_responsable_id = p.id ORDER BY RAND() LIMIT 1 \n";
        String responsable = "";
        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                responsable = resultSet.getString("nombre");
                System.out.println("Responsable: " + responsable);
            }
        } catch (Exception e) {
            System.err.println("Error al consultar la base de datos: " + e.getMessage());
        }

        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt111")).sendKeys(responsable);
        driver.findElement(By.id("frmCasos:j_idt57")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        esperar(2000);
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt111")).clear();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

    }

    private void responsableCasoCerrado(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String query= "SELECT c.gn_usuarios_responsable_id, p.nombre  FROM aus_casos c JOIN gn_usuarios p ON c.gn_usuarios_responsable_id = p.id ORDER BY RAND() LIMIT 1 \n";
        String responsable = "";
        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                responsable = resultSet.getString("nombre");
                System.out.println("Responsable: " + responsable);
            }
        } catch (Exception e) {
            System.err.println("Error al consultar la base de datos: " + e.getMessage());
        }

        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt114")).sendKeys(responsable);
        driver.findElement(By.id("frmCasos:j_idt57")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        esperar(2000);
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt114")).clear();
    }

    private void fechaHoraResponsable(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt116")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        esperar(2000);
        driver.findElement(By.id("frmCasos:tablaRegistros:j_idt116")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
    }

    @Test
    public  void opcionesFuncionales() {
        ingreso.iniciarSesion();
        esperar(2000);
        driver.get("http://10.250.3.66:8080/savia/atencionusuario/casos.faces");
        verDetalle();
        esperar(200);
        historialServicios();
        esperar(200);
        cerrarCaso();

    }

    private void verDetalle(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCasos\\:tablaRegistros\\:0\\:j_idt61")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        esperar(2000);

        driver.get("http://10.250.3.66:8080/savia/atencionusuario/casos.faces");
    }

    private void historialServicios(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCasos\\:tablaRegistros\\:0\\:j_idt65")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        esperar(2000);

        driver.get("http://10.250.3.66:8080/savia/atencionusuario/casos.faces");
    }

    private void cerrarCaso(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCasos\\:tablaRegistros\\:j_idt99")).click();
        esperar(500);

        driver.findElement(By.cssSelector("#frmCasos\\:tablaRegistros\\:j_idt99_panel > div.ui-selectcheckboxmenu-items-wrapper > ul > li:nth-child(7)")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCasos\\:tablaRegistros\\:0\\:j_idt67")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCerrarCaso\\:motivoCerrarCaso")).click();

        List<WebElement> motivos = driver.findElements(By.cssSelector("#frmCerrarCaso\\:motivoCerrarCaso_items > li"));

        int indexMotivo = (int) (Math.random() * motivos.size());

        if (indexMotivo == 0) {
            indexMotivo = 1;
        }

        motivos.get(indexMotivo).click();

        driver.findElement(By.cssSelector("#frmCerrarCaso\\:comentarioCerrarCaso")).sendKeys("Caso cerrado por pruebas automatizadas");

        driver.findElement(By.cssSelector("#frmCerrarCaso\\:j_idt2432")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
    }
}
