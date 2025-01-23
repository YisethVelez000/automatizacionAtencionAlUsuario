package casos;

import conexion.Conexion_BD;
import conexion.ingresoPruebas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.time.Duration;
import java.util.List;

public class gestionarCaso {
    private WebDriver driver;
    private Connection conexion;
    private ingresoPruebas ingreso;

    @BeforeEach
    public void setUp(){
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
    public void gestionarCaso(){
        ingreso.iniciarSesion();
        esperar(200);
        driver.get("http://10.250.3.66:8080/savia/atencionusuario/casos.faces");
        seleccionarGesion();
        esperar(100);
        caso();
        esperar(100);
        servicio();
    }

    private void seleccionarGesion() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        int indexCaso = (int) (Math.random() * 29) + 1;

        WebElement caso = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#frmCasos\\:tablaRegistros\\:" + indexCaso + "\\:j_idt63")));
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + caso.getLocation().y + ")");
        caso.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

    }

    private void caso() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        int indexInstruccion = (int) (Math.random() * 2);

        driver.findElement(By.cssSelector("#frmGestion\\:instruccion")).click();
        esperar(200);

        driver.findElement(By.cssSelector("#frmGestion\\:instruccion_" + indexInstruccion)).click();

        int indexReabierto = (int) (Math.random() * 2);

        driver.findElement(By.cssSelector("#frmGestion\\:reabierto")).click();
        esperar(200);

        driver.findElement(By.cssSelector("#frmGestion\\:reabierto_" + indexReabierto)).click();

        int indexFalloTutela = (int) (Math.random() * 2);

        driver.findElement(By.cssSelector("#frmGestion\\:falloTutela")).click();
        esperar(200);

        driver.findElement(By.cssSelector("#frmGestion\\:falloTutela_" + indexFalloTutela)).click();

        int indexRedireccionado = (int) (Math.random() * 2);

        driver.findElement(By.cssSelector("#frmGestion\\:redireccionado")).click();
        esperar(200);

        driver.findElement(By.cssSelector("#frmGestion\\:redireccionado_" + indexRedireccionado)).click();

        int indexUsuarioPluripatologico = (int) (Math.random() * 2);

        driver.findElement(By.cssSelector("#frmGestion\\:usuarioPluripatologico")).click();
        esperar(200);

        driver.findElement(By.cssSelector("#frmGestion\\:usuarioPluripatologico_" + indexUsuarioPluripatologico)).click();

        int indexProteccionDatos = (int) (Math.random() * 2);

        driver.findElement(By.cssSelector("#frmGestion\\:protecciónDatos")).click();
        esperar(200);

        driver.findElement(By.cssSelector("#frmGestion\\:protecciónDatos_" + indexProteccionDatos)).click();

        descargarAdjuntoCaso();
    }

    private void descargarAdjuntoCaso(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Nos desplazamos a la sección de anexos
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + driver.findElement(By.cssSelector("#frmGestion\\:pGestionAdjuntoCaso_header")).getLocation().y + ")");
        List<WebElement> adjuntos = driver.findElements(By.cssSelector("#frmGestion\\tablaAnexosCasos\\0\\j_idt2101"));

        if (adjuntos.size() >= 0) {
            driver.findElement(By.cssSelector("#frmGestion\\:tablaAnexosCasos\\:0\\:j_idt2101")).click();
            System.out.println("Descargando adjunto...");
        }
    }

    private void servicio(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.cssSelector("#frmGestion\\:pGestionServicio_header")).click();
        driver.findElement(By.cssSelector("#frmGestion\\:tablaServicios\\:j_idt2108")).click();
        esperar(200);

        driver.findElement(By.cssSelector("#frmCrearServicio\\:estadoCrear")).click();
        List<WebElement> estados = driver.findElements(By.cssSelector("#frmCrearServicio\\:estadoCrear_items > li"));

        int indexEstado = (int) (Math.random() * estados.size());

        if (indexEstado == 0) {
            indexEstado = 1;
        }

        driver.findElement(By.cssSelector("#frmCrearServicio\\:estadoCrear_"+indexEstado)).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearServicio\\:ambitoCrear")).click();

        List<WebElement> ambitos = driver.findElements(By.cssSelector("#frmCrearServicio\\:ambitoCrear_items > li"));

        int indexAmbito = (int) (Math.random() * ambitos.size());

        if (indexAmbito == 0) {
            indexAmbito = 1;
        }

        driver.findElement(By.cssSelector("#frmCrearServicio\\:ambitoCrear_" + indexAmbito)).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        int indexTipoAdministrativo = (int) (Math.random() * 2);

        if (indexAmbito == 1 && indexEstado != 2 ){

            driver.findElement(By.cssSelector("#frmCrearServicio\\:tipoAdministrativo")).click();

            driver.findElement(By.cssSelector("#frmCrearServicio\\:tipoAdministrativo_" + indexTipoAdministrativo)).click();
        }

        int indexMedicamento = (int) (Math.random() * 2);

        if (indexMedicamento == 0) {
            indexMedicamento = 1;
        }

        driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamento")).click();

        driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamento_" + indexMedicamento)).click();

        int indexMedicamentoCobertura = (int) (Math.random() * 2);

        if (indexMedicamento == 2){

            driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamentoCobertura")).click();

            driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamentoCobertura_" + indexMedicamentoCobertura)).click();

        }

    }
}
