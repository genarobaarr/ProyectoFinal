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

        String consulta = "SELECT idAfirmacionEvaluacionOV, descripcion, idCategoriaEvaluacionOV " +
                       "FROM afirmacion_evaluacion_ov " +
                       "WHERE idCategoriaEvaluacionOV = ? " +
                       "ORDER BY idAfirmacionEvaluacionOV ASC";

        try (Connection conexionBD = ConexionBD.abrirConexion()) {
            if (conexionBD == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            try (PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {
                sentencia.setInt(1, idCategoria);
                try (ResultSet resultado = sentencia.executeQuery()) {
                    while (resultado.next()) {
                        EvaluacionOVAfirmacion afirmacion = new EvaluacionOVAfirmacion(
                            resultado.getInt("idAfirmacionEvaluacionOV"),
                            resultado.getString("descripcion"),
                            resultado.getInt("idCategoriaEvaluacionOV")
                        );
                        afirmaciones.add(afirmacion);
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en obtenerAfirmacionesPorCategoria: " + e.getMessage(), e);
        }
        return afirmaciones;
    }

    public static List<EvaluacionOVAfirmacion> obtenerTodasAfirmaciones() throws SQLException {
        List<EvaluacionOVAfirmacion> afirmaciones = new ArrayList<>();

        String consulta = "SELECT idAfirmacionEvaluacionOV, descripcion, idCategoriaEvaluacionOV " +
                       "FROM afirmacion_evaluacion_ov " +
                       "ORDER BY idCategoriaEvaluacionOV, idAfirmacionEvaluacionOV ASC";

        try (Connection conexionBD = ConexionBD.abrirConexion()) {
            if (conexionBD == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            try (PreparedStatement pstmt = conexionBD.prepareStatement(consulta);
                 ResultSet resultado = pstmt.executeQuery()) {

                while (resultado.next()) {
                    EvaluacionOVAfirmacion afirmacion = new EvaluacionOVAfirmacion(
                        resultado.getInt("idAfirmacionEvaluacionOV"),
                        resultado.getString("descripcion"),
                        resultado.getInt("idCategoriaEvaluacionOV")
                    );
                    afirmaciones.add(afirmacion);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en obtenerTodasAfirmaciones: " + e.getMessage(), e);
        }
        return afirmaciones;
    }
}