/*
 * Omar Morales García
 * 09-06-2025
 */

package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import proyectofinal.modelo.pojo.Estudiante;
import java.util.List;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.ReporteMensual;
import proyectofinal.modelo.pojo.EvaluacionOV;
import proyectofinal.modelo.pojo.EvaluacionExposicion;
import java.math.BigDecimal;

public class EstudianteDAO {

    public List<Estudiante> obtenerEstudiantesSinProyectoAsignado() {
        List<Estudiante> estudiantes = new ArrayList<>();
        String query = "SELECT u.idUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.email, u.username, " +
                       "e.fechaNacimiento, e.matricula, e.idExperiencia " +
                       "FROM usuario u " +
                       "INNER JOIN estudiante e ON u.idUsuario = e.idUsuario " +
                       "LEFT JOIN expediente exp ON e.idUsuario = exp.idEstudiante AND exp.estatus = 'Activo' " +
                       "WHERE exp.idEstudiante is NULL; ";
        
        System.out.println("DEBUG: Executing query for students without assigned project.");
        
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) {
                System.err.println("ERROR: Connection is null in obtenerEstudiantesSinProyectoAsignado.");
                throw new SQLException("No database connection available.");
            }
            if (pstmt == null) {
                System.err.println("ERROR: PreparedStatement is null in obtenerEstudiantesSinProyectoAsignado.");
                throw new SQLException("Failed to create PreparedStatement.");
            }

            while (rs.next()) {
                int idUsuario = rs.getInt("idUsuario");
                String nombre = rs.getString("nombre");
                String apellidoPaterno = rs.getString("apellidoPaterno");
                String apellidoMaterno = rs.getString("apellidoMaterno");
                String email = rs.getString("email");
                String username = rs.getString("username");

                Date fechaNacimientoSql = rs.getDate("fechaNacimiento");
                String fechaNacimiento = (fechaNacimientoSql != null) ? fechaNacimientoSql.toString() : null;
                String matricula = rs.getString("matricula");
                int idExperiencia = rs.getInt("idExperiencia");

                Estudiante estudiante = new Estudiante(fechaNacimiento, matricula, idExperiencia, idUsuario, nombre, apellidoPaterno, apellidoMaterno, email, username);
                estudiantes.add(estudiante);
            }
            System.out.println("DEBUG: Found " + estudiantes.size() + " students without assigned project.");
        } catch (SQLException e) {
            System.err.println("SQL Exception in obtenerEstudiantesSinProyectoAsignado: " + e.getErrorCode() + " - " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al cargar estudiantes sin proyecto desde la base de datos.", e);
        } catch (Exception e) {
            System.err.println("General Exception in obtenerEstudiantesSinProyectoAsignado: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error inesperado al cargar estudiantes sin proyecto.", e);
        }
        return estudiantes;
    }

    public void crearExpedienteEstudianteProyecto(int idEstudiante, int idProyecto, int idPeriodo) {
        String query = "INSERT INTO expediente (estatus, horasAcumuladas, idProyecto, idPeriodo, idEstudiante) VALUES (?, ?, ?, ?, ?)";
        
        System.out.println("DEBUG: Executing query for creating expediente: " + query);
        System.out.println("DEBUG: With idEstudiante = " + idEstudiante + ", idProyecto = " + idProyecto + ", idPeriodo = " + idPeriodo);
        
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (conn == null) {
                System.err.println("ERROR: Connection is null in crearExpedienteEstudianteProyecto.");
                throw new SQLException("No database connection available.");
            }
            if (pstmt == null) {
                System.err.println("ERROR: PreparedStatement is null in crearExpedienteEstudianteProyecto.");
                throw new SQLException("Failed to create PreparedStatement.");
            }

            pstmt.setString(1, "Activo");
            pstmt.setInt(2, 0);
            pstmt.setInt(3, idProyecto);
            pstmt.setInt(4, idPeriodo);
            pstmt.setInt(5, idEstudiante);
            
            System.out.println("DEBUG: Parameters for expediente set.");

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                System.err.println("WARN: No rows affected when creating expediente for Estudiante " + idEstudiante);
                throw new SQLException("No se pudo crear el expediente, ninguna fila afectada.");
            }
            System.out.println("DEBUG: Expediente creado para Estudiante " + idEstudiante + " y Proyecto " + idProyecto + " en Periodo " + idPeriodo + ". Filas afectadas: " + filasAfectadas);

        } catch (SQLException e) {
            System.err.println("SQL Exception in crearExpedienteEstudianteProyecto: " + e.getErrorCode() + " - " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al registrar la asignación del proyecto en la base de datos.", e);
        } catch (Exception e) {
            System.err.println("General Exception in crearExpedienteEstudianteProyecto: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error inesperado al crear expediente.", e);
        }
    }

    public List<Estudiante> obtenerEstudiantesConReporteMensual() {
        List<Estudiante> estudiantesConReporte = new ArrayList<>();
        String query = "SELECT DISTINCT u.idUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.email, u.username, " +
                       "e.fechaNacimiento, e.matricula, e.idExperiencia, exp.idExpediente, exp.horasAcumuladas " + // Añadidos
                       "FROM usuario u " +
                       "INNER JOIN estudiante e ON u.idUsuario = e.idUsuario " +
                       "INNER JOIN expediente exp ON e.idUsuario = exp.idEstudiante " +
                       "INNER JOIN reporte_mensual rm ON exp.idExpediente = rm.idExpediente " +
                       "WHERE exp.estatus = 'Activo';";

        System.out.println("DEBUG: Executing query for students with monthly reports (including expediente info).");
        
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) {
                System.err.println("ERROR: Connection is null in obtenerEstudiantesConReporteMensual.");
                throw new SQLException("No database connection available.");
            }
            if (pstmt == null) {
                System.err.println("ERROR: PreparedStatement is null in obtenerEstudiantesConReporteMensual.");
                throw new SQLException("Failed to create PreparedStatement.");
            }

            while (rs.next()) {
                int idUsuario = rs.getInt("idUsuario");
                String nombre = rs.getString("nombre");
                String apellidoPaterno = rs.getString("apellidoPaterno");
                String apellidoMaterno = rs.getString("apellidoMaterno");
                String email = rs.getString("email");
                String username = rs.getString("username");

                Date fechaNacimientoSql = rs.getDate("fechaNacimiento");
                String fechaNacimiento = (fechaNacimientoSql != null) ? fechaNacimientoSql.toString() : null;
                String matricula = rs.getString("matricula");
                int idExperiencia = rs.getInt("idExperiencia");
                int idExpediente = rs.getInt("idExpediente");
                int horasAcumuladas = rs.getInt("horasAcumuladas");

                Estudiante estudiante = new Estudiante(fechaNacimiento, matricula, idExperiencia, idUsuario, nombre, apellidoPaterno, apellidoMaterno, email, username, idExpediente, horasAcumuladas);
                estudiantesConReporte.add(estudiante);
            }
            System.out.println("DEBUG: Found " + estudiantesConReporte.size() + " students with monthly reports.");
        } catch (SQLException e) {
            System.err.println("SQL Exception in obtenerEstudiantesConReporteMensual: " + e.getErrorCode() + " - " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al cargar estudiantes con reporte desde la base de datos.", e);
        } catch (Exception e) {
            System.err.println("General Exception in obtenerEstudiantesConReporteMensual: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error inesperado al cargar estudiantes con reporte.", e);
        }
        return estudiantesConReporte;
    }

    public List<ReporteMensual> obtenerReportesMensualesEstudiante(int idEstudiante) {
        List<ReporteMensual> reportes = new ArrayList<>();
        String query = "SELECT rm.idReporteMensual, rm.numeroReporteMensual, rm.numeroHoras, rm.observaciones, " +
                       "rm.nombreArchivo, rm.extensionArchivo, rm.archivo " +
                       "FROM reporte_mensual rm " +
                       "INNER JOIN expediente exp ON rm.idExpediente = exp.idExpediente " +
                       "WHERE exp.idEstudiante = ? ORDER BY rm.numeroReporteMensual ASC;";

        System.out.println("DEBUG: Executing query for specific student reports: " + query);
        System.out.println("DEBUG: With idEstudiante = " + idEstudiante);

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (conn == null) {
                System.err.println("ERROR: Connection is null in obtenerReportesMensualesEstudiante.");
                throw new SQLException("No database connection available.");
            }
            if (pstmt == null) {
                System.err.println("ERROR: PreparedStatement is null in obtenerReportesMensualesEstudiante.");
                throw new SQLException("Failed to create PreparedStatement.");
            }

            pstmt.setInt(1, idEstudiante);
            System.out.println("DEBUG: Parameter idEstudiante set: " + idEstudiante);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("DEBUG: Query executed. Checking results for reports...");
                while (rs.next()) {
                    ReporteMensual reporte = new ReporteMensual();
                    reporte.setIdReporteMensual(rs.getInt("idReporteMensual"));
                    reporte.setNumeroReporte(rs.getInt("numeroReporteMensual"));
                    reporte.setNumeroHoras((int) rs.getDouble("numeroHoras")); 
                    reporte.setObservaciones(rs.getString("observaciones"));
                    reporte.setNombreArchivo(rs.getString("nombreArchivo"));
                    reporte.setExtensionArchivo(rs.getString("extensionArchivo"));

                    byte[] archivoBytesPrimitivo = rs.getBytes("archivo");
                    if (archivoBytesPrimitivo != null) {
                        Byte[] archivoBytesObjeto = new Byte[archivoBytesPrimitivo.length];
                        for (int i = 0; i < archivoBytesPrimitivo.length; i++) {
                            archivoBytesObjeto[i] = archivoBytesPrimitivo[i];
                        }
                        reporte.setArchivo(archivoBytesObjeto);
                    } else {
                        reporte.setArchivo(null);
                    }
                    reportes.add(reporte);
                }
                System.out.println("DEBUG: Found " + reportes.size() + " reports for idEstudiante " + idEstudiante);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception in obtenerReportesMensualesEstudiante: " + e.getErrorCode() + " - " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al cargar reportes desde la base de datos.", e);
        } catch (Exception e) {
            System.err.println("General Exception in obtenerReportesMensualesEstudiante: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error inesperado al cargar reportes.", e);
        }
        return reportes;
    }

    public List<EvaluacionOV> obtenerEvaluacionesOVEstudiante(int idEstudiante) {
        List<EvaluacionOV> evaluaciones = new ArrayList<>();
        String query = "SELECT eo.idEvaluacionOV, eo.comentarios, eo.fecha, eo.puntaje_final " +
                       "FROM evaluacion_ov eo " +
                       "INNER JOIN expediente exp ON eo.idExpediente = exp.idExpediente " +
                       "WHERE exp.idEstudiante = ? ORDER BY eo.fecha ASC;";

        System.out.println("DEBUG: Executing query for specific student OV evaluations: " + query);
        System.out.println("DEBUG: With idEstudiante = " + idEstudiante);

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (conn == null) {
                System.err.println("ERROR: Connection is null in obtenerEvaluacionesOVEstudiante.");
                throw new SQLException("No database connection available.");
            }
            if (pstmt == null) {
                System.err.println("ERROR: PreparedStatement is null in obtenerEvaluacionesOVEstudiante.");
                throw new SQLException("Failed to create PreparedStatement.");
            }

            pstmt.setInt(1, idEstudiante);
            System.out.println("DEBUG: Parameter idEstudiante set: " + idEstudiante);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("DEBUG: Query executed. Checking results for OV evaluations...");
                while (rs.next()) {
                    EvaluacionOV evaluacion = new EvaluacionOV();
                    evaluacion.setIdEvaluacionOV(rs.getInt("idEvaluacionOV"));
                    evaluacion.setComentarios(rs.getString("comentarios"));
                    Date fechaSql = rs.getDate("fecha");
                    evaluacion.setFecha(fechaSql != null ? fechaSql.toString() : null);

                    BigDecimal puntajeBigDecimal = rs.getBigDecimal("puntaje_final");
                    evaluacion.setPuntajeFinal(puntajeBigDecimal != null ? puntajeBigDecimal.doubleValue() : 0.0);
                    evaluaciones.add(evaluacion);
                }
                System.out.println("DEBUG: Found " + evaluaciones.size() + " OV evaluations for idEstudiante " + idEstudiante);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception in obtenerEvaluacionesOVEstudiante: " + e.getErrorCode() + " - " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al cargar evaluaciones de la Organización Vinculada desde la base de datos.", e);
        } catch (Exception e) {
            System.err.println("General Exception in obtenerEvaluacionesOVEstudiante: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error inesperado al cargar evaluaciones OV.", e);
        }
        return evaluaciones;
    }

    public List<EvaluacionExposicion> obtenerEvaluacionesExposicionEstudiante(int idEstudiante) {
        List<EvaluacionExposicion> evaluaciones = new ArrayList<>();
        String query = "SELECT ee.idEvaluacionExposicion, ee.comentarios, ee.puntajeFinal " +
                       "FROM evaluacion_exposicion ee " +
                       "INNER JOIN expediente exp ON ee.idExpediente = exp.idExpediente " +
                       "WHERE exp.idEstudiante = ?;";

        System.out.println("DEBUG: Executing query for specific student Exposicion evaluations: " + query);
        System.out.println("DEBUG: With idEstudiante = " + idEstudiante);

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (conn == null) {
                System.err.println("ERROR: Connection is null in obtenerEvaluacionesExposicionEstudiante.");
                throw new SQLException("No database connection available.");
            }
            if (pstmt == null) {
                System.err.println("ERROR: PreparedStatement is null in obtenerEvaluacionesExposicionEstudiante.");
                throw new SQLException("Failed to create PreparedStatement.");
            }

            pstmt.setInt(1, idEstudiante);
            System.out.println("DEBUG: Parameter idEstudiante set: " + idEstudiante);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("DEBUG: Query executed. Checking results for Exposicion evaluations...");
                while (rs.next()) {
                    EvaluacionExposicion evaluacion = new EvaluacionExposicion();
                    evaluacion.setIdEvaluacionExposicion(rs.getInt("idEvaluacionExposicion"));
                    evaluacion.setComentarios(rs.getString("comentarios"));
                    BigDecimal puntajeBigDecimal = rs.getBigDecimal("puntajeFinal");
                    evaluacion.setPuntajeFinal(puntajeBigDecimal != null ? puntajeBigDecimal.doubleValue() : 0.0);
                    evaluaciones.add(evaluacion);
                }
                System.out.println("DEBUG: Found " + evaluaciones.size() + " Exposicion evaluations for idEstudiante " + idEstudiante);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception in obtenerEvaluacionesExposicionEstudiante: " + e.getErrorCode() + " - " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al cargar evaluaciones de exposición desde la base de datos.", e);
        } catch (Exception e) {
            System.err.println("General Exception in obtenerEvaluacionesExposicionEstudiante: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error inesperado al cargar evaluaciones de exposición.", e);
        }
        return evaluaciones;
    }
}