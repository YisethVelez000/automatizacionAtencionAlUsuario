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
        seleccionarCaso();
        esperar(100);
        persona();
        esperar(100);
        agregarTelefono();
        esperar(100);
        caso();
        esperar(100);
        servicio();

    }

    private void seleccionarCaso() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        int indexCaso = (int) (Math.random() * 29) + 1;

        WebElement caso = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#frmCasos\\:tablaRegistros\\:" + indexCaso + "\\:j_idt62")));
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + caso.getLocation().y + ")");
        caso.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

    }

    private void persona() {
        String documento = driver.findElement(By.cssSelector("#frmEditar\\:documento")).getAttribute("value");
        String query = "SELECT discapacidad, usuario_gestante FROM aseg_afiliados aa WHERE numero_documento = '" + documento + "'";
        String discapacidad = "";
        String gestante = "";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                discapacidad = resultSet.getString("discapacidad");
                gestante = resultSet.getString("usuario_gestante");
                System.out.println("Discapacidad: " + discapacidad + " Gestante: " + gestante);
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
                "WHERE telefono_movil IS NOT NULL AND telefono_movil <> ''";

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
        List<WebElement> origenes = driver.findElements(By.cssSelector("#frmEditar\\:tipoorigen_items li"));
        int indexOrigen = (int) (Math.random() * origenes.size());

        if (indexOrigen == 0) {
            indexOrigen = 1;
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", origenes.get(indexOrigen));

        origenes.get(indexOrigen).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmEditar\\:tiposolicitud")).click();
        esperar(500);
        driver.findElement(By.cssSelector("#frmEditar\\:tiposolicitud")).click();
        List<WebElement> solicitudes = driver.findElements(By.cssSelector("#frmEditar\\:tiposolicitud_items li"));
        esperar(200);
        int indexSolicitud = (int) (Math.random() * solicitudes.size());

        System.out.println("Index solicitud: " + indexSolicitud);
        System.out.println("Tamaño de la lista de solicitudes: " + solicitudes.size());

        if (indexSolicitud == 0) {
            indexSolicitud = 1;
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", solicitudes.get(indexSolicitud));
        solicitudes.get(indexSolicitud).click();

        if (indexOrigen == 7) {
            int numeroRadicado = (int) (Math.random() * 1000000000);
            driver.findElement(By.cssSelector("#frmEditar\\:numeroradicado")).sendKeys(String.valueOf(numeroRadicado));

            driver.findElement(By.cssSelector("#frmEditar\\:canalSuperSalud")).click();
            esperar(200);
            List<WebElement> canales = driver.findElements(By.cssSelector("#frmEditar\\:canalSuperSalud_items li"));
            int indexCanal = (int) (Math.random() * canales.size());

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", canales.get(indexCanal));
            canales.get(indexCanal).click();
        }

        driver.findElement(By.cssSelector("#frmEditar\\:prioridad")).click();
        esperar(200);
        List<WebElement> prioridades = driver.findElements(By.cssSelector("#frmEditar\\:prioridad_items li"));
        int indexPrioridad = (int) (Math.random() * prioridades.size());

        if (indexPrioridad == 0) {
            indexPrioridad = 1;
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", prioridades.get(indexPrioridad));
        prioridades.get(indexPrioridad).click();

        driver.findElement(By.cssSelector("#frmEditar\\:entrecontrol")).click();
        esperar(200);
        List<WebElement> entes = driver.findElements(By.cssSelector("#frmEditar\\:entrecontrol_items li"));
        int indexEnte = (int) (Math.random() * entes.size());

        if (indexEnte == 0) {
            indexEnte = 1;
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", entes.get(indexEnte));
        entes.get(indexEnte).click();

        driver.findElement(By.cssSelector("#frmEditar\\:j_idt844")).click();

        int indexPertinencia = (int) (Math.random() * 2) + 1;
        for (int i = 0; i < indexPertinencia; i++) {
            driver.findElement(By.cssSelector("#frmEditar\\:pertinenciaCasoEditar")).click();
        }

        LocalDate fechaNotificacion = LocalDate.now();
        int dia = (int) (Math.random() * 600) + 1;
        fechaNotificacion = fechaNotificacion.plusDays(dia);
        System.out.println("Fecha de notificación: " + fechaNotificacion);

        driver.findElement(By.cssSelector("#frmEditar\\:fechanotificacion_input")).clear();
        driver.findElement(By.cssSelector("#frmEditar\\:fechanotificacion_input")).sendKeys(fechaNotificacion.toString());

        driver.findElement(By.cssSelector("#frmEditar\\:j_idt844")).click();

        driver.findElement(By.cssSelector("#frmEditar\\:fechavencimiento_input")).clear();
        LocalDate fechaVencimiento = fechaNotificacion.plusDays(30);
        driver.findElement(By.cssSelector("#frmEditar\\:fechavencimiento_input")).sendKeys(fechaVencimiento.toString());

        driver.findElement(By.cssSelector("#frmEditar\\:ubicacionCaso")).click();
        esperar(200);
        List<WebElement> ubicaciones = driver.findElements(By.cssSelector("#frmEditar\\:ubicacionCaso_items li"));
        esperar(200);
        int indexUbicacion = (int) (Math.random() * ubicaciones.size());

        if (indexUbicacion == 0) {
            indexUbicacion = 1;
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ubicaciones.get(indexUbicacion));
        ubicaciones.get(indexUbicacion).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        driver.findElement(By.cssSelector("#frmEditar\\:sedes")).click();
        esperar(200);
        List<WebElement> sedes = driver.findElements(By.cssSelector("#frmEditar\\:sedes_items li"));
        int indexSede = (int) (Math.random() * sedes.size());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sedes.get(indexSede));
        sedes.get(indexSede).click();

        driver.findElement(By.cssSelector("#frmEditar\\:j_idt844")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmEditar\\:responsable_button")).click();
        esperar(500);
        List<WebElement> responsables = driver.findElements(By.cssSelector("#frmEditar\\:responsable_panel > ul > li"));
        int indexResponsable = (int) (Math.random() * responsables.size());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", responsables.get(indexResponsable));
        responsables.get(indexResponsable).click();

        int indexInstruccion = (int) (Math.random() * 1);
        driver.findElement(By.cssSelector("#frmEditar\\:instruccion")).click();
        esperar(200);
        driver.findElement(By.cssSelector("#frmEditar\\:instruccion_"+ indexInstruccion)).click();

        int indexReabierto = (int) (Math.random() * 1);
        driver.findElement(By.cssSelector("#frmEditar\\:reabierto")).click();
        esperar(200);
        driver.findElement(By.cssSelector("#frmEditar\\:reabierto_"+ indexReabierto)).click();

        int indexFalloTutela = (int) (Math.random() * 1);
        driver.findElement(By.cssSelector("#frmEditar\\:falloTutela")).click();
        esperar(200);
        driver.findElement(By.cssSelector("#frmEditar\\:falloTutela_"+ indexFalloTutela)).click();

        int indexRedireccionado = (int) (Math.random() * 1);
        driver.findElement(By.cssSelector("#frmEditar\\:redireccionado")).click();
        esperar(200);
        driver.findElement(By.cssSelector("#frmEditar\\:redireccionado_"+ indexRedireccionado)).click();

        driver.findElement(By.cssSelector("#frmEditar\\:tecnologiaAltoCosto")).click();
        esperar(200);
        List<WebElement> tecnologias = driver.findElements(By.cssSelector("#frmEditar\\:tecnologiaAltoCosto_items li"));
        int indexTecnologia = (int) (Math.random() * tecnologias.size());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tecnologias.get(indexTecnologia));
        tecnologias.get(indexTecnologia).click();

        driver.findElement(By.cssSelector("#frmEditar\\:motivo")).click();
        esperar(200);
        List<WebElement> motivos = driver.findElements(By.cssSelector("#frmEditar\\:motivo_items li"));
        int indexMotivo = (int) (Math.random() * motivos.size());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", motivos.get(indexMotivo));
        motivos.get(indexMotivo).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmEditar\\:tipoMotivo")).click();
        esperar(200);
        List<WebElement> tiposMotivo = driver.findElements(By.cssSelector("#frmEditar\\:tipoMotivo_items li"));
        int indexTipoMotivo = (int) (Math.random() * tiposMotivo.size());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tiposMotivo.get(indexTipoMotivo));
        tiposMotivo.get(indexTipoMotivo).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmEditar\\:subtipoMotivo")).click();
        esperar(200);
        List<WebElement> subtiposMotivo = driver.findElements(By.cssSelector("#frmEditar\\:subtipoMotivo_items li"));
        int indexSubtipoMotivo = (int) (Math.random() * subtiposMotivo.size());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", subtiposMotivo.get(indexSubtipoMotivo));
        subtiposMotivo.get(indexSubtipoMotivo).click();

        int pluripatologico = (int) (Math.random() * 2) + 1;
        driver.findElement(By.cssSelector("#frmEditar\\:usuarioPluripatologico")).click();
        esperar(200);
        driver.findElement(By.cssSelector("#frmEditar\\:usuarioPluripatologico_" + pluripatologico)).click();

        int proteccionDatos = (int) (Math.random() * 2) + 1;
        driver.findElement(By.cssSelector("#frmEditar\\:proteccionDatos")).click();
        esperar(200);
        driver.findElement(By.cssSelector("#frmEditar\\:proteccionDatos_" + proteccionDatos)).click();

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

    private void servicio(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmEditar\\:tablaServicios\\:j_idt971")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearServicio\\:estadoCrear")).click();
        esperar(200);
        List<WebElement> estados = driver.findElements(By.cssSelector("#frmCrearServicio\\:estadoCrear_items li"));
        int indexEstado = (int) (Math.random() * estados.size());

        if (indexEstado == 0) {
            indexEstado = 1;
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", estados.get(indexEstado));
        estados.get(indexEstado).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearServicio\\:ambitoCrear")).click();
        esperar(200);

        List<WebElement> ambitos = driver.findElements(By.cssSelector("#frmCrearServicio\\:ambitoCrear_items li"));
        int indexAmbito = (int) (Math.random() * ambitos.size());

        if (indexAmbito == 0) {
            indexAmbito = 1;
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ambitos.get(indexAmbito));
        ambitos.get(indexAmbito).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        if (indexAmbito == 1) {
            driver.findElement(By.cssSelector("#frmCrearServicio\\:tipoAdministrativo")).click();
            esperar(200);

            List<WebElement> tiposAdministrativos = driver.findElements(By.cssSelector("#frmCrearServicio\\:tipoAdministrativo_items li"));
            int indexTipoAdministrativo = (int) (Math.random() * tiposAdministrativos.size());

            if (indexTipoAdministrativo == 0) {
                indexTipoAdministrativo = 1;
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tiposAdministrativos.get(indexTipoAdministrativo));
            tiposAdministrativos.get(indexTipoAdministrativo).click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        }
        int indexAplicaMedicamento = (int) (Math.random() * 2) + 1;
        driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamento")).click();
        esperar(200);
        driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamento_" + indexAplicaMedicamento)).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        if ((indexAplicaMedicamento == 2)){
            int indexMedicamentoCobertura = (int) (Math.random() * 2) + 1;
            driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamentoCobertura")).click();
            esperar(200);

            driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamentoCobertura_" + indexMedicamentoCobertura)).click();
        }

        int indexAplicaProcedimiento = (int) (Math.random() * 2) + 1;

        for (int i = 0; i < indexAplicaProcedimiento; i++) {
            driver.findElement(By.cssSelector("#frmCrearServicio\\:btnAplicaProcedimiento")).click();
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearServicio\\:btnEspecialidad")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        String query = "SELECT codigo  FROM ma_especialidades WHERE activo = \"1\" ORDER BY  RAND() LIMIT 1 \n";
        String codigo = "";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                codigo = resultSet.getString("codigo");
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la base de datos: " + e.getMessage());
        }

        driver.findElement(By.cssSelector("#frmEspecialidadBusqueda\\:tablaRegistrosEspecialidades\\:j_idt2469")).sendKeys(codigo);

        driver.findElement(By.cssSelector("#frmEspecialidadBusqueda\\:j_idt2466")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmEspecialidadBusqueda\\:tablaRegistrosEspecialidades_data > tr:nth-child(1)")).click();

        LocalDate fechaCumplimiento = LocalDate.now();
        int dia = (int) (Math.random() * 30) + 1;
        fechaCumplimiento = fechaCumplimiento.plusDays(dia);

        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechacumplimientoCrear_input")).clear();
        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechacumplimientoCrear_input")).sendKeys(fechaCumplimiento.toString());

    }
}
