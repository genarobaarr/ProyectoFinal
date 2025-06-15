/*
 * Omar Morales García
 * 09-06-2025
 */
package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import proyectofinal.modelo.ConexionBD;

public class PeriodoDAO {

    public static int obtenerIdPeriodoActual() throws SQLException {
        int idPeriodo = -1;
        String consultaActual = "SELECT idPeriodo FROM periodo WHERE ? BETWEEN fechaInicio AND fechaFin LIMIT 1";
        String consultaMasReciente = "SELECT idPeriodo FROM periodo ORDER BY fechaFin DESC LIMIT 1";

        try (Connection conexionBD = ConexionBD.abrirConexion()) {
            if (conexionBD == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            try (PreparedStatement sentenciaPeriodoActual = conexionBD.prepareStatement(consultaActual)) {
                sentenciaPeriodoActual.setDate(1, java.sql.Date.valueOf(LocalDate.now()));

                try (ResultSet resultado = sentenciaPeriodoActual.executeQuery()) {
                    if (resultado.next()) {
                        idPeriodo = resultado.getInt("idPeriodo");
                        return idPeriodo;
                    }
                }
            }

            try (PreparedStatement sentenciaUltimoPeriodo = conexionBD.prepareStatement(consultaMasReciente);
                 ResultSet resultadoUltimo = sentenciaUltimoPeriodo.executeQuery()) {
                if (resultadoUltimo.next()) {
                    idPeriodo = resultadoUltimo.getInt("idPeriodo");
                } else {
                    throw new SQLException("No hay periodos registrados en la base de datos.");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el ID del período desde la base de datos.", e);
        }
        return idPeriodo;
    }
}