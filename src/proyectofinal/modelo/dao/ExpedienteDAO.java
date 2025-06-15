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
import proyectofinal.modelo.pojo.Expediente;
import proyectofinal.modelo.pojo.ResultadoOperacion;

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
            throw e;
        }
        return idExpediente;
    }
    
    public static ResultadoOperacion actualizarNumeroHorasAcumuladas(int numeroHoras, int idExpediente) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultadoOperacion resultado = new ResultadoOperacion();
        try {
            conexionBD = ConexionBD.abrirConexion();

            if (conexionBD != null) {
                String consulta = "UPDATE expediente SET horasAcumuladas = horasAcumuladas + ? WHERE idExpediente = ?";

                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, numeroHoras);
                sentencia.setInt(2, idExpediente);
                
                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas == 1) {
                    resultado.setError(false);
                    resultado.setMensaje("Reporte mensual registrado correctamente.");
                } else {
                    resultado.setError(true);
                    resultado.setMensaje("Lo sentimos :( no se pudo registrar el reporte mensual. Intente más tarde.");
                }
            } else {
                throw new SQLException("Error: Sin conexión a la base de datos.");
            }
        } finally {
            sentencia.close();
            conexionBD.close();
        }
        return resultado;
    }
}