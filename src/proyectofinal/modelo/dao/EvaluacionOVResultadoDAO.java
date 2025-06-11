/*
 * Omar Morales García
 * 11-06-2025
 */
package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.EvaluacionOVResultado;

public class EvaluacionOVResultadoDAO {

    public static int guardarResultadosEvaluacionOV(List<EvaluacionOVResultado> resultados) throws SQLException {
        int totalFilasAfectadas = 0;
        String query = "INSERT INTO resultado_evaluacion_ov (idCriterioEvaluacionOV, idAfirmacionEvaluacionOV, idEvaluacionOV) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (conn == null) {
                throw new SQLException("No hay conexión a la base de datos.");
            }

            for (EvaluacionOVResultado resultado : resultados) {
                pstmt.setInt(1, resultado.getIdCriterioEvaluacionOV());
                pstmt.setInt(2, resultado.getIdAfirmacionEvaluacionOV());
                pstmt.setInt(3, resultado.getIdEvaluacionOV());
                pstmt.addBatch(); 
            }

            int[] filasAfectadasPorBatch = pstmt.executeBatch();
            for (int count : filasAfectadasPorBatch) {
                totalFilasAfectadas += count;
            }

        } catch (SQLException e) {
            System.err.println("Error al guardar los resultados de la evaluación OV: " + e.getMessage());
            throw e;
        }
        return totalFilasAfectadas;
    }
}