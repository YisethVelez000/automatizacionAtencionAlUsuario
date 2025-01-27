package casos;

import conexion.Conexion_BD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import conexion.ingresoPruebas;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.WriteAbortedException;
import java.sql.Connection;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class editarCaso {

    private WebDriver driver;

    private ingresoPruebas ingreso;

    private Connection conexion;

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
    public void editarCaso() {
        ingreso.iniciarSesion();
        driver.get("http://10.250.3.66:8080/savia/atencionusuario/casos.faces");
        esperar(100);
        esperar(200);
        driver.findElement(By.cssSelector("#frmCasos\\:tablaRegistros\\:j_idt99")).click();
        esperar(200);
        driver.findElement(By.cssSelector("#frmCasos\\:tablaRegistros\\:j_idt99_panel > div.ui-selectcheckboxmenu-items-wrapper > ul > li:nth-child(6) > label")).click();
        esperar(500);
        seleccionarCaso();
        esperar(200);
        persona();
        esperar(200);
        agregarTelefono();
        esperar(200);
        caso();


    }

    private void seleccionarCaso() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        int indexCaso = (int) (Math.random() * 29) + 1;

        WebElement caso = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#frmCasos\\:tablaRegistros\\:" + indexCaso + "\\:j_idt62")));
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + caso.getLocation().y + ")");
        caso.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

    }

    private void persona(){
        String documento = driver.findElement(By.cssSelector("#frmEditar\\:documento")).getAttribute("value");
        String query = "SELECT discapacidad, usuario_gestante, mae_estado_afiliacion_valor FROM aseg_afiliados aa WHERE numero_documento = '" + documento + "'";
        String discapacidad = "";
        String gestante = "";
        String estadoAfiliacion = "";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                discapacidad = resultSet.getString("discapacidad");
                gestante = resultSet.getString("usuario_gestante");
                estadoAfiliacion = resultSet.getString("mae_estado_afiliacion_valor");
                System.out.println("Discapacidad: " + discapacidad + " Gestante: " + gestante+ " Estado afiliación: " + estadoAfiliacion);
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la base de datos: " + e.getMessage());
        }

        if (discapacidad != null) {
            if (discapacidad.equals("1")) {
                driver.findElement(By.cssSelector("#frmEditar\\:discapacidad")).click();
                esperar(200);
                driver.findElement(By.cssSelector("#frmEditar\\:discapacidad_2")).click();
            } else if (discapacidad.equals("0")) {
                driver.findElement(By.cssSelector("#frmEditar\\:discapacidad")).click();
                esperar(200);
                driver.findElement(By.cssSelector("#frmEditar\\:discapacidad_1")).click();
            }
        } else {
            driver.findElement(By.cssSelector("#frmEditar\\:discapacidad")).click();
            esperar(200);
            driver.findElement(By.cssSelector("#frmEditar\\:discapacidad_1")).click();
        }

        esperar(500);
        if (gestante != null) {
            if (gestante.equals("1")) {
                driver.findElement(By.cssSelector("#frmEditar\\:gestante")).click();
                esperar(200);
                driver.findElement(By.cssSelector("#frmEditar\\:gestante_2")).click();
            } else if (gestante.equals("0")) {
                driver.findElement(By.cssSelector("#frmEditar\\:gestante")).click();
                esperar(200);
                driver.findElement(By.cssSelector("#frmEditar\\:gestante_1")).click();
            }
        } else {
            driver.findElement(By.cssSelector("#frmEditar\\:gestante")).click();
            esperar(200);
            driver.findElement(By.cssSelector("#frmEditar\\:gestante_1")).click();
        }

        driver.findElement(By.cssSelector("#frmEditar\\:estrato")).click();
        esperar(200);
        List<WebElement> estratos = driver.findElements(By.cssSelector("#frmEditar\\:estrato_items li"));
        int indexEstrato = (int) (Math.random() * estratos.size());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", estratos.get(indexEstrato));
        estratos.get(indexEstrato).click();



    }

    private void agregarTelefono() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Navegamos con JavaScriptExecutor hasta el boton agregar telefono
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmEditar\\:telefonos\\:j_idt773")));
        driver.findElement((By.cssSelector("#frmEditar\\:telefonos\\:j_idt773"))).click();

        String query = "SELECT telefono_movil\n" +
                "FROM aseg_afiliados\n" +
                "WHERE telefono_movil IS NOT NULL AND telefono_movil <> '' ORDER BY RAND() LIMIT 1\n";

        String telefono = "";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                telefono = resultSet.getString("telefono_movil");
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la base de datos: " + e.getMessage());
        }

        driver.findElement(By.cssSelector("#frmCrearTelefono\\:numero")).sendKeys(telefono);

        driver.findElement(By.cssSelector("#frmCrearTelefono\\:descripcion")).sendKeys("Telefono editado con automatización");

        driver.findElement(By.cssSelector("#frmCrearTelefono\\:j_idt1056")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
    }

    private void caso() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.cssSelector("#frmEditar\\:tipoorigen")).click();

        esperar(200);
        List<WebElement> origenes = driver.findElements(By.cssSelector("#frmEditar\\:tipoorigen_items > li"));
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
            driver.findElement(By.cssSelector("#frmEditar\\:numeroradicado")).sendKeys(String.valueOf(numeroRadicado));

            driver.findElement(By.cssSelector("#frmEditar\\:canalSuperSalud")).click();
            esperar(500);
            List<WebElement> canalesSuperSalud = driver.findElements(By.cssSelector("#frmEditar\\:canalSuperSalud_items > li"));
            int indexCanal = (int) (Math.random() * canalesSuperSalud.size());
            esperar(500);
            System.out.println("Canal SuperSalud: " + canalesSuperSalud.get(indexCanal).getText());
            canalesSuperSalud.get(indexCanal).click();
            esperar(500);
        }

        driver.findElement(By.cssSelector("#frmEditar\\:tiposolicitud")).click();
        esperar(200);
        List<WebElement> tiposSolicitud = driver.findElements(By.cssSelector("#frmEditar\\:tiposolicitud_items > li"));
        int indexTipoSolicitud = (int) (Math.random() * tiposSolicitud.size());
        if (indexTipoSolicitud == 0) {
            indexTipoSolicitud = 1;
        }
        System.out.println("Tipo Solicitud: " + tiposSolicitud.get(indexTipoSolicitud).getText());
        tiposSolicitud.get(indexTipoSolicitud).click();

        esperar(200);
        driver.findElement(By.cssSelector("#frmEditar\\:prioridad")).click();
        esperar(200);
        List<WebElement> prioridades = driver.findElements(By.cssSelector("#frmEditar\\:prioridad_items > li"));
        int indexPrioridad = (int) (Math.random() * prioridades.size());
        if (indexPrioridad == 0) {
            indexPrioridad = 1;
        }
        System.out.println("Prioridad: " + prioridades.get(indexPrioridad).getText());
        prioridades.get(indexPrioridad).click();

        esperar(200);
        driver.findElement(By.cssSelector("#frmEditar\\:entrecontrol")).click();
        esperar(200);
        List<WebElement> entreControles = driver.findElements(By.cssSelector("#frmEditar\\:entrecontrol_items > li"));
        int indexEntreControl = (int) (Math.random() * entreControles.size());
        if (indexEntreControl == 0) {
            indexEntreControl = 1;
        }
        System.out.println("Entre Control: " + entreControles.get(indexEntreControl).getText());


        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", entreControles.get(indexEntreControl));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", entreControles.get(indexEntreControl));

        esperar(200);
        driver.findElement(By.cssSelector("#frmEditar\\:riesgovida")).click();
        esperar(200);
        List<WebElement> riesgosVida = driver.findElements(By.cssSelector("#frmEditar\\:riesgovida_items > li"));
        int indexRiesgoVida = (int) (Math.random() * riesgosVida.size());
        if (indexRiesgoVida == 0) {
            indexRiesgoVida = 1;
        }
        System.out.println("Riesgo Vida: " + riesgosVida.get(indexRiesgoVida).getText());
        // Nos desplazamos hasta el elemento
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", riesgosVida.get(indexRiesgoVida));
        // Hacemos clic en el elemento
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", riesgosVida.get(indexRiesgoVida));

        driver.findElement(By.cssSelector("#frmEditar\\:ubicacionCaso")).click();
        esperar(200);
        List<WebElement> ubicaciones = driver.findElements(By.cssSelector("#frmEditar\\:ubicacionCaso_items > li"));
        esperar(200);
        int indexUbicacion = (int) (Math.random() * ubicaciones.size());
        if (indexUbicacion == 0) {
            indexUbicacion = 1;
        }
        System.out.println("Ubicacion: " + ubicaciones.get(indexUbicacion).getText());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ubicaciones.get(indexUbicacion));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", ubicaciones.get(indexUbicacion));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        esperar(200);
        driver.findElement(By.cssSelector("#frmEditar\\:sedes")).click();
        esperar(200);
        List<WebElement> sedes = driver.findElements(By.cssSelector("#frmEditar\\:sedes_items > li"));
        int indexSede = (int) (Math.random() * sedes.size());
        System.out.println("Sede: " + sedes.get(indexSede).getText());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sedes.get(indexSede));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sedes.get(indexSede));

        LocalDate fechaNotificacion = LocalDate.now().plusDays(3);



        esperar(200);
        driver.findElement(By.cssSelector("#frmEditar\\:tecnologiaAltoCosto")).click();
        esperar(200);
        List<WebElement> tecnologias = driver.findElements(By.cssSelector("#frmEditar\\:tecnologiaAltoCosto_items > li"));
        int indexTecnologia = (int) (Math.random() * tecnologias.size());
        if (indexTecnologia == 0) {
            indexTecnologia = 1;
        }

        System.out.println("Tecnologia Alto Costo: " + tecnologias.get(indexTecnologia).getText());
        driver.findElement(By.cssSelector("#frmEditar\\:fechanotificacion_input")).sendKeys(fechaNotificacion.toString());

        LocalDate fechaVencimiento = fechaNotificacion.plusDays(30);

        driver.findElement(By.cssSelector("#frmEditar\\:fechavencimiento_input")).sendKeys(fechaVencimiento.toString());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tecnologias.get(indexTecnologia));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tecnologias.get(indexTecnologia));

        esperar(200);
        driver.findElement(By.cssSelector("#frmEditar\\:motivo")).click();
        esperar(200);
        List<WebElement> motivos = driver.findElements(By.cssSelector("#frmEditar\\:motivo_items > li"));
        int indexMotivo = (int) (Math.random() * motivos.size());
        if (indexMotivo == 0) {
            indexMotivo = 1;
        }
        System.out.println("Motivo: " + motivos.get(indexMotivo).getText());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", motivos.get(indexMotivo));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", motivos.get(indexMotivo));


        if (indexMotivo != 38) {
            System.out.println("hay tipo de motivo");
            esperar(200);
            driver.findElement(By.cssSelector("#frmEditar\\:tipoMotivo")).click();
            esperar(200);
            List<WebElement> tiposMotivo = driver.findElements(By.cssSelector("#frmEditar\\:tipoMotivo_items > li"));
            int indexTipoMotivo = (int) (Math.random() * tiposMotivo.size());
            if (indexTipoMotivo == 0) {
                indexTipoMotivo = 1;
            }
            if (tiposMotivo.size() > 1) {
                System.out.println("Tipo Motivo: " + tiposMotivo.get(indexTipoMotivo).getText());

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tiposMotivo.get(indexTipoMotivo));
                tiposMotivo.get(indexTipoMotivo).click();
                esperar(200);
            }
        } else {
            System.out.println("No hay tipo de motivo");
        }

        if (indexMotivo != 38 ) {
            System.out.println("hay sub motivo");
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmEditar\\:subtipoMotivo")));
            esperar(200);
            driver.findElement(By.cssSelector("#frmEditar\\:subtipoMotivo")).click();
            esperar(200);
            List<WebElement> subMotivos = driver.findElements(By.cssSelector("#frmEditar\\:subtipoMotivo_items > li"));
            int indexSubMotivo = (int) (Math.random() * subMotivos.size());
            if (subMotivos.size() > 1) {
                if (indexSubMotivo == 0) {
                    indexSubMotivo = 1;
                }
                System.out.println("Sub Motivo: " + subMotivos.get(indexSubMotivo).getText());

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", subMotivos.get(indexSubMotivo));

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subMotivos.get(indexSubMotivo));
            }
        } else {
            System.out.println("No hay sub motivo");
        }

        driver.findElement(By.cssSelector("#frmEditar\\:usuarioPluripatologico")).click();
        esperar(200);
        int indexPluripatologico = (int) (Math.random() * 2);
        System.out.println("Pluripatologico: " + indexPluripatologico);
        if (indexPluripatologico == 0) {
            driver.findElement(By.cssSelector("#frmEditar\\:usuarioPluripatologico_0")).click();
            esperar(200);
        } else {
            driver.findElement(By.cssSelector("#frmEditar\\:usuarioPluripatologico_1")).click();
            esperar(200);
        }

        driver.findElement(By.cssSelector("#frmEditar\\:proteccionDatos")).click();
        esperar(200);
        int indexProteccionDatos = (int) (Math.random() * 2);
        System.out.println("Proteccion Datos: " + indexProteccionDatos);
        if (indexProteccionDatos == 0) {
            driver.findElement(By.cssSelector("#frmEditar\\:proteccionDatos_0")).click();
            esperar(200);
        } else {
            driver.findElement(By.cssSelector("#frmEditar\\:proteccionDatos_1")).click();
            esperar(200);
        }

        driver.findElement(By.cssSelector("#frmEditar\\:instruccion")).click();
        esperar(500);
        int indexInstruccion = (int) (Math.random() * 2);
        System.out.println("Instruccion: " + indexInstruccion);
        if (indexInstruccion == 0) {
            driver.findElement(By.cssSelector("#frmEditar\\:instruccion_0")).click();
            esperar(200);
        } else {
            driver.findElement(By.cssSelector("#frmEditar\\:instruccion_1")).click();
            esperar(200);
        }

        driver.findElement(By.cssSelector("#frmEditar\\:reabierto")).click();
        esperar(200);
        int indexReabierto = (int) (Math.random() * 2);
        System.out.println("Reabierto: " + indexReabierto);
        if (indexReabierto == 0) {
            driver.findElement(By.cssSelector("#frmEditar\\:reabierto_0")).click();
            esperar(200);
        } else {
            driver.findElement(By.cssSelector("#frmEditar\\:reabierto_1")).click();
            esperar(200);
        }

        driver.findElement(By.cssSelector("#frmEditar\\:falloTutela")).click();
        esperar(200);
        int indexFalloTutela = (int) (Math.random() * 2);
        System.out.println("Fallo Tutela: " + indexFalloTutela);
        if (indexFalloTutela == 0) {
            driver.findElement(By.cssSelector("#frmEditar\\:falloTutela_0")).click();
            esperar(200);
        } else {
            driver.findElement(By.cssSelector("#frmEditar\\:falloTutela_1")).click();
            esperar(200);
        }

        driver.findElement(By.cssSelector("#frmEditar\\:redireccionado")).click();
        esperar(200);
        int indexRedireccionado = (int) (Math.random() * 2);
        System.out.println("Redireccionado: " + indexRedireccionado);
        if (indexRedireccionado == 0) {
            driver.findElement(By.cssSelector("#frmEditar\\:redireccionado_0")).click();
            esperar(200);
        } else {
            driver.findElement(By.cssSelector("#frmEditar\\:redireccionado_1")).click();
            esperar(200);
        }

        agregarAdjuntoCaso();
    }

    private void agregarAdjuntoCaso(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        File uploadFile = new File("C:\\Users\\apuertav\\Downloads\\Contratacion_Prestadores_2-1_20230405140945909.pdf");

        //Nos movemos hasta el botón de adjuntar archivo
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmEditar\\:tablaAnexosCasos\\:inptAnexo_input")));

        WebElement adjunto = driver.findElement(By.cssSelector("#frmEditar\\:tablaAnexosCasos\\:inptAnexo_input"));

        adjunto.sendKeys(uploadFile.getAbsolutePath());

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmEditar\\:tablaAnexosCasos\\:inptAnexo > div.ui-fileupload-buttonbar.ui-widget-header.ui-corner-top > button.ui-fileupload-upload.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-icon-left")).click();

    }


}
