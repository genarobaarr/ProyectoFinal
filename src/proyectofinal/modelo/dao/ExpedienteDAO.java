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
        // Consulta SQL ajustada para reflejar exactamente los campos de tu POJO Expediente
        String query = "SELECT idExpediente, estatus, horasAcumuladas, idProyecto, idPeriodo, idEstudiante " +
                       "FROM expediente " + // Asegúrate de que el nombre de tu tabla sea 'expediente'
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
}