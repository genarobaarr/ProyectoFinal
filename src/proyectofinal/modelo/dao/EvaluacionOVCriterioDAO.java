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
        String consulta = "SELECT idCriterioEvaluacionOV, nombre, valor FROM criterio_evaluacion_ov ORDER BY valor ASC";

        try (Connection conexionBD = ConexionBD.abrirConexion()) {
            if (conexionBD == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            try (PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
                 ResultSet resultado = sentencia.executeQuery()) {

                while (resultado.next()) {
                    EvaluacionOVCriterio criterio = new EvaluacionOVCriterio(
                        resultado.getInt("idCriterioEvaluacionOV"),
                        resultado.getString("nombre"),
                        resultado.getDouble("valor")
                    );
                    criterios.add(criterio);
                }

            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los criterios de evaluación OV.", e);
        }
        return criterios;
    }
}