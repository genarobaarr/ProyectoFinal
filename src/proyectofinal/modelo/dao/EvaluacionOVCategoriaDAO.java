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
import proyectofinal.modelo.pojo.EvaluacionOVCategoria;

public class EvaluacionOVCategoriaDAO {

    public static List<EvaluacionOVCategoria> obtenerCategorias() throws SQLException {
        List<EvaluacionOVCategoria> categorias = new ArrayList<>();
        String consulta = "SELECT idCategoriaEvaluacionOV, nombre FROM categoria_evaluacion_ov ORDER BY idCategoriaEvaluacionOV ASC";

        try (Connection conexionBD = ConexionBD.abrirConexion()) {
            if (conexionBD == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            try (PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
                 ResultSet resultado = sentencia.executeQuery()) {

                while (resultado.next()) {
                    EvaluacionOVCategoria categoria = new EvaluacionOVCategoria(
                        resultado.getInt("idCategoriaEvaluacionOV"),
                        resultado.getString("nombre")
                    );
                    categorias.add(categoria);
                }

            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener las categorías de evaluación OV.", e);
        }
        return categorias;
    }
}