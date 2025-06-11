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
import java.util.List;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.ReporteMensual;
import proyectofinal.modelo.pojo.ResultadoOperacion;

public class ReporteMensualDAO {
    
    public static List<ReporteMensual> obtenerReportesMensualesEstudiante(int idEstudiante) {
    List<ReporteMensual> reportes = new ArrayList<>();
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
        try (ResultSet rs = sentencia.executeQuery()) {
            while (rs.next()) {
                reportes.add(convertirReporteMensual(rs));
            }
        }
    } catch (SQLException e) {
        System.err.println("SQL Exception en obtenerReportesMensualesEstudiante: " + e.getMessage());
        throw new RuntimeException("Error al cargar los reportes desde la base de datos.", e);
    }
    return reportes;
}

public static ResultadoOperacion registrarReporteMensual(ReporteMensual reporte) throws SQLException {
    ResultadoOperacion resultado = new ResultadoOperacion();
    Connection conexionBD = ConexionBD.abrirConexion();

    if (conexionBD != null) {
        String consulta = "INSERT INTO reporte_mensual (numeroReporteMensual, numeroHoras, observaciones, " +
                          "nombreArchivo, extensionArchivo, archivo, idExpediente) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
        sentencia.setInt(1, reporte.getNumeroReporte());
        sentencia.setInt(2, reporte.getNumeroHoras());
        sentencia.setString(3, reporte.getObservaciones());
        sentencia.setString(4, reporte.getNombreArchivo());
        sentencia.setString(5, reporte.getExtensionArchivo());
        sentencia.setBytes(6, reporte.getArchivo());
        sentencia.setInt(7, reporte.getIdExpediente());

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

    public static ReporteMensual convertirReporteMensual(ResultSet resultado)throws SQLException{
        ReporteMensual reporteMensual = new ReporteMensual(
        resultado.getInt("idReporteMensual"),
        resultado.getInt("numeroReporteMensual"),
        resultado.getInt("numeroHoras"),
        resultado.getString("observaciones"),
        resultado.getString("nombreArchivo"),
        resultado.getString("extensionArchivo"),
        resultado.getBytes("archivo"),
        resultado.getInt("idExpediente"));
        
        return reporteMensual;
}
}