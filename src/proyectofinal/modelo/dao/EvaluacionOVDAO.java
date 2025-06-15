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
        String consulta = "INSERT INTO evaluacion_ov (comentarios, fecha, puntaje_final, idExpediente) VALUES (?, ?, ?, ?)";

        try (Connection conexionBD = ConexionBD.abrirConexion()) {
            if (conexionBD == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            try (PreparedStatement sentencia = conexionBD.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS)) {
                sentencia.setString(1, evaluacionOV.getComentarios());
                sentencia.setString(2, LocalDate.now().toString());
                sentencia.setDouble(3, evaluacionOV.getPuntajeFinal());
                sentencia.setInt(4, evaluacionOV.getIdExpediente());

                int filasAfectadas = sentencia.executeUpdate();
                if (filasAfectadas == 0) {
                    throw new SQLException("La inserción de la evaluación OV no afectó ninguna fila.");
                }

                try (ResultSet resultado = sentencia.getGeneratedKeys()) {
                    if (resultado.next()) {
                        idGenerado = resultado.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al guardar la evaluación OV en la base de datos.", e);
        }
        return idGenerado;
    }
}