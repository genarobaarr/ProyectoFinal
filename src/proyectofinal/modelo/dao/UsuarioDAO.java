/*
 * Genaro Alejandro Barradas Sánchez
 * 16-06-2025
 */
package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.ResultadoOperacion;
import proyectofinal.modelo.pojo.Usuario;

public class UsuarioDAO {
    
    public static int obtenerIdUsuarioPorCredenciales(String username, String password) throws SQLException {
        int idUsuario = 0;        
        String consulta = "SELECT idUsuario FROM usuario WHERE "
                        + "username = ? AND password = ?";
        
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD == null) {
                throw new SQLException("No hay conexión con la base de datos.");
            }

            sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, username);
            sentencia.setString(2, password);
            resultado = sentencia.executeQuery();

            if (resultado.next()) {
                idUsuario = resultado.getInt("idUsuario");
            }
        } finally {
            if (resultado != null) { 
                resultado.close(); 
            }
            if (sentencia != null) { 
                sentencia.close(); 
            }
            if (conexionBD != null) { 
                conexionBD.close();
            }
        }
        return idUsuario;
    }
    
    public static ResultadoOperacion registrarUsuario(Usuario usuario) throws SQLException {
        ResultadoOperacion resultado = new ResultadoOperacion();
        String consulta = "INSERT INTO usuario (nombre, apellidoPaterno, apellidoMaterno, email, username, password) VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection conexionBD = null;
        PreparedStatement sentencia = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD == null) {
                throw new SQLException("No hay conexión con la base de datos.");
            }

            sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getApellidoPaterno());
            sentencia.setString(3, usuario.getApellidoMaterno());
            sentencia.setString(4, usuario.getEmail());
            sentencia.setString(5, usuario.getUsername());
            sentencia.setString(6, usuario.getPassword());
            
            int filasAfectadas = sentencia.executeUpdate();
            
            if (filasAfectadas == 1) {
                resultado.setError(false);
                resultado.setMensaje("Usuario registrado correctamente");
            } else {
                resultado.setError(true);
                resultado.setMensaje("Lo sentimos :( por el momento no se puede "
                        + "registrar la información del usuario, "
                        + "por favor inténtelo más tarde");
            }
        } finally {
            if (sentencia != null) { 
                sentencia.close();
            }
            if (conexionBD != null) { 
                conexionBD.close();
            }
        }
        return resultado;
    }
    
    public static boolean existeUsuario(String username) throws SQLException {
        boolean existe = false;
        String consulta = "SELECT COUNT(*) FROM usuario WHERE username = ?";
        
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD == null) {
                throw new SQLException("No hay conexión con la base de datos.");
            }

            sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, username);
            resultado = sentencia.executeQuery();

            if (resultado.next()) {
                if (resultado.getInt(1) > 0) {
                    existe = true;
                }
            }
        } finally {
            if (resultado != null) { 
                resultado.close(); 
            }
            if (sentencia != null) { 
                sentencia.close();
            }
            if (conexionBD != null) { 
                conexionBD.close();
            }
        }
        return existe;
    }
}