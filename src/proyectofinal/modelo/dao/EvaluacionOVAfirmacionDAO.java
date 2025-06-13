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
import proyectofinal.modelo.pojo.EvaluacionOVAfirmacion;

public class EvaluacionOVAfirmacionDAO {

    public static List<EvaluacionOVAfirmacion> obtenerAfirmacionesPorCategoria(int idCategoria) throws SQLException {
        List<EvaluacionOVAfirmacion> afirmaciones = new ArrayList<>();
        String query = "SELECT idAfirmacionEvaluacionOV, descripcion, idCategoriaEvaluacionOV " +
                       "FROM afirmacion_evaluacion_ov WHERE idCategoriaEvaluacionOV = ? ORDER BY idAfirmacionEvaluacionOV ASC";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (conn == null) {
                throw new SQLException("No hay conexión a la base de datos.");
            }

            pstmt.setInt(1, idCategoria);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EvaluacionOVAfirmacion afirmacion = new EvaluacionOVAfirmacion(
                        rs.getInt("idAfirmacionEvaluacionOV"),
                        rs.getString("descripcion"),
                        rs.getInt("idCategoriaEvaluacionOV")
                    );
                    afirmaciones.add(afirmacion);
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return afirmaciones;
    }

    public static List<EvaluacionOVAfirmacion> obtenerTodasAfirmaciones() throws SQLException {
        List<EvaluacionOVAfirmacion> afirmaciones = new ArrayList<>();
        String query = "SELECT idAfirmacionEvaluacionOV, descripcion, idCategoriaEvaluacionOV " +
                       "FROM afirmacion_evaluacion_ov ORDER BY idCategoriaEvaluacionOV, idAfirmacionEvaluacionOV ASC";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (conn == null) {
                throw new SQLException("No hay conexión a la base de datos.");
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EvaluacionOVAfirmacion afirmacion = new EvaluacionOVAfirmacion(
                        rs.getInt("idAfirmacionEvaluacionOV"),
                        rs.getString("descripcion"),
                        rs.getInt("idCategoriaEvaluacionOV")
                    );
                    afirmaciones.add(afirmacion);
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return afirmaciones;
    }
}