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
import proyectofinal.modelo.pojo.EvaluacionOV;
import proyectofinal.modelo.pojo.EvaluacionExposicion;
import java.math.BigDecimal;

public class EstudianteDAO {

    public static List<Estudiante> obtenerEstudiantesSinProyectoAsignado() {
        List<Estudiante> estudiantes = new ArrayList<>();
        String query = "SELECT u.idUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.email, u.username, " +
                       "e.fechaNacimiento, e.matricula, e.idExperiencia " +
                       "FROM usuario u " +
                       "INNER JOIN estudiante e ON u.idUsuario = e.idUsuario " +
                       "LEFT JOIN expediente exp ON e.idUsuario = exp.idEstudiante AND exp.estatus = 'Activo' " +
                       "WHERE exp.idEstudiante is NULL; ";
        
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) {
                throw new SQLException("No hay conexión a la base de datos.");
            }
            if (pstmt == null) {
                throw new SQLException("No hay conexión a la base de datos.");
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
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar estudiantes sin proyecto desde la base de datos.", e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al cargar estudiantes sin proyecto.", e);
        }
        return estudiantes;
    }

    public static void crearExpedienteEstudianteProyecto(int idEstudiante, int idProyecto, int idPeriodo) {
        String query = "INSERT INTO expediente (estatus, horasAcumuladas, idProyecto, idPeriodo, idEstudiante) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (conn == null) {
                throw new SQLException("No hay conexión a la base de datos.");
            }
            if (pstmt == null) {
                throw new SQLException("No hay conexión a la base de datos.");
            }

            pstmt.setString(1, "Activo");
            pstmt.setInt(2, 0);
            pstmt.setInt(3, idProyecto);
            pstmt.setInt(4, idPeriodo);
            pstmt.setInt(5, idEstudiante);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo crear el expediente, ninguna fila afectada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar la asignación del proyecto en la base de datos.", e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al crear expediente.", e);
        }
    }

    public static List<Estudiante> obtenerEstudiantesConReporteMensual() {
        List<Estudiante> estudiantesConReporte = new ArrayList<>();
        String query = "SELECT DISTINCT u.idUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.email, u.username, " +
                       "e.fechaNacimiento, e.matricula, e.idExperiencia, exp.idExpediente, exp.horasAcumuladas " + 
                       "FROM usuario u " +
                       "INNER JOIN estudiante e ON u.idUsuario = e.idUsuario " +
                       "INNER JOIN expediente exp ON e.idUsuario = exp.idEstudiante " +
                       "INNER JOIN reporte_mensual rm ON exp.idExpediente = rm.idExpediente " +
                       "WHERE exp.estatus = 'Activo';";
        
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) {
                throw new SQLException("No database connection available.");
            }
            if (pstmt == null) {
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
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar estudiantes con reporte desde la base de datos.", e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al cargar estudiantes con reporte.", e);
        }
        return estudiantesConReporte;
    }

    public static List<EvaluacionOV> obtenerEvaluacionesOVEstudiante(int idEstudiante) {
        List<EvaluacionOV> evaluaciones = new ArrayList<>();
        String query = "SELECT eo.idEvaluacionOV, eo.comentarios, eo.fecha, eo.puntaje_final " +
                       "FROM evaluacion_ov eo " +
                       "INNER JOIN expediente exp ON eo.idExpediente = exp.idExpediente " +
                       "WHERE exp.idEstudiante = ? ORDER BY eo.fecha ASC;";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (conn == null) {
                throw new SQLException("No database connection available.");
            }
            if (pstmt == null) {
                throw new SQLException("Failed to create PreparedStatement.");
            }

            pstmt.setInt(1, idEstudiante);

            try (ResultSet rs = pstmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar evaluaciones de la Organización Vinculada desde la base de datos.", e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al cargar evaluaciones OV.", e);
        }
        return evaluaciones;
    }

    public static List<EvaluacionExposicion> obtenerEvaluacionesExposicionEstudiante(int idEstudiante) {
        List<EvaluacionExposicion> evaluaciones = new ArrayList<>();
        String query = "SELECT ee.idEvaluacionExposicion, ee.comentarios, ee.puntajeFinal " +
                       "FROM evaluacion_exposicion ee " +
                       "INNER JOIN expediente exp ON ee.idExpediente = exp.idExpediente " +
                       "WHERE exp.idEstudiante = ?;";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (conn == null) {
                throw new SQLException("No database connection available.");
            }
            if (pstmt == null) {
                throw new SQLException("Failed to create PreparedStatement.");
            }

            pstmt.setInt(1, idEstudiante);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EvaluacionExposicion evaluacion = new EvaluacionExposicion();
                    evaluacion.setIdEvaluacionExposicion(rs.getInt("idEvaluacionExposicion"));
                    evaluacion.setComentarios(rs.getString("comentarios"));
                    BigDecimal puntajeBigDecimal = rs.getBigDecimal("puntajeFinal");
                    evaluacion.setPuntajeFinal(puntajeBigDecimal != null ? puntajeBigDecimal.doubleValue() : 0.0);
                    evaluaciones.add(evaluacion);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar evaluaciones de exposición desde la base de datos.", e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al cargar evaluaciones de exposición.", e);
        }
        return evaluaciones;
    }
    
    public static Estudiante obtenerEstudiantePorIdUsuario(int idUsuario) throws SQLException {
        Estudiante estudiante = null;
        String query = "SELECT u.idUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.email, u.username, " +
                       "e.fechaNacimiento, e.matricula, e.idExperiencia, exp.idExpediente, exp.horasAcumuladas " +
                       "FROM usuario u " +
                       "INNER JOIN estudiante e ON u.idUsuario = e.idUsuario " +
                       "LEFT JOIN expediente exp ON e.idUsuario = exp.idEstudiante " + 
                       "WHERE u.idUsuario = ?;";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (conn == null) {
                throw new SQLException("No database connection available.");
            }
            if (pstmt == null) {
                throw new SQLException("Failed to create PreparedStatement.");
            }

            pstmt.setInt(1, idUsuario);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int usuarioId = rs.getInt("idUsuario");
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

                    estudiante = new Estudiante(fechaNacimiento, matricula, idExperiencia, usuarioId, nombre, apellidoPaterno, apellidoMaterno, email, username, idExpediente, horasAcumuladas);
                }
                System.out.println("DEBUG: Found student: " + (estudiante != null ? estudiante.getNombre() : "null"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar estudiante por ID de usuario desde la base de datos.", e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al cargar estudiante.", e);
        }
        return estudiante;
    }
}