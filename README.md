# Sistema de Gestión de Prácticas Profesionales de Ingeniería de Software

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-3C34B6?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)

## Descripción del Proyecto

Este proyecto es un sistema de gestión diseñado para automatizar y facilitar el manejo de las prácticas profesionales de la carrera de Ingeniería de Software. Su objetivo principal es optimizar el flujo de trabajo entre estudiantes, coordinadores de prácticas y organizaciones vinculadas, cubriendo desde el registro de proyectos hasta la asignación de estudiantes y el seguimiento de sus actividades.

El sistema busca digitalizar procesos clave para:
* Registro y administración de organizaciones vinculadas.
* Registro y asignación de responsables de proyecto dentro de las organizaciones.
* Creación y gestión de proyectos de prácticas.
* Asignación de estudiantes a proyectos.
* Seguimiento y evaluación del progreso de las prácticas.

## Características Principales

* **Gestión de Usuarios:** Roles diferenciados para coordinadores de prácticas, estudiantes y responsables de organización.
* **Organizaciones Vinculadas:** Registro, consulta y edición de datos de empresas/organizaciones.
* **Proyectos de Prácticas:** Creación de nuevos proyectos, con detalles como descripción, objetivos, fechas y responsable asociado.
* **Asignación de Estudiantes:** Herramientas para asignar estudiantes a proyectos disponibles.
* **Interfaz Intuitiva:** Desarrollada con JavaFX para una experiencia de usuario amigable.

## Tecnologías Utilizadas

* **Lenguaje de Programación:** Java
* **Framework de UI:** JavaFX
* **Base de Datos:** MySQL
* **Control de Versiones:** Git & GitHub

## Configuración de la Base de Datos (MySQL)

1.  Asegúrate de tener un servidor MySQL instalado y funcionando.
2.  Crea una base de datos para el proyecto (ej., `proyecto_construccion`).
3.  **¡INFORMACIÓN IMPORTANTE PARA COLABORADORES!**
    La clase `ConexionBD.java` (ubicada en `src/proyectofinal/modelo`) **ha sido eliminada del repositorio público para evitar problemas con las credenciales de la base de datos de cada colaborador.**

    **Para que el proyecto funcione, cada colaborador debe crear manualmente esta clase con sus propias credenciales de conexión a MySQL.**

    Aquí tienes un ejemplo de cómo debería ser la estructura de `ConexionBD.java`:

    ```java
    package proyectofinal.modelo;

    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.SQLException;

    public class ConexionBD {

        // --- DEBES CAMBIAR ESTOS VALORES POR TUS PROPIAS CREDENCIALES ---
        private static final String IP = "localhost"; // O la IP de tu servidor MySQL
        private static final String PUERTO = "3306"; // Puerto de tu servidor MySQL
        private static final String NOMBRE_BD = "practicas_software"; // Cambia por el nombre de tu BD
        private static final String USUARIO = "root"; // Cambia por tu usuario de MySQL
        private static final String PASSWORD = "tu_password"; // Cambia por tu contraseña de MySQL
        private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // Driver JDBC para MySQL 8+

        // ----------------------------------------------------------------

        public static Connection obtenerConexion() throws SQLException, ClassNotFoundException {
            Class.forName(DRIVER); // Carga el driver JDBC
            String urlConexion = String.format("jdbc:mysql://%s:%s/%s?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", IP, PUERTO, NOMBRE_BD);
            return DriverManager.getConnection(urlConexion, USUARIO, PASSWORD);
        }
    }
    ```
    * **Notas adicionales para `ConexionBD.java`:**
        * Asegúrate de tener el driver JDBC de MySQL (ej. `mysql-connector-java.jar`) correctamente añadido a las dependencias de tu proyecto.
        * Para versiones recientes de MySQL Connector/J (8.0+), el `DRIVER` suele ser `com.mysql.cj.jdbc.Driver`.

4.  **Ejecuta los scripts SQL para crear las tablas necesarias de la base de datos.** El script lo puedes encontrar en **`db/database_dump.sql`**.
    * Puedes ejecutarlo en tu base de datos MySQL usando una herramienta como MySQL Workbench, o la línea de comandos (ej. `mysql -u tu_usuario -p tu_base_de_datos < db/database_dump.sql`).