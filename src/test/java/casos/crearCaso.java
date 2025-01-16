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

import java.io.File;
import java.sql.Connection;
import java.time.Duration;
import java.util.List;

public class crearCaso {
    private WebDriver driver;
    private ingresoPruebas ingreso;
    private Connection conexion;

    @BeforeEach
    public void SetUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\apuertav\\IdeaProjects\\AtencionAlUsuario\\src\\test\\resources\\chromedriver.exe");
        driver = new org.openqa.selenium.chrome.ChromeDriver();
        ingreso = new ingresoPruebas(driver);

        configurarConexionBD();
    }

    public void configurarConexionBD() {
        Conexion_BD conexionBD = new Conexion_BD();
        conexion = conexionBD.conectar();

        if (conexion != null) {
            System.out.println("Conexión exitosa a la base de datos!");
        } else {
            System.out.println("Error al conectar a la base de datos");
        }
    }

    public void esperar(long tiempo) {
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void crearCaso() {
        ingreso.iniciarSesion();
        driver.get("http://10.250.3.66:8080/savia/atencionusuario/casos.faces");
        esperar(500);
        driver.findElement(By.id("frmCasos:j_idt57")).click();
        esperar(500);
        persona();
        driver.findElement(By.id("frmCrear:pnlPrograma_toggler")).click();
        esperar(500);
        crearContactos();
        esperar(500);
        caso();
        esperar(200);
        //agregarAdjuntoCaso();

    }

    private void persona() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String query = "SELECT  mae_tipo_documento_id  , mae_tipo_documento_valor , numero_documento, mae_genero_valor FROM aseg_afiliados aa ORDER BY RAND() LIMIT 1";

        String tipoDocumento = "";
        String numeroDocumento = "";
        String sexo = "";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                tipoDocumento = resultSet.getString("mae_tipo_documento_id");
                numeroDocumento = resultSet.getString("numero_documento");
                sexo = resultSet.getString("mae_genero_valor");
            }
            System.out.println("consulta exitosa");
        } catch (Exception e) {
            System.err.println("Error al consultar la base de datos: " + e.getMessage());
        }

        List<String> TiposDocumento = List.of("411", "0", "401", "412", "402", "410", "406", "413", "404", "408", "12.308", "405", "1", "414", "403");
        List<String> Generos = List.of("Masculino", "Femenino");

        boolean continuar = true;
        int index = 0;
        do {
            System.out.println("Ciclo: " + index);
            if (tipoDocumento.equals(TiposDocumento.get(index))) {
                System.out.println("Tipo de documento: " + tipoDocumento);
                continuar = false;
                if (tipoDocumento.equals("0")) {
                    index = 2;
                    continuar = false;
                } else if (tipoDocumento.equals("1")) {
                    index = 13;
                    continuar = false;
                }
            }
            if (index > TiposDocumento.size()) {
                continuar = false;
            }
            index = index + 1;
        } while (continuar);
        System.out.println(index);
        driver.findElement(By.id("frmCrear:tipoDocumento")).click();
        esperar(500);
        driver.findElement(By.id("frmCrear:tipoDocumento_" + index)).click();


        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("frmCrear:documento"))));
        driver.findElement(By.id("frmCrear:documento")).click();
        driver.findElement(By.id("frmCrear:documento")).sendKeys(numeroDocumento);

        driver.findElement(By.id("frmCrear:sexo")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        driver.findElement(By.id("frmCrear:sexo")).click();
        if (sexo.equals("Masculino")) {
            driver.findElement(By.id("frmCrear:sexo_2")).click();
        } else {
            driver.findElement(By.id("frmCrear:sexo_1")).click();
        }
    }

    private void crearContactos() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.id("frmCrear:telefonosPersonas:j_idt466")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        int telefono = (int) (Math.random() * 100000000) + 1;

        driver.findElement(By.id("frmCrearTelefono:numero")).sendKeys(String.valueOf(telefono));
        driver.findElement(By.id("frmCrearTelefono:descripcion")).sendKeys("Telefono de prueba automatizado");
        driver.findElement(By.id("frmCrearTelefono:j_idt1056")).click();

    }

    private void caso() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.id("frmCrear:tipoOrigen")).click();
        int index = (int) (Math.random() * 7) + 1;

        driver.findElement(By.id("frmCrear:tipoOrigen_" + index)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        esperar(500);

        driver.findElement(By.id("frmCrear:tiposolicitud")).click();
        index = (int) (Math.random() * 7) + 1;
        driver.findElement(By.id("frmCrear:tiposolicitud_" + index)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        esperar(500);

        driver.findElement(By.id("frmCrear:prioridad")).click();
        index = (int) (Math.random() * 3) + 1;
        driver.findElement(By.id("frmCrear:prioridad_" + index)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        esperar(500);

        driver.findElement(By.id("frmCrear:entrecontrol")).click();
        index = (int) (Math.random() * 13) + 1;
        driver.findElement(By.id("frmCrear:entrecontrol_" + index)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        esperar(500);

        driver.findElement(By.id("frmCrear:riesgovida")).click();
        index = (int) (Math.random() * 4) + 1;
        driver.findElement(By.id("frmCrear:riesgovida_" + index)).click();

        driver.findElement(By.id("frmCrear:observacion")).sendKeys("Caso de prueba automatizado");

        driver.findElement(By.id("frmCrear:ubicacionCaso")).click();
        index = (int) (Math.random() * 10) + 1;
        esperar(500);
        driver.findElement(By.id("frmCrear:ubicacionCaso_" + index)).click();

        driver.findElement(By.id("frmCrear:tecnologiaAltoCosto")).click();
        index = (int) (Math.random() * 10) + 1;
        esperar(500);
        driver.findElement(By.id("frmCrear:tecnologiaAltoCosto_" + index)).click();

        driver.findElement(By.id("frmCrear:motivo")).click();
        index = (int) (Math.random() * 10) + 1;
        esperar(500);
        driver.findElement(By.id("frmCrear:motivo_" + index)).click();
        esperar(500);


        index = (int) (Math.random() * 2) + 1;
        driver.findElement(By.id("frmCrear:usuarioPluripatologico")).click();
        esperar(500);
        driver.findElement(By.id("frmCrear:usuarioPluripatologico_" + index)).click();

        index = (int) (Math.random() * 2) + 1;
        esperar(500);
        driver.findElement(By.id("frmCrear:proteccionDatos")).click();
        esperar(500);
        driver.findElement(By.id("frmCrear:proteccionDatos_" + index)).click();

        index = (int) (Math.random() * 2) + 1;
        esperar(500);
        driver.findElement(By.id("frmCrear:instruccion")).click();
        esperar(500);
        driver.findElement(By.id("frmCrear:instruccion_" + index)).click();

        esperar(500);
        index = (int) (Math.random() * 2) + 1;
        driver.findElement(By.id("frmCrear:reabierto")).click();
        esperar(500);
        driver.findElement(By.id("frmCrear:reabierto_" + index)).click();

        esperar(500);
        index = (int) (Math.random() * 2) + 1;
        driver.findElement(By.id("frmCrear:falloTutela")).click();
        esperar(500);
        driver.findElement(By.id("frmCrear:falloTutela_" + index)).click();

        esperar(500);
        index = (int) (Math.random() * 2) + 1;
        driver.findElement(By.id("frmCrear:redireccionado")).click();
        esperar(500);
        driver.findElement(By.id("frmCrear:redireccionado_" + index)).click();





    }

    private void agregarAdjuntoCaso() {
        File uploadFile = new File("C:\\Users\\apuertav\\Downloads\\Contratacion_Prestadores_2-1_20230405140945909.pdf");
        WebElement uploadElement = driver.findElement(By.id("frmCrear:tablaAnexosCasos:inptAnexo_input"));
        uploadElement.sendKeys(uploadFile.getAbsolutePath());
        esperar(3500);
        driver.findElement(By.cssSelector("button.ui-fileupload-upload.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-icon-left")).click();
    }

}
