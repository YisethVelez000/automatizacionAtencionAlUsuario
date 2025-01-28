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
        driver.findElement(By.cssSelector("#frmCasos\\:tablaRegistros\\:j_idt99_panel > div.ui-selectcheckboxmenu-items-wrapper > ul > li:nth-child(6) > label")).click();
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
        esperar(200);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        int indexReabierto = (int) (Math.random() * 2);

        driver.findElement(By.cssSelector("#frmGestion\\:reabierto")).click();
        esperar(200);

        driver.findElement(By.cssSelector("#frmGestion\\:reabierto_" + indexReabierto)).click();
        esperar(200);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        int indexFalloTutela = (int) (Math.random() * 2);

        driver.findElement(By.cssSelector("#frmGestion\\:falloTutela")).click();
        esperar(200);

        driver.findElement(By.cssSelector("#frmGestion\\:falloTutela_" + indexFalloTutela)).click();
        esperar(200);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        int indexRedireccionado = (int) (Math.random() * 2);

        driver.findElement(By.cssSelector("#frmGestion\\:redireccionado")).click();
        esperar(200);

        driver.findElement(By.cssSelector("#frmGestion\\:redireccionado_" + indexRedireccionado)).click();
        esperar(200);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        int indexUsuarioPluripatologico = (int) (Math.random() * 2);

        driver.findElement(By.cssSelector("#frmGestion\\:usuarioPluripatologico")).click();
        esperar(200);

        driver.findElement(By.cssSelector("#frmGestion\\:usuarioPluripatologico_" + indexUsuarioPluripatologico)).click();
        esperar(200);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        int indexProteccionDatos = (int) (Math.random() * 2);

        driver.findElement(By.cssSelector("#frmGestion\\:protecciónDatos")).click();
        esperar(200);

        driver.findElement(By.cssSelector("#frmGestion\\:protecciónDatos_" + indexProteccionDatos)).click();
        esperar(200);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
    }


    private void servicio() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.cssSelector("#frmGestion\\:pGestionServicio_header")).click();
        driver.findElement(By.cssSelector("#frmGestion\\:tablaServicios\\:j_idt2108")).click();
        esperar(200);

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

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearServicio\\:ambitoCrear")).click();
        esperar(200);
        List<WebElement> ambitos = driver.findElements(By.cssSelector("#frmCrearServicio\\:ambitoCrear_items > li"));

        int indexAmbito = (int) (Math.random() * ambitos.size());

        if (indexAmbito == 0) {
            indexAmbito = 1;
        }

        System.out.println("Ambito: " + ambitos.get(indexAmbito).getText());
        ambitos.get(indexAmbito).click();

        int indexTipoAdministrativo = 0;

        if (indexAmbito == 1 && indexEstado != 2){

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

            driver.findElement(By.cssSelector("#frmCrearServicio\\:tipoAdministrativo")).click();

            List<WebElement> tiposAdministrativos = driver.findElements(By.cssSelector("#frmCrearServicio\\:tipoAdministrativo_items > li"));

            indexTipoAdministrativo = (int) (Math.random() * tiposAdministrativos.size());

            if (indexTipoAdministrativo == 0) {
                indexTipoAdministrativo = 1;
            }

            System.out.println("Tipo Administrativo: " + tiposAdministrativos.get(indexTipoAdministrativo).getText());
            tiposAdministrativos.get(indexTipoAdministrativo).click();

        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamento")).click();
        esperar(200);

        List<WebElement> aplicaMedicamento = driver.findElements(By.cssSelector("#frmCrearServicio\\:aplicaMedicamento_items > li"));
        int indexAplicaMedicamento = (int) (Math.random() * aplicaMedicamento.size());

        if (indexAplicaMedicamento == 0){
            indexAplicaMedicamento = 1;
        }

        System.out.println("Aplica Medicamento: " + aplicaMedicamento.get(indexAplicaMedicamento).getText());
        aplicaMedicamento.get(indexAplicaMedicamento).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        int indexAplicaMedicamentoCobertura;

        if (indexAplicaMedicamento == 2){

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

            driver.findElement(By.cssSelector("#frmCrearServicio\\:aplicaMedicamentoCobertura")).click();
            esperar(200);

            List<WebElement> aplicaMedicamentoCobertura = driver.findElements(By.cssSelector("#frmCrearServicio\\:aplicaMedicamentoCobertura_items > li"));
            indexAplicaMedicamentoCobertura = (int) (Math.random() * aplicaMedicamentoCobertura.size());

            if (indexAplicaMedicamentoCobertura == 0){
                indexAplicaMedicamentoCobertura = 1;
            }

            System.out.println("Aplica Medicamento Cobertura: " + aplicaMedicamentoCobertura.get(indexAplicaMedicamentoCobertura).getText());
            aplicaMedicamentoCobertura.get(indexAplicaMedicamentoCobertura).click();

        }

        int indexAplicaProcedimiento = (int) (Math.random() * 2);


        if (indexAplicaProcedimiento == 0){
            driver.findElement(By.cssSelector("#frmCrearServicio\\:btnAplicaProcedimiento")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        } else {
            System.out.println("No aplica procedimiento");
        }

        String query = "SELECT codigo  FROM ma_especialidades WHERE activo = \"1\" ORDER BY  RAND() LIMIT 1 ";
        String codigoEspecialidad = "";
        String codigoDiagnostico = "";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                codigoEspecialidad = resultSet.getString("codigo");
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la base de datos: " + e.getMessage());
        }

        System.out.println("Index Estado: " + indexEstado + " Index Ambito: " + indexAmbito + " Index Tipo Administrativo: " + indexTipoAdministrativo);

        if(indexEstado == 4  && indexAmbito == 1 && indexTipoAdministrativo == 1){

            System.out.println("No aplica especialidad");

        }else {
            System.out.println("Aplica especialidad");

            driver.findElement(By.cssSelector("#frmCrearServicio\\:btnEspecialidad")).click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

            driver.findElement(By.cssSelector("#frmEspecialidadBusqueda\\:tablaRegistrosEspecialidades\\:j_idt2469")).click();
            driver.findElement(By.cssSelector("#frmEspecialidadBusqueda\\:tablaRegistrosEspecialidades\\:j_idt2469")).sendKeys(codigoEspecialidad);

            driver.findElement(By.cssSelector("#frmEspecialidadBusqueda\\:j_idt2466")).click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

            List<WebElement> especialidades = driver.findElements(By.cssSelector("#frmEspecialidadBusqueda\\:tablaRegistrosEspecialidades_data > tr"));
            int indexEspecialidad = (int) (Math.random() * especialidades.size());

            especialidades.get(indexEspecialidad).click();

            System.out.println("Aplica CIE-X");

            query = "SELECT codigo  FROM ma_diagnosticos md WHERE activo = \"1\" ORDER BY RAND() LIMIT 1";

            try {
                java.sql.Statement st = conexion.createStatement();
                java.sql.ResultSet resultSet = st.executeQuery(query);
                while (resultSet.next()) {
                    codigoDiagnostico = resultSet.getString("codigo");
                }
            } catch (Exception e) {
                System.out.println("Error al consultar la base de datos: " + e.getMessage());
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

            System.out.println("Aplica Patologia");

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

        LocalDate fechaCumplimiento = LocalDate.now().plusDays(60);

        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechacumplimientoCrear_input")).click();
        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechacumplimientoCrear_input")).sendKeys(fechaCumplimiento.toString());

        driver.findElement(By.cssSelector("#frmCrearServicio\\:descripcionCrear")).click();
        driver.findElement(By.cssSelector("#frmCrearServicio\\:descripcionCrear")).sendKeys("Servicio editado con automatización");

        driver.findElement(By.cssSelector("#frmCrearServicio\\:servicioAtribuidoIPS")).click();
        esperar(200);

        List<WebElement> ips = driver.findElements(By.cssSelector("#frmCrearServicio\\:servicioAtribuidoIPS_items > li"));
        int indexIPS = (int) (Math.random() * ips.size());

        if (indexIPS == 0) {
            indexIPS = 1;
        }

        System.out.println("IPS: " + ips.get(indexIPS).getText());
        ips.get(indexIPS).click();

        if (indexEstado == 3 && indexAmbito ==4) {
            System.out.println("No destino ");
        }else{
            driver.findElement(By.cssSelector("#frmCrearServicio\\:btnservicioDestino")).click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

            List<WebElement> sedes = driver.findElements(By.cssSelector("#frmPrestadorIpsDestino\\:tablaRegistrosIpsDestino_data > tr"));
            int indexSede = (int) (Math.random() * sedes.size());

            sedes.get(indexSede).click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        }

        if (indexEstado == 1 && indexAmbito == 2 ){

            driver.findElement(By.cssSelector("#frmCrearServicio\\:asignado")).click();
            esperar(200);

            List<WebElement> asignados = driver.findElements(By.cssSelector("#frmCrearServicio\\:asignado_items > li"));

            int indexAsignado = (int) (Math.random() * asignados.size());

            if (indexAsignado == 0) {
                indexAsignado = 1;
            }
            asignados.get(indexAsignado).click();

        } else if ( indexEstado == 1 && indexAmbito ==1) {

            driver.findElement(By.cssSelector("#frmCrearServicio\\:asignado")).click();
            esperar(200);

            List<WebElement> asignados = driver.findElements(By.cssSelector("#frmCrearServicio\\:asignado_items > li"));

            int indexAsignado = (int) (Math.random() * asignados.size());

            if (indexAsignado == 0) {
                indexAsignado = 1;
            }
            asignados.get(indexAsignado).click();
        }

        LocalDate fechaInicioVigencia = LocalDate.now().plusDays(1);
        LocalDate fechaFinVigencia = fechaInicioVigencia.plusDays(60);

        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechaInicioVigenciaCrear_input")).click();
        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechaInicioVigenciaCrear_input")).sendKeys(fechaInicioVigencia.toString());

        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechaFinVigenciaCrear_input")).click();
        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechaFinVigenciaCrear_input")).sendKeys(fechaFinVigencia.toString());

        driver.findElement(By.cssSelector("#frmCrearServicio\\:j_idt1172")).click();

        String codigoMedicamento = "";
        String codigoTecnologia = "";

        if (indexAplicaProcedimiento == 0){
            if (indexAplicaMedicamento == 2){
                driver.findElement(By.cssSelector("#frmCrearServicio\\:medicamentoCrear")).click();

                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

                query = "SELECT cum FROM ma_medicamentos WHERE mae_estado_registro_sanitario_valor  = \"Vigente\" ORDER BY RAND() LIMIT 1";

                try {
                    java.sql.Statement st = conexion.createStatement();
                    java.sql.ResultSet resultSet = st.executeQuery(query);
                    while (resultSet.next()) {
                        codigoMedicamento = resultSet.getString("cum");
                    }
                } catch (Exception e) {
                    System.out.println("Error al consultar la base de datos: " + e.getMessage());
                }

                driver.findElement(By.cssSelector("#frmMedicamentoBusqueda\\:tablaRegistrosMedicamentos\\:j_idt2491")).click();
                driver.findElement(By.cssSelector("#frmMedicamentoBusqueda\\:tablaRegistrosMedicamentos\\:j_idt2491")).sendKeys(codigoMedicamento);

                driver.findElement(By.cssSelector("#frmMedicamentoBusqueda\\:j_idt2482")).click();

                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

                List<WebElement> medicamentos = driver.findElements(By.cssSelector("#frmMedicamentoBusqueda\\:tablaRegistrosMedicamentos_data > tr"));
                int indexMedicamento = (int) (Math.random() * medicamentos.size());

                medicamentos.get(indexMedicamento).click();

                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

            }else{
                driver.findElement(By.cssSelector("#frmCrearServicio\\:procedimientoCrear")).click();

                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

                query = "SELECT cups FROM ma_tecnologias mt ORDER BY RAND () LIMIT 1";

                try {
                    java.sql.Statement st = conexion.createStatement();
                    java.sql.ResultSet resultSet = st.executeQuery(query);
                    while (resultSet.next()) {
                        codigoTecnologia = resultSet.getString("cups");
                    }
                } catch (Exception e) {
                    System.out.println("Error al consultar la base de datos: " + e.getMessage());
                }

                driver.findElement(By.cssSelector("#frmTecnologiaBusqueda\\:tablaRegistrosTecnologias\\:j_idt2504")).click();
                driver.findElement(By.cssSelector("#frmTecnologiaBusqueda\\:tablaRegistrosTecnologias\\:j_idt2504")).sendKeys(codigoTecnologia);

                driver.findElement(By.cssSelector("#frmTecnologiaBusqueda\\:j_idt2501")).click();

                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

                List<WebElement> tecnologias = driver.findElements(By.cssSelector("#frmTecnologiaBusqueda\\:tablaRegistrosTecnologias_data > tr"));
                int indexTecnologia = (int) (Math.random() * tecnologias.size());

                tecnologias.get(indexTecnologia).click();

                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
            }
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

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
