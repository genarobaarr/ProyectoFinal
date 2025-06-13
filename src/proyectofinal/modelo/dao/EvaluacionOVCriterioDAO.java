/*
 * Omar Morales García
 * 11-06-2025
 */
package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.EvaluacionOVCriterio;

public class EvaluacionOVCriterioDAO {

    public static List<EvaluacionOVCriterio> obtenerCriterios() throws SQLException {
        List<EvaluacionOVCriterio> criterios = new ArrayList<>();
        String query = "SELECT idCriterioEvaluacionOV, nombre, valor FROM criterio_evaluacion_ov ORDER BY valor ASC";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (conn == null) {
                throw new SQLException("No hay conexión a la base de datos.");
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EvaluacionOVCriterio criterio = new EvaluacionOVCriterio(
                        rs.getInt("idCriterioEvaluacionOV"),
                        rs.getString("nombre"),
                        rs.getDouble("valor")
                    );
                    criterios.add(criterio);
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return criterios;
    }
}