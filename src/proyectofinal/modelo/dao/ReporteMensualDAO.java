/*
 * Alejandro Martinez Ramirez
 * 10-06-2025
 */

package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.ReporteMensual;
import proyectofinal.modelo.pojo.ResultadoOperacion;

public class ReporteMensualDAO {
    
    public static ArrayList<ReporteMensual> obtenerReportesMensualesEstudiante(int idEstudiante) throws SQLException{
        ArrayList<ReporteMensual> reportes = new ArrayList<>();
        String consulta = "SELECT rm.idReporteMensual, rm.numeroReporteMensual, rm.numeroHoras, rm.observaciones, " +
                       "rm.nombreArchivo, rm.extensionArchivo, rm.archivo, rm.idExpediente " +
                       "FROM reporte_mensual rm " +
                       "INNER JOIN expediente exp ON rm.idExpediente = exp.idExpediente " +
                       "WHERE exp.idEstudiante = ? ORDER BY rm.numeroReporteMensual ASC;";

        try (Connection conexionBD = ConexionBD.abrirConexion();
        PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {
            if (conexionBD == null) {
                throw new SQLException("No hay conexión con la base de datos.");
            }

            sentencia.setInt(1, idEstudiante);
            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    reportes.add(convertirReporteMensual(resultado));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al cargar los reportes desde la base de datos.", e);
        }
        return reportes;
    }

    public static ArrayList<Integer> obtenerNumerosReportesMensualesPorEstudiante(int idEstudiante) throws SQLException{
        ArrayList<Integer> reportes = new ArrayList<>();
        String consulta = "SELECT rm.numeroReporteMensual" +
                       "FROM reporte_mensual rm " +
                       "INNER JOIN expediente exp ON rm.idExpediente = exp.idExpediente " +
                       "WHERE exp.idEstudiante = ? ORDER BY rm.numeroReporteMensual ASC;";

        try (Connection conexionBD = ConexionBD.abrirConexion();
        PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {
            if (conexionBD == null) {
                throw new SQLException("No hay conexión con la base de datos.");
            }

            sentencia.setInt(1, idEstudiante);
            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    reportes.add(resultado.getInt(consulta));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al cargar los reportes desde la base de datos.", e);
        }
        return reportes;
    }

    public static ArrayList<ReporteMensual> obtenerReportesMensualesNoValidados() throws SQLException{
        ArrayList<ReporteMensual> reportes = new ArrayList<>();
        String consulta = "SELECT rm.idReporteMensual, rm.numeroReporteMensual, rm.numeroHoras, rm.observaciones, " +
                       "rm.nombreArchivo, rm.extensionArchivo, rm.archivo, rm.idExpediente " +
                       "FROM reporte_mensual rm " +
                       "INNER JOIN expediente exp ON rm.idExpediente = exp.idExpediente " +
                       "WHERE rm.estatus = 'No validado' ORDER BY rm.numeroReporteMensual ASC ";

        try (Connection conexionBD = ConexionBD.abrirConexion();
        PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {
            if (conexionBD == null) {
                throw new SQLException("No hay conexión con la base de datos.");
            }
            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    reportes.add(convertirReporteMensual(resultado));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al cargar los reportes desde la base de datos.", e);
        }
        return reportes;
    }

    public static ResultadoOperacion registrarReporteMensual(ReporteMensual reporte) throws SQLException {
        ResultadoOperacion resultado = new ResultadoOperacion();
        Connection conexionBD = ConexionBD.abrirConexion();

        if (conexionBD != null) {
            String consulta = "INSERT INTO reporte_mensual (numeroReporteMensual, numeroHoras, observaciones, " +
                              "nombreArchivo, extensionArchivo, idExpediente) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, reporte.getNumeroReporte());
            sentencia.setInt(2, reporte.getNumeroHoras());
            sentencia.setString(3, reporte.getObservaciones());
            sentencia.setString(4, reporte.getNombreArchivo());
            sentencia.setString(5, reporte.getExtensionArchivo());
            sentencia.setInt(6, reporte.getIdExpediente());

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas == 1) {
                resultado.setError(false);
                resultado.setMensaje("Reporte mensual registrado correctamente.");
            } else {
                resultado.setError(true);
                resultado.setMensaje("Lo sentimos :( no se pudo registrar el reporte mensual. Intente más tarde.");
            }

            sentencia.close();
            conexionBD.close();
        } else {
            throw new SQLException("Error: Sin conexión a la base de datos.");
        }

        return resultado;
    }

    public static ResultadoOperacion validarReporteMensual(int idReporteMensual) throws SQLException {
        ResultadoOperacion resultado = new ResultadoOperacion();
        Connection conexionBD = ConexionBD.abrirConexion();

        if (conexionBD != null) {
            String consulta = "UPDATE reporte_mensual SET estatus = 'Validado' WHERE idReporteMensual = ?";

            try (PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {
                sentencia.setInt(1, idReporteMensual);
                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas == 1) {
                    resultado.setError(false);
                    resultado.setMensaje("Reporte mensual validado correctamente.");
                } else {
                    resultado.setError(true);
                    resultado.setMensaje("No se encontró el reporte para validar.");
                }
            } catch (SQLException e) {
                resultado.setError(true);
                resultado.setMensaje("Error al validar el reporte mensual: " + e.getMessage());
                throw e;
            } finally {
                conexionBD.close();
            }
        } else {
            throw new SQLException("Error: Sin conexión a la base de datos.");
        }

        return resultado;
    }
    
    public static int obtenerSiguienteNumeroReporte(int idExpediente) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        int siguienteNumero = 1;
        try {
            conexionBD = ConexionBD.abrirConexion();

            if (conexionBD != null) {
                String consulta = "SELECT MAX(numeroReporteMensual) AS maxReporte FROM reporte_mensual WHERE idExpediente = ?";

                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExpediente);
                resultado = sentencia.executeQuery();

                if (resultado.next()) {
                    int maxNumero = resultado.getInt("maxReporte");
                    if (maxNumero > 0) {
                        siguienteNumero = maxNumero + 1;
                    }
                }
            } else {
                throw new SQLException("Error: Sin conexión a la base de datos.");
            }
        } finally {
            sentencia.close();
            resultado.close();
            conexionBD.close();
        }
        return siguienteNumero;
    }

    public static ReporteMensual convertirReporteMensual(ResultSet resultado) throws SQLException {
        ReporteMensual reporteMensual = new ReporteMensual();

        reporteMensual.setIdReporteMensual(resultado.getInt("idReporteMensual"));
        reporteMensual.setNumeroReporte(resultado.getInt("numeroReporteMensual"));
        reporteMensual.setNumeroHoras(resultado.getInt("numeroHoras"));

        String observaciones = resultado.getString("observaciones");
        reporteMensual.setObservaciones(observaciones != null ? observaciones : "");

        reporteMensual.setNombreArchivo(resultado.getString("nombreArchivo"));
        reporteMensual.setExtensionArchivo(resultado.getString("extensionArchivo"));
        reporteMensual.setArchivo(resultado.getBytes("archivo"));
        reporteMensual.setIdExpediente(resultado.getInt("idExpediente"));

        return reporteMensual;
    }
}