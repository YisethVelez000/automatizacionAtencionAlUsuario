package personas;

import conexion.Conexion_BD;
import conexion.ingresoPruebas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class crearPersonas {
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

    private long esperar (long tiempo){
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return tiempo;
    }

    @Test
    public void crear() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ingreso.iniciarSesion();
        driver.get("http://10.250.3.66:8080/savia/atencionusuario/personas.faces");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmPersonas\\:j_idt43")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrear\\:tipoDocumento")).click();
        esperar(200);

        List<WebElement> tiposDocumento = driver.findElements(By.cssSelector("#frmCrear\\:tipoDocumento_items > li"));

        int indexTipoDocumento = (int) (Math.random() * tiposDocumento.size());

        if (indexTipoDocumento == 0) {
            indexTipoDocumento++;
        }

        tiposDocumento.get(indexTipoDocumento).click();

        int numeroDocumento = (int) (Math.random() * 1000000000);

        driver.findElement(By.cssSelector("#frmCrear\\:documento")).sendKeys(String.valueOf(numeroDocumento));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        driver.findElement(By.cssSelector("#frmCrear\\:nombres")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));

        String query = "SELECT nombres  FROM aus_personas ap ORDER BY RAND() LIMIT 1 ";
        String nombres = "";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                nombres = resultSet.getString("nombres");
            }
        } catch (Exception e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }

        driver.findElement(By.cssSelector("#frmCrear\\:nombres")).sendKeys(nombres);

        String apellidos = "";
        query = "SELECT apellidos  FROM aus_personas ap ORDER BY RAND() LIMIT 1 ";

        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                apellidos = resultSet.getString("apellidos");
            }
        } catch (Exception e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }

        driver.findElement(By.cssSelector("#frmCrear\\:apellidos")).sendKeys(apellidos);

        driver.findElement(By.cssSelector("#frmCrear\\:sexo")).click();
        esperar(200);

        List<WebElement> sexos = driver.findElements(By.cssSelector("#frmCrear\\:sexo_items > li"));

        int indexSexo = (int) (Math.random() * sexos.size());

        if (indexSexo == 0) {
            indexSexo++;
        }

        sexos.get(indexSexo).click();

        // Crear una instancia de Random
        Random random = new Random();

        // Generar un año aleatorio entre 2000 y 2023
        int año = random.nextInt(25) + 1970;

        // Generar un mes aleatorio entre 1 y 12
        int mes = random.nextInt(12) + 1;

        // Generar un día aleatorio, teniendo en cuenta el número de días del mes
        int día = random.nextInt(LocalDate.of(año, mes, 1).lengthOfMonth()) + 1;

        // Crear la fecha aleatoria
        LocalDate fechaAleatoria = LocalDate.of(año, mes, día);

        driver.findElement(By.cssSelector("#frmCrear\\:fechaNacimiento_input")).sendKeys(fechaAleatoria.toString());

        driver.findElement(By.cssSelector("#frmCrear\\:email")).click();

        String correo = generarCorreo(nombres, apellidos);

        driver.findElement(By.cssSelector("#frmCrear\\:email")).sendKeys(correo);

        driver.findElement(By.cssSelector("#frmCrear\\:estado")).click();
        esperar(200);

        List<WebElement> estados = driver.findElements(By.cssSelector("#frmCrear\\:estado_items > li"));

        int indexEstado = (int) (Math.random() * estados.size());

        if (indexEstado == 0) {
            indexEstado++;
        }

        estados.get(indexEstado).click();

        driver.findElement(By.cssSelector("#frmCrear\\:ubicacion")).click();
        esperar(200);

        List<WebElement> ubicaciones = driver.findElements(By.cssSelector("#frmCrear\\:ubicacion_items > li"));
    }


    public static String generarCorreo(String nombres, String apellidos) {
        // Obtener la primera letra del nombre
        String inicialNombre = nombres.substring(0, 1).toLowerCase();


        // Convertir el apellido a minúsculas y eliminar espacios y dejar solo un apellido
        String apellidoFormateado = apellidos.toLowerCase().replaceAll("\\s+", "");


        //Tomar solo los primeros 6 caracteres del apellido
        if (apellidoFormateado.length() > 5) {
            apellidoFormateado = apellidoFormateado.substring(0, 5);
        }

        // Combinar la inicial del nombre con el apellido y agregar un dominio
        String correo = inicialNombre + apellidoFormateado + "@gmail.com";

        return correo;
    }
}
