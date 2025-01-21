package casos;

import conexion.Conexion_BD;
import conexion.ingresoPruebas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.sql.Connection;
import java.time.Duration;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ingreso.iniciarSesion();
        driver.get("http://10.250.3.66:8080/savia/atencionusuario/casos.faces");
        esperar(500);
        driver.findElement(By.id("frmCasos:j_idt57")).click();
        esperar(500);
        persona();
        esperar(100);
        agregarContacto();
        esperar(100);
        caso();
        driver.findElement(By.cssSelector("#frmCrear\\:pCasoCrear_toggler")).click();
        esperar(100);
        servicio();


    }

    private void persona() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String query = "SELECT\n" +
                "mae_tipo_documento_valor ,\n" +
                "numero_documento,\n" +
                "mae_genero_codigo \n" +
                "FROM aseg_afiliados ORDER BY RAND() LIMIT 1 ";
        String tipoDocumento = "";
        String numeroDocumento = "";
        String genero = "";
        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                tipoDocumento = resultSet.getString("mae_tipo_documento_valor");
                numeroDocumento = resultSet.getString("numero_documento");
                genero = resultSet.getString("mae_genero_codigo");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los datos de la persona: " + e.getMessage());
        }

        driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento")).click();

        if (tipoDocumento.equals("Registro Civil")) {
            // Usamos javaScriptExecutor para desplazarnos hasta el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_12")));

            // Usamos javaScriptExecutor para hacer clic en el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_12")));

        } else if (tipoDocumento.equals("Cedula Ciudadania")) {
            // Usamos javaScriptExecutor para desplazarnos hasta el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_3")));

            // Usamos javaScriptExecutor para hacer clic en el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_3")));

        } else if (tipoDocumento.equals("Adulto sin Identificacion")) {
            // Usamos javaScriptExecutor para desplazarnos hasta el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_1")));

            // Usamos javaScriptExecutor para hacer clic en el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_1")));

        } else if (tipoDocumento.equals("Tarjeta Identidad")) {
            // Usamos javaScriptExecutor para desplazarnos hasta el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_15")));

            // Usamos javaScriptExecutor para hacer clic en el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_15")));

        } else if (tipoDocumento.equals("Cédula Extranjería")) {
            // Usamos javaScriptExecutor para desplazarnos hasta el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_5")));

            // Usamos javaScriptExecutor para hacer clic en el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_5")));

        } else if (tipoDocumento.equals("Menor sin Identificación")) {
            // Usamos javaScriptExecutor para desplazarnos hasta el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_6")));

            // Usamos javaScriptExecutor para hacer clic en el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_6")));

        } else if (tipoDocumento.equals("Certificado de nacido vivo")) {
            // Usamos javaScriptExecutor para desplazarnos hasta el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_4")));

            // Usamos javaScriptExecutor para hacer clic en el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_4")));

        } else if (tipoDocumento.equals("Permiso Especial Permanencia")) {
            // Usamos javaScriptExecutor para desplazarnos hasta el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_10")));

            // Usamos javaScriptExecutor para hacer clic en el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_10")));

        } else if (tipoDocumento.equals("Pasaporte")) {
            // Usamos javaScriptExecutor para desplazarnos hasta el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_12")));

            // Usamos javaScriptExecutor para hacer clic en el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_12")));

        } else if (tipoDocumento.equals("Permiso por Protección Temporal")) {
            // Usamos javaScriptExecutor para desplazarnos hasta el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_11")));

            // Usamos javaScriptExecutor para hacer clic en el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_11")));

        } else if (tipoDocumento.equals("Nuip")) {
            // Usamos javaScriptExecutor para desplazarnos hasta el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_8")));

            // Usamos javaScriptExecutor para hacer clic en el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_8")));

        } else if (tipoDocumento.equals("Salvoconducto")) {
            // Usamos javaScriptExecutor para desplazarnos hasta el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_13")));

            // Usamos javaScriptExecutor para hacer clic en el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_13")));

        } else if (tipoDocumento.equals("NIT")) {
            // Usamos javaScriptExecutor para desplazarnos hasta el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_9")));

            // Usamos javaScriptExecutor para hacer clic en el elemento

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_9")));

        }

        driver.findElement(By.cssSelector("#frmCrear\\:documento")).sendKeys(numeroDocumento);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        esperar(3000);
        driver.findElement(By.cssSelector("#frmCrear\\:sexo")).click();

        if (genero.equals("M")) {
            driver.findElement(By.cssSelector("#frmCrear\\:sexo_2")).click();
        } else if (genero.equals("F")) {
            driver.findElement(By.cssSelector("#frmCrear\\:sexo_1")).click();
        } else {
            driver.findElement(By.cssSelector("#frmCrear\\:sexo_3")).click();
        }

        driver.findElement(By.cssSelector("#frmCrear\\:pnlPrograma_toggler")).click();
    }

    private void agregarContacto() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String query = "SELECT telefono_movil\n" +
                "FROM aseg_afiliados\n" +
                "WHERE telefono_movil IS NOT NULL AND telefono_movil <> '' ORDER BY RAND() LIMIT 1\n";
        String telefono = "";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                telefono = resultSet.getString("telefono_movil");
                System.out.println("Telefono: " + telefono);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los datos de la persona: " + e.getMessage());
        }

        driver.findElement(By.cssSelector("#frmCrear\\:telefonosPersonas\\:j_idt466")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearTelefono\\:numero")).click();
        driver.findElement(By.cssSelector("#frmCrearTelefono\\:numero")).sendKeys(telefono);

        driver.findElement(By.cssSelector("#frmCrearTelefono\\:descripcion")).sendKeys("Telefono movil de prueba automatizada");

        driver.findElement(By.cssSelector("#frmCrearTelefono\\:j_idt1056")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

    }

    private void caso() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.cssSelector("#frmCrear\\:tipoOrigen")).click();

        esperar(200);
        List<WebElement> origenes = driver.findElements(By.cssSelector("#frmCrear\\:tipoOrigen_items > li"));
        int index = (int) (Math.random() * origenes.size());
        if (index == 0) {
            index = 1;
        }
        System.out.println("Origen: " + origenes.get(index).getText());
        origenes.get(index).click();
        esperar(500);

        if (index == 7) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
            esperar(500);
            int numeroRadicado = (int) (Math.random() * 1000000);
            driver.findElement(By.cssSelector("#frmCrear\\:numeroradicado")).sendKeys(String.valueOf(numeroRadicado));

            driver.findElement(By.cssSelector("#frmCrear\\:canalSuperSalud")).click();
            esperar(500);
            List<WebElement> canalesSuperSalud = driver.findElements(By.cssSelector("#frmCrear\\:canalSuperSalud_items > li"));
            int indexCanal = (int) (Math.random() * canalesSuperSalud.size());
            esperar(500);
            System.out.println("Canal SuperSalud: " + canalesSuperSalud.get(indexCanal).getText());
            canalesSuperSalud.get(indexCanal).click();
            esperar(500);
        }

        driver.findElement(By.cssSelector("#frmCrear\\:tiposolicitud")).click();
        esperar(200);
        List<WebElement> tiposSolicitud = driver.findElements(By.cssSelector("#frmCrear\\:tiposolicitud_items > li"));
        int indexTipoSolicitud = (int) (Math.random() * tiposSolicitud.size());
        if (indexTipoSolicitud == 0) {
            indexTipoSolicitud = 1;
        }
        System.out.println("Tipo Solicitud: " + tiposSolicitud.get(indexTipoSolicitud).getText());
        tiposSolicitud.get(indexTipoSolicitud).click();

        esperar(200);
        driver.findElement(By.cssSelector("#frmCrear\\:prioridad")).click();
        esperar(200);
        List<WebElement> prioridades = driver.findElements(By.cssSelector("#frmCrear\\:prioridad_items > li"));
        int indexPrioridad = (int) (Math.random() * prioridades.size());
        if (indexPrioridad == 0) {
            indexPrioridad = 1;
        }
        System.out.println("Prioridad: " + prioridades.get(indexPrioridad).getText());
        prioridades.get(indexPrioridad).click();

        esperar(200);
        driver.findElement(By.cssSelector("#frmCrear\\:entrecontrol")).click();
        esperar(200);
        List<WebElement> entreControles = driver.findElements(By.cssSelector("#frmCrear\\:entrecontrol_items > li"));
        int indexEntreControl = (int) (Math.random() * entreControles.size());
        if (indexEntreControl == 0) {
            indexEntreControl = 1;
        }
        System.out.println("Entre Control: " + entreControles.get(indexEntreControl).getText());

        //Nos desplazamos hasta el elemento
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", entreControles.get(indexEntreControl));
        //Hacemos clic en el elemento
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", entreControles.get(indexEntreControl));

        esperar(200);
        driver.findElement(By.cssSelector("#frmCrear\\:riesgovida")).click();
        esperar(200);
        List<WebElement> riesgosVida = driver.findElements(By.cssSelector("#frmCrear\\:riesgovida_items > li"));
        int indexRiesgoVida = (int) (Math.random() * riesgosVida.size());
        if (indexRiesgoVida == 0) {
            indexRiesgoVida = 1;
        }
        System.out.println("Riesgo Vida: " + riesgosVida.get(indexRiesgoVida).getText());
        // Nos desplazamos hasta el elemento
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", riesgosVida.get(indexRiesgoVida));
        // Hacemos clic en el elemento
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", riesgosVida.get(indexRiesgoVida));

        driver.findElement(By.cssSelector("#frmCrear\\:observacion")).sendKeys("Caso de prueba automatizada");

        driver.findElement(By.cssSelector("#frmCrear\\:ubicacionCaso")).click();
        esperar(200);
        List<WebElement> ubicaciones = driver.findElements(By.cssSelector("#frmCrear\\:ubicacionCaso_items > li"));
        esperar(200);
        int indexUbicacion = (int) (Math.random() * ubicaciones.size());
        if (indexUbicacion == 0) {
            indexUbicacion = 1;
        }
        System.out.println("Ubicacion: " + ubicaciones.get(indexUbicacion).getText());
        //Nos desplazamos hasta el elemento
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ubicaciones.get(indexUbicacion));
        //Hacemos clic en el elemento
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", ubicaciones.get(indexUbicacion));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        esperar(200);
        driver.findElement(By.cssSelector("#frmCrear\\:sedes")).click();
        esperar(200);
        List<WebElement> sedes = driver.findElements(By.cssSelector("#frmCrear\\:sedes_items > li"));
        int indexSede = (int) (Math.random() * sedes.size());
        System.out.println("Sede: " + sedes.get(indexSede).getText());
        //Nos desplazamos hasta el elemento
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sedes.get(indexSede));
        //Hacemos clic en el elemento
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sedes.get(indexSede));

        esperar(200);
        driver.findElement(By.cssSelector("#frmCrear\\:tecnologiaAltoCosto")).click();
        esperar(200);
        List<WebElement> tecnologias = driver.findElements(By.cssSelector("#frmCrear\\:tecnologiaAltoCosto_items > li"));
        int indexTecnologia = (int) (Math.random() * tecnologias.size());
        if (indexTecnologia == 0) {
            indexTecnologia = 1;
        }
        System.out.println("Tecnologia Alto Costo: " + tecnologias.get(indexTecnologia).getText());
        //Nos desplazamos hasta el elemento
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tecnologias.get(indexTecnologia));
        //Hacemos clic en el elemento
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tecnologias.get(indexTecnologia));

        esperar(200);
        driver.findElement(By.cssSelector("#frmCrear\\:motivo")).click();
        esperar(200);
        List<WebElement> motivos = driver.findElements(By.cssSelector("#frmCrear\\:motivo_items > li"));
        int indexMotivo = (int) (Math.random() * motivos.size());
        if (indexMotivo == 0) {
            indexMotivo = 1;
        }
        System.out.println("Motivo: " + motivos.get(indexMotivo).getText());
        //Nos desplazamos hasta el elemento
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", motivos.get(indexMotivo));
        //Hacemos clic en el elemento
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", motivos.get(indexMotivo));


        WebElement tipoMotivo = driver.findElement(By.cssSelector("#frmCrear\\:tipoMotivo"));
        String clase = tipoMotivo.getAttribute("class");
        System.out.println("Clase: " + clase);
        String activo = "ui-selectonemenu ui-widget ui-state-default ui-corner-all ui-state-disabled ";
        clase = tipoMotivo.getAttribute("class");
        System.out.println("Clase: " + clase);
        if (!Objects.equals(clase, activo)) {
            System.out.println("hay tipo de motivo");
            esperar(200);
            driver.findElement(By.cssSelector("#frmCrear\\:tipoMotivo")).click();
            esperar(200);
            List<WebElement> tiposMotivo = driver.findElements(By.cssSelector("#frmCrear\\:tipoMotivo_items > li"));
            int indexTipoMotivo = (int) (Math.random() * tiposMotivo.size());
            if (indexTipoMotivo == 0) {
                indexTipoMotivo = 1;
            }
            if (tiposMotivo.size() > 1) {
                System.out.println("Tipo Motivo: " + tiposMotivo.get(indexTipoMotivo).getText());
                //Nos desplazamos hasta el elemento
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tiposMotivo.get(indexTipoMotivo));
                //Hacemos clic en el elemento
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tiposMotivo.get(indexTipoMotivo));
            }
        } else {
            System.out.println("No hay tipo de motivo");
        }

        WebElement subMotivo = driver.findElement(By.cssSelector("#frmCrear\\:subtipoMotivo"));
        String claseSubMotivo = subMotivo.getAttribute("class");
        System.out.println("Clase: " + claseSubMotivo);
        String activoSubMotivo = "ui-selectonemenu ui-widget ui-state-default ui-corner-all ";
        if (Objects.equals(claseSubMotivo, activoSubMotivo)) {
            System.out.println("hay sub motivo");
            esperar(200);
            driver.findElement(By.cssSelector("#frmCrear\\:subtipoMotivo")).click();
            esperar(200);
            List<WebElement> subMotivos = driver.findElements(By.cssSelector("#frmCrear\\:subtipoMotivo_items > li"));
            int indexSubMotivo = (int) (Math.random() * subMotivos.size());
            if (subMotivos.size() > 1) {
                if (indexSubMotivo == 0) {
                    indexSubMotivo = 1;
                }
                System.out.println("Sub Motivo: " + subMotivos.get(indexSubMotivo).getText());
                //Nos desplazamos hasta el elemento
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", subMotivos.get(indexSubMotivo));
                //Hacemos clic en el elemento
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subMotivos.get(indexSubMotivo));
            }
        } else {
            System.out.println("No hay sub motivo");
        }

        driver.findElement(By.cssSelector("#frmCrear\\:usuarioPluripatologico")).click();
        esperar(200);
        int indexPluripatologico = (int) (Math.random() * 2);
        System.out.println("Pluripatologico: " + indexPluripatologico);
        if (indexPluripatologico == 0) {
            driver.findElement(By.cssSelector("#frmCrear\\:usuarioPluripatologico_1")).click();
        } else {
            driver.findElement(By.cssSelector("#frmCrear\\:usuarioPluripatologico_2")).click();
        }

        driver.findElement(By.cssSelector("#frmCrear\\:proteccionDatos")).click();
        esperar(200);
        int indexProteccionDatos = (int) (Math.random() * 2);
        System.out.println("Proteccion Datos: " + indexProteccionDatos);
        if (indexProteccionDatos == 0) {
            driver.findElement(By.cssSelector("#frmCrear\\:proteccionDatos_1")).click();
        } else {
            driver.findElement(By.cssSelector("#frmCrear\\:proteccionDatos_2")).click();
        }

        driver.findElement(By.cssSelector("#frmCrear\\:instruccion")).click();
        esperar(200);
        int indexInstruccion = (int) (Math.random() * 2);
        System.out.println("Instruccion: " + indexInstruccion);
        if (indexInstruccion == 0) {
            driver.findElement(By.cssSelector("#frmCrear\\:instruccion_1")).click();
        } else {
            driver.findElement(By.cssSelector("#frmCrear\\:instruccion_2")).click();
        }

        driver.findElement(By.cssSelector("#frmCrear\\:reabierto")).click();
        esperar(200);
        int indexReabierto = (int) (Math.random() * 2);
        System.out.println("Reabierto: " + indexReabierto);
        if (indexReabierto == 0) {
            driver.findElement(By.cssSelector("#frmCrear\\:reabierto_1")).click();
        } else {
            driver.findElement(By.cssSelector("#frmCrear\\:reabierto_2")).click();
        }

        driver.findElement(By.cssSelector("#frmCrear\\:falloTutela")).click();
        esperar(200);
        int indexFalloTutela = (int) (Math.random() * 2);
        System.out.println("Fallo Tutela: " + indexFalloTutela);
        if (indexFalloTutela == 0) {
            driver.findElement(By.cssSelector("#frmCrear\\:falloTutela_1")).click();
        } else {
            driver.findElement(By.cssSelector("#frmCrear\\:falloTutela_2")).click();
        }

        driver.findElement(By.cssSelector("#frmCrear\\:redireccionado")).click();
        esperar(200);
        int indexRedireccionado = (int) (Math.random() * 2);
        System.out.println("Redireccionado: " + indexRedireccionado);
        if (indexRedireccionado == 0) {
            driver.findElement(By.cssSelector("#frmCrear\\:redireccionado_1")).click();
        } else {
            driver.findElement(By.cssSelector("#frmCrear\\:redireccionado_2")).click();
        }

        agregarAdjuntoCaso();
    }

    private void agregarAdjuntoCaso() {
        //Navegamos hasta el elemento
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrear\\:tablaAnexosCasos\\:inptAnexo_input")));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        File uploadFile = new File("C:\\Users\\apuertav\\Downloads\\Contratacion_Prestadores_2-1_20230405140945909.pdf");
        WebElement adjunto =  driver.findElement(By.cssSelector("#frmCrear\\:tablaAnexosCasos\\:inptAnexo_input"));

        //Usamos javaScriptExecutor y enviamos la ruta del archivo
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", adjunto);
        adjunto.sendKeys(uploadFile.getAbsolutePath());
        esperar(4000);

        //Usamos JavaScriptExecutor para hacer clic en el botón de subir archivo
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector("#frmCrear\\:tablaAnexosCasos\\:inptAnexo > div.ui-fileupload-buttonbar.ui-widget-header.ui-corner-top > button.ui-fileupload-upload.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-icon-left")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

    }

    private void servicio(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.cssSelector("#frmCrear\\:tablaServicios\\:j_idt646")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearServicio\\:estadoCrear")).click();
        esperar(200);
        List<WebElement> estados = driver.findElements(By.cssSelector("#frmCrearServicio\\:estadoCrear_items > li"));
        int indexEstado = (int) (Math.random() * estados.size());
        if (indexEstado == 0) {
            indexEstado = 1;
        }
        System.out.println("Estado: " + estados.get(indexEstado).getText());
        estados.get(indexEstado).click();

        esperar(1000);
        driver.findElement(By.cssSelector("#frmCrearServicio\\:ambitoCrear")).click();
        esperar(200);
        List<WebElement> ambitos = driver.findElements(By.cssSelector("#frmCrearServicio\\:ambitoCrear_items > li"));
        int indexAmbito = (int) (Math.random() * ambitos.size());
        if (indexAmbito == 0) {
            indexAmbito = 1;
        }

        System.out.println("Ambito: " + ambitos.get(indexAmbito).getText());
        ambitos.get(indexAmbito).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        if (indexAmbito == 1 && indexEstado!= 2){

            driver.findElement(By.cssSelector("#frmCrearServicio\\:tipoAdministrativo")).click();
            int indexTipoAdministrativo = (int) (Math.random() * 2);
            System.out.println("Tipo Administrativo: " + indexTipoAdministrativo);
            if (indexTipoAdministrativo == 0) {
                indexTipoAdministrativo = 1;
            }
            if (indexTipoAdministrativo == 1) {
                driver.findElement(By.cssSelector("#frmCrearServicio\\:tipoAdministrativo_1")).click();
            } else {
                driver.findElement(By.cssSelector("#frmCrearServicio\\:tipoAdministrativo_2")).click();
            }

        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamento")).click();
        int indexAplicaMedicamento = (int) (Math.random() * 2);
        System.out.println("Aplica Medicamento: " + indexAplicaMedicamento);
        if (indexAplicaMedicamento ==0){
            indexAplicaMedicamento = 1;
        }
        if (indexAplicaMedicamento == 1) {
            driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamento_1")).click();
        } else {
            driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamento_2")).click();
            esperar(900);
            driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamentoCobertura")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        }

        if (indexEstado!=3 && indexAmbito!= 1){
            int index = (int) (Math.random() * 2);
            for (int i = 0; i < index; i++) {
                driver.findElement(By.cssSelector("#frmCrearServicio\\:btnAplicaProcedimiento")).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
                esperar(500);


            }
        }
        driver.findElement(By.cssSelector("#frmCrearServicio\\:btnEspecialidad")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        String query = "SELECT codigo  FROM ma_especialidades WHERE activo = \"1\" ORDER BY  RAND() LIMIT 1 ";
        String codigo = "";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                codigo = resultSet.getString("codigo");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los datos de la persona: " + e.getMessage());
        }

        driver.findElement(By.cssSelector("#frmEspecialidadBusqueda\\:tablaRegistrosEspecialidades\\:j_idt2469")).sendKeys(codigo);

        driver.findElement(By.cssSelector("#frmEspecialidadBusqueda\\:j_idt2466")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        List<WebElement> especialidades = driver.findElements(By.cssSelector("#frmEspecialidadBusqueda\\:tablaRegistrosEspecialidades_data > tr"));
        int indexEspecialidad = (int) (Math.random() * especialidades.size());
        System.out.println("Especialidad: " + especialidades.get(indexEspecialidad).getText());
        especialidades.get(indexEspecialidad).click();

        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechacumplimientoCrear_input")).click();
        esperar(200);
        List<WebElement> dias = driver.findElements(By.cssSelector("#ui-datepicker-div > table > tbody > tr:nth-child(4) > td.ui-datepicker-today > a"));
        int indexDia = (int) (Math.random() * dias.size());
        dias.get(indexDia).click();


        driver.findElement(By.cssSelector("#frmCrearServicio\\:descripcionCrear")).sendKeys("Descripcion de prueba automatizada");

        driver.findElement(By.cssSelector("#frmCrearServicio\\:btnCiex")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
         query = "SELECT codigo  FROM ma_diagnosticos md WHERE activo = \"1\" ORDER BY RAND() LIMIT 1";
         codigo = "";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                codigo = resultSet.getString("codigo");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los datos de la persona: " + e.getMessage());
        }

        driver.findElement(By.cssSelector("#frmDiagnosticoBusqueda\\:tablaRegistrosDiagnoticos\\:j_idt2456")).sendKeys(codigo);

        driver.findElement(By.cssSelector("#frmDiagnosticoBusqueda\\:j_idt2447")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        List<WebElement> diagnosticos = driver.findElements(By.cssSelector("#frmDiagnosticoBusqueda\\:tablaRegistrosDiagnoticos_data > tr"));
        int indexDiagnostico = (int) (Math.random() * diagnosticos.size());
        System.out.println("Diagnostico: " + diagnosticos.get(indexDiagnostico).getText());
        diagnosticos.get(indexDiagnostico).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearServicio\\:patologiaCrear")).click();
        esperar(200);
        List<WebElement> patologias = driver.findElements(By.cssSelector("#frmCrearServicio\\:patologiaCrear_items > li"));
        int indexPatologia = (int) (Math.random() * patologias.size());
        if (indexPatologia == 0) {
            indexPatologia = 1;
        }
        if (patologias.size() > 1) {
            System.out.println("Patologia: " + patologias.get(indexPatologia).getText());
            patologias.get(indexPatologia).click();
        }

        driver.findElement(By.cssSelector("#frmCrearServicio\\:servicioAtribuidoIPS")).click();
        esperar(200);
        List<WebElement> servicios = driver.findElements(By.cssSelector("#frmCrearServicio\\:servicioAtribuidoIPS_items > li"));
        int indexServicio = (int) (Math.random() * servicios.size());
        if (indexServicio == 0) {
            indexServicio = 1;
        }

        if (indexServicio > 1) {
            System.out.println("Servicio: " + servicios.get(indexServicio).getText());
            servicios.get(indexServicio).click();
        }

        driver.findElement(By.cssSelector("#frmCrearServicio\\:btnservicioDestino")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        List<WebElement> sedes = driver.findElements(By.cssSelector("#frmPrestadorIpsDestino\\:tablaRegistrosIpsDestino_data > tr"));
        int indexSede = (int) (Math.random() * sedes.size());
        System.out.println("Sede: " + sedes.get(indexSede).getText());
        sedes.get(indexSede).click();

        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechaInicioVigenciaCrear_input")).click();
        esperar(200);
        List<WebElement> diasInicio = driver.findElements(By.cssSelector("#ui-datepicker-div > table > tbody > tr:nth-child(4) > td.ui-datepicker-today > a"));
        int indexDiaInicio = (int) (Math.random() * diasInicio.size());
        diasInicio.get(indexDiaInicio).click();

        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechaFinVigenciaCrear_input")).click();
        esperar(200);
        List<WebElement> diasFin = driver.findElements(By.cssSelector("#ui-datepicker-div > table > tbody > tr:nth-child(4) > td.ui-datepicker-today > a"));
        int indexDiaFin = (int) (int) (Math.random() * diasFin.size());
        diasFin.get(indexDiaFin).click();

        adjuntosServicios();

        driver.findElement(By.cssSelector("#frmCrearServicio\\:j_idt1212")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
    }

    private void adjuntosServicios(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        File uploadFile = new File("C:\\Users\\apuertav\\Downloads\\Contratacion_Prestadores_2-1_20230405140945909.pdf");
        WebElement adjunto =  driver.findElement(By.cssSelector("#frmCrearServicio\\:tablaAnexosServicios\\:anexo4_input"));

        //Usamos javaScriptExecutor y enviamos la ruta del archivo
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", adjunto);
        adjunto.sendKeys(uploadFile.getAbsolutePath());
        esperar(4000);

        //Usamos JavaScriptExecutor para hacer clic en el botón de subir archivo
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector("#frmCrearServicio\\:tablaAnexosServicios\\:anexo4 > div.ui-fileupload-buttonbar.ui-widget-header.ui-corner-top > button.ui-fileupload-cancel.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-icon-left")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
    }
}