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
import proyectofinal.modelo.pojo.Periodo;

public class PeriodoDAO {

    public static Periodo obtenerPeriodoActual() throws SQLException {
        Periodo periodo = null;
        String consulta = "SELECT idPeriodo, fechaInicio, fechaFin, nombre FROM periodo WHERE ? BETWEEN fechaInicio AND fechaFin LIMIT 1";

        try (Connection conexionBD = ConexionBD.abrirConexion()) {
            if (conexionBD == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            try (PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {
                sentencia.setDate(1, java.sql.Date.valueOf(LocalDate.now()));

                try (ResultSet resultado = sentencia.executeQuery()) {
                    if (resultado.next()) {
                        periodo = new Periodo();
                        periodo.setIdPeriodo(resultado.getInt("idPeriodo"));
                        periodo.setFechaInicio(resultado.getString("fechaInicio"));
                        periodo.setFechaFin(resultado.getString("fechaFin"));
                        periodo.setNombre(resultado.getString("nombre"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el ID del período desde la base de datos.", e);
        }
        return periodo;
    }
    
    public static Periodo obtenerUltimoPeriodoReciente() throws SQLException {
        Periodo periodo = null;
        String consulta = "SELECT idPeriodo, fechaInicio, fechaFin, nombre FROM periodo ORDER BY fechaFin DESC LIMIT 1";

        try (Connection conexionBD = ConexionBD.abrirConexion()) {
            if (conexionBD == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            try (PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
                 ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    periodo = new Periodo();
                    periodo.setIdPeriodo(resultado.getInt("idPeriodo"));
                    periodo.setFechaInicio(resultado.getString("fechaInicio"));
                    periodo.setFechaFin(resultado.getString("fechaFin"));
                    periodo.setFechaFin(resultado.getString("nombre"));
                } else {
                    throw new SQLException("No hay periodos registrados en la base de datos.");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el ID del período desde la base de datos.", e);
        }
        return periodo;
    }
}