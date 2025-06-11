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
        String query = "SELECT idCategoriaEvaluacionOV, nombre FROM categoria_evaluacion_ov ORDER BY idCategoriaEvaluacionOV ASC";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (conn == null) {
                throw new SQLException("No hay conexión a la base de datos.");
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EvaluacionOVCategoria categoria = new EvaluacionOVCategoria(
                        rs.getInt("idCategoriaEvaluacionOV"),
                        rs.getString("nombre")
                    );
                    categorias.add(categoria);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener categorías de evaluación OV: " + e.getMessage());
            throw e;
        }
        return categorias;
    }
}