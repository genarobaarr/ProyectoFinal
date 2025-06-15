/*
 * Omar Morales García
 * 11-06-2025
 */
package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.EvaluacionOV;

public class EvaluacionOVDAO {

    public static int guardarEvaluacionOV(EvaluacionOV evaluacionOV) throws SQLException {
        int idGenerado = -1;
        String query = "INSERT INTO evaluacion_ov (comentarios, fecha, puntaje_final, idExpediente) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.abrirConexion()) {
            if (conn == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, evaluacionOV.getComentarios());
                pstmt.setString(2, LocalDate.now().toString());
                pstmt.setDouble(3, evaluacionOV.getPuntajeFinal());
                pstmt.setInt(4, evaluacionOV.getIdExpediente());

                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas == 0) {
                    throw new SQLException("La inserción de la evaluación OV no afectó ninguna fila.");
                }

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al guardar la evaluación OV en la base de datos.", e);
        }

        return idGenerado;
    }
}
