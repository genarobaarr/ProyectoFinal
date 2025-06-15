/*
 * Omar Morales García
 * 11-06-2025
 */
package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.Expediente;
import proyectofinal.modelo.pojo.ResultadoOperacion;

public class ExpedienteDAO {

    public static Expediente obtenerExpedientePorId(int idExpediente) throws SQLException {
        Connection conn = ConexionBD.abrirConexion();
        if (conn == null) {
            throw new SQLException("No hay conexión a la base de datos.");
        }

        String query = "SELECT idExpediente, estatus, horasAcumuladas, idProyecto, idPeriodo, idEstudiante " +
                       "FROM expediente WHERE idExpediente = ?";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, idExpediente);
        ResultSet rs = pstmt.executeQuery();

        Expediente expediente = null;
        if (rs.next()) {
            expediente = new Expediente();
            expediente.setIdExpediente(rs.getInt("idExpediente"));
            expediente.setEstatus(rs.getString("estatus"));
            expediente.setHorasAcumuladas(rs.getInt("horasAcumuladas"));
            expediente.setIdProyecto(rs.getInt("idProyecto"));
            expediente.setIdPeriodo(rs.getInt("idPeriodo"));
            expediente.setIdEstudiante(rs.getInt("idEstudiante"));
        }

        rs.close();
        pstmt.close();
        conn.close();

        return expediente;
    }

    public static Expediente obtenerExpedientePorIdEstudiante(int idEstudiante) throws SQLException {
        Connection conn = ConexionBD.abrirConexion();
        if (conn == null) {
            throw new SQLException("No hay conexión a la base de datos.");
        }

        String query = "SELECT idExpediente, estatus, horasAcumuladas, idProyecto, idPeriodo, idEstudiante " +
                       "FROM expediente WHERE idEstudiante = ?";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, idEstudiante);
        ResultSet rs = pstmt.executeQuery();

        Expediente expediente = null;
        if (rs.next()) {
            expediente = new Expediente();
            expediente.setIdExpediente(rs.getInt("idExpediente"));
            expediente.setEstatus(rs.getString("estatus"));
            expediente.setHorasAcumuladas(rs.getInt("horasAcumuladas"));
            expediente.setIdProyecto(rs.getInt("idProyecto"));
            expediente.setIdPeriodo(rs.getInt("idPeriodo"));
            expediente.setIdEstudiante(rs.getInt("idEstudiante"));
        }

        rs.close();
        pstmt.close();
        conn.close();

        return expediente;
    }

    public static int obtenerIdExpedientePorIdEstudiante(int idEstudiante) throws SQLException {
        Connection conn = ConexionBD.abrirConexion();
        if (conn == null) {
            throw new SQLException("No hay conexión a la base de datos.");
        }

        String query = "SELECT idExpediente FROM expediente WHERE idEstudiante = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, idEstudiante);
        ResultSet rs = pstmt.executeQuery();

        int idExpediente = 0;
        if (rs.next()) {
            idExpediente = rs.getInt("idExpediente");
        }

        rs.close();
        pstmt.close();
        conn.close();

        return idExpediente;
    }

    public static ResultadoOperacion actualizarNumeroHorasAcumuladas(int numeroHoras, int idExpediente) throws SQLException {
        Connection conn = ConexionBD.abrirConexion();
        if (conn == null) {
            throw new SQLException("No hay conexión a la base de datos.");
        }

        String query = "UPDATE expediente SET horasAcumuladas = horasAcumuladas + ? WHERE idExpediente = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, numeroHoras);
        pstmt.setInt(2, idExpediente);

        int filasAfectadas = pstmt.executeUpdate();

        ResultadoOperacion resultado = new ResultadoOperacion();
        if (filasAfectadas == 1) {
            resultado.setError(false);
            resultado.setMensaje("Reporte mensual registrado correctamente.");
        } else {
            resultado.setError(true);
            resultado.setMensaje("Lo sentimos :( no se pudo registrar el reporte mensual. Intente más tarde.");
        }

        pstmt.close();
        conn.close();

        return resultado;
    }
}
