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
import java.time.format.DateTimeFormatter;
import proyectofinal.modelo.ConexionBD;

public class PeriodoDAO {
    public static int obtenerIdPeriodoActual() throws SQLException {
        int idPeriodo = -1; // Valor por defecto si no se encuentra
        String consulta = "SELECT idPeriodo FROM periodo WHERE ? BETWEEN fechaInicio AND fechaFin LIMIT 1";

        try (Connection conexionBD = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {

            LocalDate fechaActual = LocalDate.now();
            String fechaActualStr = fechaActual.format(DateTimeFormatter.ISO_LOCAL_DATE);

            sentencia.setString(1, fechaActualStr);
            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {
                idPeriodo = resultado.getInt("idPeriodo");
            } else {
                System.err.println("Advertencia: No se encontró un periodo activo para la fecha actual " + fechaActualStr);
                String latestPeriodQuery = "SELECT idPeriodo FROM periodo ORDER BY fechaFin DESC LIMIT 1";
                try (PreparedStatement sentenciaLatest = conexionBD.prepareStatement(latestPeriodQuery);
                     ResultSet resultadoLatest = sentenciaLatest.executeQuery()) {
                    if (resultadoLatest.next()) {
                        idPeriodo = resultadoLatest.getInt("idPeriodo");
                        System.out.println("DEBUG: Usando el periodo más reciente con ID: " + idPeriodo);
                    } else {
                        throw new RuntimeException("No hay periodos registrados en la base de datos.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el ID del período desde la base de datos.", e);
        }
        return idPeriodo;
    }
}
