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
        esperar(500);
        agregarContacto();
        esperar(500);
        caso();
        esperar(500);
        servicio();

    }

    private void persona(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String query = "SELECT mae_tipo_documento_valor, numero_documento, mae_genero_codigo FROM aseg_afiliados ORDER BY RAND() LIMIT 1";
        String tipoDocumento = "";
        String numeroDocumento = "";
        String genero = "";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                tipoDocumento = rs.getString("mae_tipo_documento_valor");
                numeroDocumento = rs.getString("numero_documento");
                genero = rs.getString("mae_genero_codigo");
            }
        }catch (Exception e){
            System.err.println("Error al obtener los datos de la persona: " + e.getMessage());
        }

        driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento")).click();

        List<String> tiposDocumento = List.of("Adulto sin Identificacion", "Carné Diplomático", "Cedula Ciudadania", "Certificado de nacido vivo", "Cédula Extranjería", "Menor sin Identificación", "NIT", "Nuip","Pasaporte", "Permiso Especial Permanencia","Permiso por Protección Temporal","Registro Civil","RUT","Salvoconducto","Tarjeta Identidad");

        driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento")).click();

        esperar(500);

        System.out.println("Tipo de documento : " + tipoDocumento);

        System.out.println("Tipo de documento tamaño : " + tiposDocumento.size());
        for (int i = 0; i < tiposDocumento.size(); i++) {
            System.out.println("Tipo de documento : " + tiposDocumento.get(i));
            if (tiposDocumento.get(i).equals(tipoDocumento)) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_" + (i + 1) )));

                driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento_" + (i+1) )).click();
                break;
            }
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        esperar(500);

        driver.findElement(By.cssSelector("#frmCrear\\:documento")).click();
        driver.findElement(By.cssSelector("#frmCrear\\:documento")).sendKeys(numeroDocumento);

        esperar(200);

        driver.findElement(By.cssSelector("#frmCrear\\:panelPersonaCrear_content > div:nth-child(2) > div:nth-child(3)")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrear\\:sexo")).click();

        esperar(200);

        if (genero.equals("M")) {
            driver.findElement(By.cssSelector("#frmCrear\\:sexo_2")).click();

            driver.findElement(By.cssSelector("#frmCrear\\:gestante")).click();

            esperar(200);

            driver.findElement(By.cssSelector("#frmCrear\\:gestante_1")).click();

        } else if (genero.equals("F")) {
            driver.findElement(By.cssSelector("#frmCrear\\:sexo_1")).click();
        } else {
            driver.findElement(By.cssSelector("#frmCrear\\:sexo_3")).click();
        }
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


        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", entreControles.get(indexEntreControl));

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

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ubicaciones.get(indexUbicacion));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", ubicaciones.get(indexUbicacion));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        esperar(200);
        driver.findElement(By.cssSelector("#frmCrear\\:sedes")).click();
        esperar(200);
        List<WebElement> sedes = driver.findElements(By.cssSelector("#frmCrear\\:sedes_items > li"));
        int indexSede = (int) (Math.random() * sedes.size());
        System.out.println("Sede: " + sedes.get(indexSede).getText());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sedes.get(indexSede));

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

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tecnologias.get(indexTecnologia));

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

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", motivos.get(indexMotivo));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", motivos.get(indexMotivo));


        if (indexMotivo != 38) {
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

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tiposMotivo.get(indexTipoMotivo));
                tiposMotivo.get(indexTipoMotivo).click();
                esperar(200);
            }
        } else {
            System.out.println("No hay tipo de motivo");
        }

        if (indexMotivo != 38 ) {
            System.out.println("hay sub motivo");
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrear\\:subtipoMotivo")));
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

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", subMotivos.get(indexSubMotivo));

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
            esperar(200);
        } else {
            driver.findElement(By.cssSelector("#frmCrear\\:usuarioPluripatologico_2")).click();
            esperar(200);
        }

        driver.findElement(By.cssSelector("#frmCrear\\:proteccionDatos")).click();
        esperar(200);
        int indexProteccionDatos = (int) (Math.random() * 2);
        System.out.println("Proteccion Datos: " + indexProteccionDatos);
        if (indexProteccionDatos == 0) {
            driver.findElement(By.cssSelector("#frmCrear\\:proteccionDatos_1")).click();
            esperar(200);
        } else {
            driver.findElement(By.cssSelector("#frmCrear\\:proteccionDatos_2")).click();
            esperar(200);
        }

        driver.findElement(By.cssSelector("#frmCrear\\:instruccion")).click();
        esperar(200);
        int indexInstruccion = (int) (Math.random() * 2);
        System.out.println("Instruccion: " + indexInstruccion);
        if (indexInstruccion == 0) {
            driver.findElement(By.cssSelector("#frmCrear\\:instruccion_1")).click();
            esperar(200);
        } else {
            driver.findElement(By.cssSelector("#frmCrear\\:instruccion_2")).click();
            esperar(200);
        }

        driver.findElement(By.cssSelector("#frmCrear\\:reabierto")).click();
        esperar(200);
        int indexReabierto = (int) (Math.random() * 2);
        System.out.println("Reabierto: " + indexReabierto);
        if (indexReabierto == 0) {
            driver.findElement(By.cssSelector("#frmCrear\\:reabierto_1")).click();
            esperar(200);
        } else {
            driver.findElement(By.cssSelector("#frmCrear\\:reabierto_2")).click();
            esperar(200);
        }

        driver.findElement(By.cssSelector("#frmCrear\\:falloTutela")).click();
        esperar(200);
        int indexFalloTutela = (int) (Math.random() * 2);
        System.out.println("Fallo Tutela: " + indexFalloTutela);
        if (indexFalloTutela == 0) {
            driver.findElement(By.cssSelector("#frmCrear\\:falloTutela_1")).click();
            esperar(200);
        } else {
            driver.findElement(By.cssSelector("#frmCrear\\:falloTutela_2")).click();
            esperar(200);
        }

        driver.findElement(By.cssSelector("#frmCrear\\:redireccionado")).click();
        esperar(200);
        int indexRedireccionado = (int) (Math.random() * 2);
        System.out.println("Redireccionado: " + indexRedireccionado);
        if (indexRedireccionado == 0) {
            driver.findElement(By.cssSelector("#frmCrear\\:redireccionado_1")).click();
            esperar(200);
        } else {
            driver.findElement(By.cssSelector("#frmCrear\\:redireccionado_2")).click();
            esperar(200);
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

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", estados.get(indexEstado));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", estados.get(indexEstado));

        esperar(200);

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

        int indexTipoAdministrativo = (int) (Math.random() * 2);

        if (indexAmbito == 1 && indexEstado!= 2){

            driver.findElement(By.cssSelector("#frmCrearServicio\\:tipoAdministrativo")).click();
            System.out.println("Tipo Administrativo: " + indexTipoAdministrativo);
            if (indexTipoAdministrativo == 0) {
                indexTipoAdministrativo = 1;
            }
            if (indexTipoAdministrativo == 1) {
                driver.findElement(By.cssSelector("#frmCrearServicio\\:tipoAdministrativo_1")).click();
                esperar(200);
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
            } else {
                esperar(200);
                driver.findElement(By.cssSelector("#frmCrearServicio\\:tipoAdministrativo_2")).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
            }

        }

        int indexAplicaMedicamento = (int) (Math.random() * 2);

        int indexAplicaMedicamentoCobertura = (int) (Math.random() * 2);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamento")));
        driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamento")).click();

        if (indexAplicaMedicamento == 0) {
            driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamento_1")).click();
        } else {
            driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamento_2")).click();
            esperar(200);

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

            if (indexAplicaMedicamentoCobertura == 0) {
                driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamentoCobertura")).click();
                esperar(200);
                driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamentoCobertura_1")).click();
            } else {
                driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamentoCobertura")).click();
                esperar(200);
                driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamentoCobertura_2")).click();
            }
        }

        int indexAplicaProcedimiento = (int) (Math.random() * 2);


        String query = "SELECT codigo  FROM ma_especialidades WHERE activo = \"1\" ORDER BY  RAND() LIMIT 1 \n";
        String codigoEspecialidad = "";

        if (indexAmbito !=1 && indexEstado !=3 && indexTipoAdministrativo !=1 ) {
            System.out.println("Habilitados");
            if (indexAplicaProcedimiento == 0){
                System.out.println("Aplica procedimiento");
                driver.findElement(By.cssSelector("#frmCrearServicio\\:btnAplicaProcedimiento")).click();
            }else{
                System.out.println("No aplica procedimiento");
                driver.findElement(By.cssSelector("#frmCrearServicio\\:btnAplicaProcedimiento")).click();
            }

            try {
                java.sql.Statement st = conexion.createStatement();
                java.sql.ResultSet resultSet = st.executeQuery(query);
                while (resultSet.next()) {
                    codigoEspecialidad = resultSet.getString("codigo");
                    System.out.println("Codigo Especialidad: " + codigoEspecialidad);
                }
            } catch (Exception e) {
                System.err.println("Error al obtener los datos de la persona: " + e.getMessage());
            }

            driver.findElement(By.cssSelector("#frmCrearServicio\\:btnEspecialidad")).click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

            driver.findElement(By.cssSelector("#frmEspecialidadBusqueda\\:tablaRegistrosEspecialidades\\:j_idt2469")).click();
            driver.findElement(By.cssSelector("#frmEspecialidadBusqueda\\:tablaRegistrosEspecialidades\\:j_idt2469")).sendKeys(codigoEspecialidad);

            driver.findElement(By.cssSelector("#frmEspecialidadBusqueda\\:j_idt2466")).click();

            esperar(200);

            List<WebElement> especialidades = driver.findElements(By.cssSelector("#frmEspecialidadBusqueda\\:tablaRegistrosEspecialidades_data > tr"));

            int indexEspecialidad = (int) (Math.random() * especialidades.size());

            especialidades.get(indexEspecialidad).click();

        }

        LocalDate fechaCumplimiento = LocalDate.now().plusDays(30);

        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechacumplimientoCrear_input")).click();
        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechacumplimientoCrear_input")).sendKeys(fechaCumplimiento.toString());

        driver.findElement(By.cssSelector("#frmCrearServicio\\:descripcionCrear")).click();
        driver.findElement(By.cssSelector("#frmCrearServicio\\:descripcionCrear")).sendKeys("Servicio de prueba automatizada");

        query = "SELECT codigo  FROM ma_diagnosticos md WHERE activo = \"1\" ORDER BY RAND() LIMIT 1 \n";
        String codigoDiagnostico = "";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                codigoDiagnostico = resultSet.getString("codigo");
                System.out.println("Codigo Diagnostico: " + codigoDiagnostico);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los datos de la persona: " + e.getMessage());
        }

        driver.findElement(By.cssSelector("#frmCrearServicio\\:btnCiex")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmDiagnosticoBusqueda\\:tablaRegistrosDiagnoticos\\:j_idt2456")).click();
        driver.findElement(By.cssSelector("#frmDiagnosticoBusqueda\\:tablaRegistrosDiagnoticos\\:j_idt2456")).sendKeys(codigoDiagnostico);

        driver.findElement(By.cssSelector("#frmDiagnosticoBusqueda\\:j_idt2447")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        List<WebElement> diagnosticos = driver.findElements(By.cssSelector("#frmDiagnosticoBusqueda\\:tablaRegistrosDiagnoticos_data > tr"));

        int indexDiagnostico = (int) (Math.random() * diagnosticos.size());

        diagnosticos.get(indexDiagnostico).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearServicio\\:patologiaCrear")).click();
        esperar(200);
        List<WebElement> patologias = driver.findElements(By.cssSelector("#frmCrearServicio\\:patologiaCrear_items > li"));

        int indexPatologia = (int) (Math.random() * patologias.size());
        if (indexPatologia == 0) {
            indexPatologia = 1;
        }

        System.out.println("Patologia: " + patologias.get(indexPatologia).getText());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", patologias.get(indexPatologia));

        patologias.get(indexPatologia).click();
    }

}