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

import java.io.File;
import java.sql.Connection;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class gestionarCaso {
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
    public void gestionarCaso() {
        ingreso.iniciarSesion();
        esperar(200);
        driver.get("http://10.250.3.66:8080/savia/atencionusuario/casos.faces");
        esperar(200);
        driver.findElement(By.cssSelector("#frmCasos\\:tablaRegistros\\:j_idt99")).click();
        esperar(200);
        driver.findElement(By.cssSelector("#frmCasos\\:tablaRegistros\\:j_idt99_panel > div.ui-selectcheckboxmenu-items-wrapper > ul > li:nth-child(1) > label")).click();
        esperar(200);
        seleccionarGesion();
        esperar(100);
        caso();
        esperar(100);
        servicio();
        esperar(100);
        seguimiento();
        esperar(100);
        //descargarAdjuntoCaso();
        driver.findElement(By.cssSelector("#frmGestion\\:j_idt2157")).click();

    }

    private void seleccionarGesion() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

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
    }

    private void descargarAdjuntoCaso() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Nos desplazamos a la sección de anexos
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + driver.findElement(By.cssSelector("#frmGestion\\:pGestionAdjuntoCaso_header")).getLocation().y + ")");
        List<WebElement> adjuntos = driver.findElements(By.cssSelector("#frmGestion\\tablaAnexosCasos\\0\\j_idt2101"));

        if (adjuntos.size() >= 0) {
            driver.findElement(By.cssSelector("#frmGestion\\:tablaAnexosCasos\\:0\\:j_idt2101")).click();
            System.out.println("Descargando adjunto...");
        }
    }

    private void servicio() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.cssSelector("#frmGestion\\:pGestionServicio_header")).click();
        driver.findElement(By.cssSelector("#frmGestion\\:tablaServicios\\:j_idt2108")).click();
        esperar(200);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearServicio\\:estadoCrear")).click();
        List<WebElement> estados = driver.findElements(By.cssSelector("#frmCrearServicio\\:estadoCrear_items > li"));

        int indexEstado = (int) (Math.random() * estados.size());

        if (indexEstado == 0) {
            indexEstado = 1;
        }

        driver.findElement(By.cssSelector("#frmCrearServicio\\:estadoCrear_" + indexEstado)).click();

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

        if (indexAmbito == 1 && indexEstado != 2) {

            driver.findElement(By.cssSelector("#frmCrearServicio\\:tipoAdministrativo")).click();

            driver.findElement(By.cssSelector("#frmCrearServicio\\:tipoAdministrativo_" + indexTipoAdministrativo)).click();
        }

        int indexMedicamento = (int) (Math.random() * 2);

        if (indexMedicamento == 0) {
            indexMedicamento = 1;
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamento")).click();

        driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamento_" + indexMedicamento)).click();

        int indexMedicamentoCobertura = (int) (Math.random() * 2);

        if (indexMedicamento == 2) {

            driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamentoCobertura")).click();

            driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamentoCobertura_" + indexMedicamentoCobertura)).click();

        }

        String query = "SELECT codigo  FROM ma_especialidades WHERE activo = \"1\" ORDER BY  RAND() LIMIT 1 \n";
        String codigoEspecialidad = "";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                codigoEspecialidad = resultSet.getString("codigo");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener el código de la especialidad: " + e.getMessage());
        }

        driver.findElement(By.cssSelector("#frmCrearServicio\\:btnEspecialidad")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmEspecialidadBusqueda\\:tablaRegistrosEspecialidades\\:j_idt2469")).sendKeys(codigoEspecialidad);

        driver.findElement(By.cssSelector("#frmEspecialidadBusqueda\\:j_idt2466")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        List<WebElement> especialidades = driver.findElements(By.cssSelector("#frmEspecialidadBusqueda\\:tablaRegistrosEspecialidades_data > tr"));

        int indexEspecialidad = (int) (Math.random() * especialidades.size());

        if (especialidades.size() > 0) {

            especialidades.get(indexEspecialidad).click();
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        LocalDate fechaCumplimiento = LocalDate.now().plusDays(1);

        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechacumplimientoCrear_input")).sendKeys(fechaCumplimiento.toString());

        driver.findElement(By.cssSelector("#frmCrearServicio\\:descripcionCrear")).click();
        driver.findElement(By.cssSelector("#frmCrearServicio\\:descripcionCrear")).sendKeys("Descripción de prueba automatizada en la opcion funcional gestionar ");

        query = "SELECT codigo  FROM ma_diagnosticos md WHERE activo = \"1\" ORDER BY RAND() LIMIT 1 \n";
        String codigoDiagnostico = "";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                codigoDiagnostico = resultSet.getString("codigo");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener el código del diagnóstico: " + e.getMessage());
        }
        driver.findElement(By.cssSelector("#frmCrearServicio\\:btnCiex")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmDiagnosticoBusqueda\\:tablaRegistrosDiagnoticos\\:j_idt2456")).sendKeys(codigoDiagnostico);

        driver.findElement(By.cssSelector("#frmDiagnosticoBusqueda\\:j_idt2447")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        List<WebElement> diagnosticos = driver.findElements(By.cssSelector("#frmDiagnosticoBusqueda\\:tablaRegistrosDiagnoticos_data > tr"));

        int indexDiagnostico = (int) (Math.random() * diagnosticos.size());

        if (diagnosticos.size() > 0) {
            diagnosticos.get(indexDiagnostico).click();
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearServicio\\:patologiaCrear")).click();
        esperar(200);

        List<WebElement> patologias = driver.findElements(By.cssSelector("#frmCrearServicio\\:patologiaCrear_items > li"));

        int indexPatologia = (int) (Math.random() * patologias.size());

        if (indexPatologia == 0) {
            indexPatologia = 1;
        }

        driver.findElement(By.cssSelector("#frmCrearServicio\\:patologiaCrear_" + indexPatologia)).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearServicio\\:servicioAtribuidoIPS")).click();

        List<WebElement> ips = driver.findElements(By.cssSelector("#frmCrearServicio\\:servicioAtribuidoIPS_items > li"));

        int indexIPS = (int) (Math.random() * ips.size());

        if (indexIPS == 0) {
            indexIPS = 1;
        }

        driver.findElement(By.cssSelector("#frmCrearServicio\\:servicioAtribuidoIPS_" + indexIPS)).click();

        driver.findElement(By.cssSelector("#frmCrearServicio\\:btnservicioDestino")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        List<WebElement> sedes = driver.findElements(By.cssSelector("#frmPrestadorIpsDestino\\:tablaRegistrosIpsDestino_data > tr"));

        int indexSede = (int) (Math.random() * sedes.size());

        if (sedes.size() > 0) {
            sedes.get(indexSede).click();
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        LocalDate fechaInicioVigencia = LocalDate.now().plusDays(1);

        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechaInicioVigenciaCrear_input")).sendKeys(fechaInicioVigencia.toString());

        LocalDate fechaFinVigencia = fechaInicioVigencia.plusDays(30);

        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechaFinVigenciaCrear_input")).sendKeys(fechaFinVigencia.toString());

        driver.findElement(By.cssSelector("#frmCrearServicio\\:pCrearServicioTecnologias_header")).click();

        agregarAdjuntosServicios();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + driver.findElement(By.cssSelector("#frmCrearServicio\\:j_idt1212")).getLocation().y + ")");
        driver.findElement(By.cssSelector("#frmCrearServicio\\:j_idt1212")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

    }

    private void agregarAdjuntosServicios(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        File file = new File("C:\\Users\\apuertav\\Downloads\\Atencion-al-usuario_Casos_2-3_20240614170656036 (1).pdf");

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + driver.findElement(By.cssSelector("#frmCrearServicio\\:tablaAnexosServicios\\:anexo4_input")).getLocation().y + ")");

        WebElement adjunto = driver.findElement(By.cssSelector("#frmCrearServicio\\:tablaAnexosServicios\\:anexo4_input"));

        adjunto.sendKeys(file.getAbsolutePath());

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearServicio\\:tablaAnexosServicios\\:anexo4 > div.ui-fileupload-buttonbar.ui-widget-header.ui-corner-top > button.ui-fileupload-upload.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-icon-left")).click();
    }

    private void seguimiento(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.cssSelector("#frmGestion\\:tablaSeguimientos\\:j_idt2134")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearSeguimiento\\:estadoSeguimiento")).click();

        List<WebElement> estados = driver.findElements(By.cssSelector("#frmCrearSeguimiento\\:estadoSeguimiento_items > li"));

        int indexEstado = (int) (Math.random() * estados.size());

        if (indexEstado == 0) {
            indexEstado = 1;
        }

        estados.get(indexEstado).click();

        driver.findElement(By.cssSelector("#frmCrearSeguimiento\\:observacionSeg")).sendKeys("Observación de prueba automatizada en la opción funcional gestionar");

        agregarAdjuntoSeguimiento();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearSeguimiento\\:j_idt1831")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

    }

    private void agregarAdjuntoSeguimiento(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        File file = new File("C:\\Users\\apuertav\\Downloads\\Atencion-al-usuario_Casos_2-3_20240614170656036 (1).pdf");

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + driver.findElement(By.cssSelector("#frmCrearSeguimiento\\:tablaAnexosSeguimiento\\:inptAnexo_input")).getLocation().y + ")");
        driver.findElement(By.cssSelector("#frmCrearSeguimiento\\:tablaAnexosSeguimiento\\:inptAnexo_input")).sendKeys(file.getAbsolutePath());

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearSeguimiento\\:tablaAnexosSeguimiento\\:inptAnexo > div.ui-fileupload-buttonbar.ui-widget-header.ui-corner-top > button.ui-fileupload-upload.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-icon-left")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));


    }

}
