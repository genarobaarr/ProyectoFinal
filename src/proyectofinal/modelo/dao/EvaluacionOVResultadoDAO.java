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

        try (Connection conn = ConexionBD.abrirConexion()) {
            if (conn == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                for (EvaluacionOVResultado resultado : resultados) {
                    pstmt.setInt(1, resultado.getIdCriterioEvaluacionOV());
                    pstmt.setInt(2, resultado.getIdAfirmacionEvaluacionOV());
                    pstmt.setInt(3, resultado.getIdEvaluacionOV());
                    pstmt.addBatch();
                }

                int[] resultadosBatch = pstmt.executeBatch();
                for (int resultado : resultadosBatch) {
                    if (resultado == PreparedStatement.EXECUTE_FAILED) {
                        throw new SQLException("Falló la ejecución del batch para guardar resultados de evaluación OV.");
                    }
                    totalFilasAfectadas += resultado;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al guardar los resultados de evaluación OV.", e);
        }

        return totalFilasAfectadas;
    }
}