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
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD == null) {
            throw new SQLException("No hay conexión a la base de datos.");
        }

        String consulta = "SELECT idExpediente, estatus, horasAcumuladas, idProyecto, idPeriodo, idEstudiante " +
                       "FROM expediente WHERE idExpediente = ?";

        PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
        sentencia.setInt(1, idExpediente);
        ResultSet resultado = sentencia.executeQuery();

        Expediente expediente = null;
        if (resultado.next()) {
            expediente = new Expediente();
            expediente.setIdExpediente(resultado.getInt("idExpediente"));
            expediente.setEstatus(resultado.getString("estatus"));
            expediente.setHorasAcumuladas(resultado.getInt("horasAcumuladas"));
            expediente.setIdProyecto(resultado.getInt("idProyecto"));
            expediente.setIdPeriodo(resultado.getInt("idPeriodo"));
            expediente.setIdEstudiante(resultado.getInt("idEstudiante"));
        }

        resultado.close();
        sentencia.close();
        conexionBD.close();

        return expediente;
    }

    public static Expediente obtenerExpedientePorIdEstudiante(int idEstudiante) throws SQLException {
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD == null) {
            throw new SQLException("No hay conexión a la base de datos.");
        }

        String consulta = "SELECT idExpediente, estatus, horasAcumuladas, idProyecto, idPeriodo, idEstudiante " +
                       "FROM expediente WHERE idEstudiante = ?";

        PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
        sentencia.setInt(1, idEstudiante);
        ResultSet resultado = sentencia.executeQuery();

        Expediente expediente = null;
        if (resultado.next()) {
            expediente = new Expediente();
            expediente.setIdExpediente(resultado.getInt("idExpediente"));
            expediente.setEstatus(resultado.getString("estatus"));
            expediente.setHorasAcumuladas(resultado.getInt("horasAcumuladas"));
            expediente.setIdProyecto(resultado.getInt("idProyecto"));
            expediente.setIdPeriodo(resultado.getInt("idPeriodo"));
            expediente.setIdEstudiante(resultado.getInt("idEstudiante"));
        }

        resultado.close();
        sentencia.close();
        conexionBD.close();

        return expediente;
    }

    public static int obtenerIdExpedientePorIdEstudiante(int idEstudiante) throws SQLException {
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD == null) {
            throw new SQLException("No hay conexión a la base de datos.");
        }

        String consulta = "SELECT idExpediente FROM expediente WHERE idEstudiante = ?";
        PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
        sentencia.setInt(1, idEstudiante);
        ResultSet resultado = sentencia.executeQuery();

        int idExpediente = 0;
        if (resultado.next()) {
            idExpediente = resultado.getInt("idExpediente");
        }

        resultado.close();
        sentencia.close();
        conexionBD.close();

        return idExpediente;
    }

    public static ResultadoOperacion actualizarNumeroHorasAcumuladas(int numeroHoras, int idExpediente) throws SQLException {
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD == null) {
            throw new SQLException("No hay conexión a la base de datos.");
        }

        String consulta = "UPDATE expediente SET horasAcumuladas = horasAcumuladas + ? WHERE idExpediente = ?";
        PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
        sentencia.setInt(1, numeroHoras);
        sentencia.setInt(2, idExpediente);

        int filasAfectadas = sentencia.executeUpdate();

        ResultadoOperacion resultado = new ResultadoOperacion();
        if (filasAfectadas == 1) {
            resultado.setError(false);
            resultado.setMensaje("Reporte mensual registrado correctamente.");
        } else {
            resultado.setError(true);
            resultado.setMensaje("Lo sentimos :( no se pudo registrar el reporte mensual. Intente más tarde.");
        }

        sentencia.close();
        conexionBD.close();

        return resultado;
    }
}