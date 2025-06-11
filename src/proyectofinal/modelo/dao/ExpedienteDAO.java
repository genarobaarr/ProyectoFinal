/*
 * Omar Morales García
 * 11-06-2025
 */
package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.Expediente; // Asegúrate de importar el POJO Expediente

public class ExpedienteDAO {

    public static Expediente obtenerExpedientePorId(int idExpediente) throws SQLException {
        Expediente expediente = null;
        String query = "SELECT idExpediente, estatus, horasAcumuladas, idProyecto, idPeriodo, idEstudiante " +
                       "FROM expediente " + 
                       "WHERE idExpediente = ?";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (conn == null) {
                throw new SQLException("No hay conexión a la base de datos.");
            }

            pstmt.setInt(1, idExpediente);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    expediente = new Expediente();
                    expediente.setIdExpediente(rs.getInt("idExpediente"));
                    expediente.setEstatus(rs.getString("estatus"));
                    expediente.setHorasAcumuladas(rs.getInt("horasAcumuladas"));
                    expediente.setIdProyecto(rs.getInt("idProyecto"));
                    expediente.setIdPeriodo(rs.getInt("idPeriodo"));
                    expediente.setIdEstudiante(rs.getInt("idEstudiante"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener expediente por ID: " + e.getMessage());
            throw e;
        }
        return expediente;
    }
    
    public static Expediente obtenerExpedientePorIdEstudiante(int idEstudiante) throws SQLException {
        Expediente expediente = null;
        String consulta = "SELECT idExpediente, estatus, horasAcumuladas, idProyecto, idPeriodo, idEstudiante " +
                       "FROM expediente " + 
                       "WHERE idEstudiante = ?";

        try (Connection conexionBD = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {

            if (conexionBD == null) {
                throw new SQLException("No hay conexión a la base de datos.");
            }

            sentencia.setInt(1, idEstudiante);
            try (ResultSet rs = sentencia.executeQuery()) {
                if (rs.next()) {
                    expediente = new Expediente();
                    expediente.setIdExpediente(rs.getInt("idExpediente"));
                    expediente.setEstatus(rs.getString("estatus"));
                    expediente.setHorasAcumuladas(rs.getInt("horasAcumuladas"));
                    expediente.setIdProyecto(rs.getInt("idProyecto"));
                    expediente.setIdPeriodo(rs.getInt("idPeriodo"));
                    expediente.setIdEstudiante(rs.getInt("idEstudiante"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener expediente por ID: " + e.getMessage());
            throw e;
        }
        return expediente;
    }
    
    public static int obtenerIdExpedientePorIdEstudiante(int idEstudiante) throws SQLException {
        int idExpediente = 0;
        String consulta = "SELECT idExpediente " +
                       "FROM expediente " + 
                       "WHERE idEstudiante = ?";

        try (Connection conexionBD = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {

            if (conexionBD == null) {
                throw new SQLException("No hay conexión a la base de datos.");
            }

            sentencia.setInt(1, idEstudiante);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    idExpediente = resultado.getInt("idExpediente");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener expediente por ID: " + e.getMessage());
            throw e;
        }
        return idExpediente;
    }
}