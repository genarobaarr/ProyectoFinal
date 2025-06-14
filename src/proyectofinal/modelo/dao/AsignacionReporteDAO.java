/*
 * Alejandro Martínez Ramírez
 * 11-06-2025
 */
package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.AsignacionReporte;
import proyectofinal.modelo.pojo.ResultadoOperacion;

public class AsignacionReporteDAO {

    public static AsignacionReporte obtenerAsignacionActual() throws SQLException {
        AsignacionReporte asignacion = null;
        String consulta = "SELECT idAsignacion, titulo, descripcion, fechaInicio, fechaFin, estatus FROM asignacion_reporte";

        try (Connection conexionBD = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            if (resultado.next()) {
                asignacion = new AsignacionReporte();
                asignacion.setIdAsignacion(resultado.getInt("idAsignacion"));
                asignacion.setTitulo(resultado.getString("titulo"));
                asignacion.setDescripcion(resultado.getString("descripcion"));
                asignacion.setFechaInicio(resultado.getString("fechaInicio"));
                asignacion.setFechaFin(resultado.getString("fechaFin"));
                asignacion.setEstatus(resultado.getString("estatus"));
            }
        } catch (SQLException e) {
            throw e;
        }

        return asignacion;
    }

    public static ResultadoOperacion actualizarAsignacion(AsignacionReporte asignacion) throws SQLException {
        ResultadoOperacion resultado = new ResultadoOperacion();
        String consulta = "UPDATE asignacion_reporte SET titulo = ?, descripcion = ?, fechaInicio = ?, fechaFin = ?, estatus = ? WHERE idAsignacion = ?";

        try (Connection conexionBD = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {

            if (conexionBD == null) {
                throw new SQLException("Error: Sin conexión a la base de datos.");
            }

            sentencia.setString(1, asignacion.getTitulo());
            sentencia.setString(2, asignacion.getDescripcion());
            sentencia.setString(3, asignacion.getFechaInicio());
            sentencia.setString(4, asignacion.getFechaFin());
            sentencia.setString(5, asignacion.getEstatus());
            sentencia.setInt(6, asignacion.getIdAsignacion());

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas == 1) {
                resultado.setError(false);
                resultado.setMensaje("Asignación actualizada correctamente.");
            } else {
                resultado.setError(true);
                resultado.setMensaje("No se encontró la asignación a actualizar.");
            }

        } catch (SQLException e) {
            throw e;
        }

        return resultado;
    }

    public static ResultadoOperacion registrarAsignacion(AsignacionReporte asignacion) throws SQLException {
        ResultadoOperacion resultado = new ResultadoOperacion();
        String consulta = "INSERT INTO asignacion_reporte (titulo, descripcion, fechaInicio, fechaFin, estatus) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexionBD = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {

            if (conexionBD == null) {
                throw new SQLException("Error: Sin conexión a la base de datos.");
            }

            sentencia.setString(1, asignacion.getTitulo());
            sentencia.setString(2, asignacion.getDescripcion());
            sentencia.setString(3, asignacion.getFechaInicio());
            sentencia.setString(4, asignacion.getFechaFin());
            sentencia.setString(5, asignacion.getEstatus());

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas == 1) {
                resultado.setError(false);
                resultado.setMensaje("Asignación registrada correctamente.");
            } else {
                resultado.setError(true);
                resultado.setMensaje("No se pudo registrar la asignación.");
            }

        } catch (SQLException e) {
            throw e;
        }

        return resultado;
    }
}
