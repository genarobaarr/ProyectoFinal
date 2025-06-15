/*
 * Omar Morales García
 * 10-06-2025
 */
package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.EvaluacionExposicion;
import proyectofinal.modelo.pojo.EvaluacionExposicionCriterio;

public class EvaluacionExposicionDAO {
    
    public static int guardarEvaluacionExposicion(EvaluacionExposicion evaluacion, List<EvaluacionExposicionCriterio> criterios) throws SQLException {
        int idGenerado = -1;
        Connection conexionBD = null;
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }
            
            conexionBD.setAutoCommit(false);
            String insertEvaluacionSQL = "INSERT INTO evaluacion_exposicion (fechaEvaluacion, comentarios, puntajeFinal, idExpediente, idAcademicoEvaluador) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement pstmtEvaluacion = conexionBD.prepareStatement(insertEvaluacionSQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmtEvaluacion.setDate(1, evaluacion.getFechaEvaluacion());
                pstmtEvaluacion.setString(2, evaluacion.getComentarios());
                pstmtEvaluacion.setDouble(3, evaluacion.getPuntajeFinal());
                pstmtEvaluacion.setInt(4, evaluacion.getIdExpediente());
                pstmtEvaluacion.setInt(5, evaluacion.getIdAcademicoEvaluador());

                int filasAfectadas = pstmtEvaluacion.executeUpdate();
                if (filasAfectadas == 0) {
                    conexionBD.rollback();
                    return -1;
                }
                try (ResultSet rs = pstmtEvaluacion.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                    }
                }
            }

            String insertCriterioSQL = "INSERT INTO criterio_evaluacion_exposicion (idEvaluacionExposicion, criterio, nivel, valor) VALUES (?, ?, ?, ?)";

            try (PreparedStatement pstmtCriterio = conexionBD.prepareStatement(insertCriterioSQL)) {
                for (EvaluacionExposicionCriterio criterio : criterios) {
                    pstmtCriterio.setInt(1, idGenerado);
                    pstmtCriterio.setString(2, criterio.getCriterio());
                    pstmtCriterio.setString(3, criterio.getNivel());
                    pstmtCriterio.setDouble(4, criterio.getValor());
                    pstmtCriterio.addBatch();
                }
                int[] resultados = pstmtCriterio.executeBatch();
                for (int res : resultados) {
                    if (res == Statement.EXECUTE_FAILED) {
                        conexionBD.rollback();
                        return -1;
                    }
                }
            }
            conexionBD.commit();

        } catch (SQLException e) {
            try {
                if (conexionBD != null) conexionBD.rollback();
            } catch (SQLException ex) {
                throw ex;
            }
            throw new RuntimeException("Error al guardar la evaluación y criterios.", e);
        } finally {
            try {
                if (conexionBD != null) {
                    conexionBD.setAutoCommit(true);
                    conexionBD.close();
                }
            } catch (SQLException e) {
                throw e;
            }
        }
        return idGenerado;
    }
}