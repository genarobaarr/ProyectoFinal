/*
 * Genaro Alejandro Barradas Sánchez
 * 28-05-2025
 */
package proyectofinal.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String IP = "localhost";
    private static final String PUERTO = "3306";
    private static final String NOMBRE_BD = "proyecto_construccion";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "my$Q.L1P4sS$w0Rd";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    public static Connection abrirConexion() {
        
        Connection conexionBD = null;
        String urlConexion = 
                String.format("jdbc:mysql://%s:%s/%s?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", 
                        IP, PUERTO, NOMBRE_BD);
        
        try {
            Class.forName(DRIVER);
            conexionBD = DriverManager.getConnection(urlConexion, USUARIO, PASSWORD);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.err.println("Error: Clase no encontrada.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error en la conexión: " + ex.getMessage());
        }
        return conexionBD;
    }
}