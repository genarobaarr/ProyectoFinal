/*
 * Omar Morales García
 * 11-06-2025
 */
package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Importar Statement
import proyectofinal.modelo.pojo.EvaluacionOV;
import java.time.LocalDate;
import proyectofinal.modelo.ConexionBD;

public class EvaluacionOVDAO {

    public static int guardarEvaluacionOV(EvaluacionOV evaluacionOV) throws SQLException {
        int idGenerado = -1; // Valor por defecto si no se genera ID
        String query = "INSERT INTO evaluacion_ov (comentarios, fecha, puntaje_final, idExpediente) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) { // Añadir Statement.RETURN_GENERATED_KEYS

            if (conn == null) {
                System.err.println("ERROR: Connection is null in guardarEvaluacionOV.");
                throw new SQLException("No database connection available.");
            }

            pstmt.setString(1, evaluacionOV.getComentarios());
            pstmt.setString(2, LocalDate.now().toString());
            pstmt.setDouble(3, evaluacionOV.getPuntajeFinal());
            pstmt.setInt(4, evaluacionOV.getIdExpediente());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1); // Obtener el ID generado automáticamente
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al guardar la evaluación OV en la base de datos: " + e.getMessage());
            throw e;
        }
        return idGenerado;
    }
}