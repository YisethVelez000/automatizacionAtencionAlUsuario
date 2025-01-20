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
        servicio();
        driver.findElement(By.id("frmCrear:pCasoCrear_toggler")).click();
        esperar(500);
        seguimiento();
        esperar(500);
        driver.findElement(By.cssSelector("#frmCrear\\:j_idt692")).click();

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
            if (index >= TiposDocumento.size()) {
                continuar = false;
            }
            index = index + 1;
        } while (continuar);
        if (index == 15) {
            index = 14;
        }
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
        int index = (int) (Math.random() * 5) + 1;

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
        index = (int) (Math.random() * 5) + 1;
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
        File uploadFile = new File("C:\\Users\\apuertav\\Downloads\\Carta- MBV 516  Alinson Yiset Puerta Velez.pdf");
        WebElement uploadElement = driver.findElement(By.id("frmCrear:tablaAnexosCasos:inptAnexo_input"));
        uploadElement.sendKeys(uploadFile.getAbsolutePath());
        esperar(3500);
        driver.findElement(By.className("ui-fileupload-upload")).click();
    }

    private void seguimiento() {
        driver.findElement(By.id("frmCrear:estadoSeguimiento")).click();
        driver.findElement(By.id("frmCrear:estadoSeguimiento_1")).click();
        esperar(500);
        driver.findElement(By.id("frmCrear:observacionSeg")).sendKeys("Seguimiento de prueba automatizado");

    }

    private void servicio() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.id("frmCrear:tablaServicios:j_idt646")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.id("frmCrearServicio:estadoCrear")).click();

        int index = (int) (Math.random() * 4) + 1;
        driver.findElement(By.id("frmCrearServicio:estadoCrear_" + index)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.id("frmCrearServicio:ambitoCrear")).click();
        int index1 = (int) (Math.random() * 4) + 1;
        driver.findElement(By.id("frmCrearServicio:ambitoCrear_" + index)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        if (index1 == 1 ) {
            driver.findElement(By.id("frmCrearServicio:j_idt1095")).click();
            index = (int) (Math.random() * 2) + 1;
            driver.findElement(By.id("frmCrearServicio:tipoAdministrativo_" + index)).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        }
        if (index != 3) {
            index = (int) (Math.random() * 2) + 1;

            if (index == 1) {
                driver.findElement(By.id("frmCrearServicio:btnAplicaProcedimiento")).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
            }

            driver.findElement(By.id("frmCrearServicio:btnEspecialidad")).click();
            String query = "SELECT codigo  FROM ma_especialidades me ORDER BY RAND() LIMIT 1 ";
            String codigo = "";
            try {
                java.sql.Statement st = conexion.createStatement();
                java.sql.ResultSet resultSet = st.executeQuery(query);
                while (resultSet.next()) {
                    codigo = resultSet.getString("codigo");
                }
                System.out.println("consulta exitosa");
            } catch (Exception e) {
                System.err.println("Error al consultar la base de datos: " + e.getMessage());
            }

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
            driver.findElement(By.id("frmEspecialidadBusqueda:tablaRegistrosEspecialidades:j_idt2469")).sendKeys(codigo);
            driver.findElement(By.id("frmEspecialidadBusqueda:j_idt2466")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

            driver.findElement(By.cssSelector("tbody#frmEspecialidadBusqueda\\:tablaRegistrosEspecialidades_data")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

            driver.findElement(By.id("frmCrearServicio:btnCiex")).click();

            query = "SELECT ma_diagnostico_codigo  FROM auc_diagnosticos ad ORDER BY RAND() LIMIT 1 ";
            codigo = "";

            try {
                java.sql.Statement st = conexion.createStatement();
                java.sql.ResultSet resultSet = st.executeQuery(query);
                while (resultSet.next()) {
                    codigo = resultSet.getString("ma_diagnostico_codigo");
                }
                System.out.println("consulta exitosa");
            } catch (Exception e) {
                System.err.println("Error al consultar la base de datos: " + e.getMessage());
            }

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
            driver.findElement(By.id("frmDiagnosticoBusqueda:tablaRegistrosDiagnoticos:j_idt2456")).sendKeys(codigo);
            driver.findElement(By.id("frmDiagnosticoBusqueda:j_idt2447")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

            driver.findElement(By.cssSelector("tbody#frmDiagnosticoBusqueda\\:tablaRegistrosDiagnoticos_data")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        }


        driver.findElement(By.id("frmCrearServicio:aplicaMedicamento")).click();
        index = (int) (Math.random() * 2) + 1;
        driver.findElement(By.id("frmCrearServicio:aplicaMedicamento_" + index)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        if (index == 2) {
            driver.findElement(By.id("frmCrearServicio:aplicaMedicamentoCobertura")).click();
            index = (int) (Math.random() * 2) + 1;
            driver.findElement(By.id("frmCrearServicio:aplicaMedicamentoCobertura_" + index)).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        }

        LocalDate fecha = LocalDate.now();
        int semana = (int) (Math.random() * 15) + 1;
        fecha.minusWeeks(semana);
        driver.findElement(By.id("frmCrearServicio:fechacumplimientoCrear_input")).sendKeys(String.valueOf(fecha));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        driver.findElement(By.id("frmCrearServicio:descripcionCrear")).sendKeys("Servicio de prueba automatizado");
        driver.findElement(By.id("frmCrearServicio:j_idt1121")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrearServicio\\:patologiaCrear")).click();
        List<WebElement> patologias = driver.findElements(By.cssSelector("#frmCrearServicio\\:patologiaCrear_items li"));
        index = (int) (Math.random() * patologias.size()) + 0;
        //Nos desplazamos a la patologia seleccionada
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", patologias.get(index));
        patologias.get(index).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        index = (int) (Math.random() * 3) + 1;
        driver.findElement(By.cssSelector("#frmCrearServicio\\:servicioAtribuidoIPS")).click();
        driver.findElement(By.cssSelector("#frmCrearServicio\\:servicioAtribuidoIPS_" + index)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.id("frmCrearServicio:btnservicioDestino")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        String query = "SELECT numero_documento from cnt_prestadores cp ORDER BY RAND() LIMIT 1 ";
        String numeroDocumento = "";
        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                numeroDocumento = resultSet.getString("numero_documento");
            }
            System.out.println("consulta exitosa");
        } catch (Exception e) {
            System.err.println("Error al consultar la base de datos: " + e.getMessage());
        }

        driver.findElement(By.cssSelector("#frmPrestadorIpsDestino\\:tablaRegistrosIpsDestino\\:j_idt2218")).click();
        driver.findElement(By.cssSelector("#frmPrestadorIpsDestino\\:tablaRegistrosIpsDestino\\:j_idt2218")).sendKeys(numeroDocumento);
        driver.findElement(By.cssSelector("#frmPrestadorIpsDestino\\:tablaRegistrosIpsDestino_paginator_top")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmPrestadorIpsDestino\\:tablaRegistrosIpsDestino_data")).click();

        fecha = LocalDate.now();
        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechaInicioVigenciaCrear_input")).sendKeys(String.valueOf(fecha));
        driver.findElement(By.cssSelector("#frmCrearServicio\\:pCrearServicioTecnologias_header")).click();

        semana = (int) (Math.random() * 15) + 1;
        fecha.plusWeeks(semana);
        driver.findElement(By.cssSelector("#frmCrearServicio\\:fechaFinVigenciaCrear_input")).sendKeys(String.valueOf(fecha));

        driver.findElement(By.cssSelector("#frmCrearServicio\\:pCrearServicioTecnologias_header")).click();

        driver.findElement(By.cssSelector("#frmCrearServicio\\:j_idt1212")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

    }

}
