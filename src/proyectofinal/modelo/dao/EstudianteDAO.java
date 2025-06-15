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

    public static List<Estudiante> obtenerEstudiantesSinProyectoAsignado() throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        String query = "SELECT u.idUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.email, u.username, " +
                       "e.fechaNacimiento, e.matricula, e.idExperiencia " +
                       "FROM usuario u " +
                       "INNER JOIN estudiante e ON u.idUsuario = e.idUsuario " +
                       "LEFT JOIN expediente exp ON e.idUsuario = exp.idEstudiante AND exp.estatus = 'Activo' " +
                       "WHERE exp.idEstudiante is NULL";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

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

                Estudiante estudiante = new Estudiante(fechaNacimiento, matricula, idExperiencia,
                                                       idUsuario, nombre, apellidoPaterno,
                                                       apellidoMaterno, email, username);
                estudiantes.add(estudiante);
            }
        }

        return estudiantes;
    }

    public static void crearExpedienteEstudianteProyecto(int idEstudiante, int idProyecto, int idPeriodo) throws SQLException {
        String query = "INSERT INTO expediente (estatus, horasAcumuladas, idProyecto, idPeriodo, idEstudiante) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "Activo");
            pstmt.setInt(2, 0);
            pstmt.setInt(3, idProyecto);
            pstmt.setInt(4, idPeriodo);
            pstmt.setInt(5, idEstudiante);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo crear el expediente, ninguna fila afectada.");
            }
        }
    }

    public static List<Estudiante> obtenerEstudiantesConReporteMensual() throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        String query = "SELECT DISTINCT u.idUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.email, u.username, " +
                       "e.fechaNacimiento, e.matricula, e.idExperiencia, exp.idExpediente, exp.horasAcumuladas " + 
                       "FROM usuario u " +
                       "INNER JOIN estudiante e ON u.idUsuario = e.idUsuario " +
                       "INNER JOIN expediente exp ON e.idUsuario = exp.idEstudiante " +
                       "INNER JOIN reporte_mensual rm ON exp.idExpediente = rm.idExpediente " +
                       "WHERE exp.estatus = 'Activo'";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

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

                Estudiante estudiante = new Estudiante(fechaNacimiento, matricula, idExperiencia,
                                                       idUsuario, nombre, apellidoPaterno,
                                                       apellidoMaterno, email, username,
                                                       idExpediente, horasAcumuladas);
                estudiantes.add(estudiante);
            }
        }

        return estudiantes;
    }

    public static List<EvaluacionOV> obtenerEvaluacionesOVEstudiante(int idEstudiante) throws SQLException {
        List<EvaluacionOV> evaluaciones = new ArrayList<>();
        String query = "SELECT eo.idEvaluacionOV, eo.comentarios, eo.fecha, eo.puntaje_final " +
                       "FROM evaluacion_ov eo " +
                       "INNER JOIN expediente exp ON eo.idExpediente = exp.idExpediente " +
                       "WHERE exp.idEstudiante = ? ORDER BY eo.fecha ASC";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idEstudiante);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EvaluacionOV evaluacion = new EvaluacionOV();
                    evaluacion.setIdEvaluacionOV(rs.getInt("idEvaluacionOV"));
                    evaluacion.setComentarios(rs.getString("comentarios"));

                    Date fechaSql = rs.getDate("fecha");
                    evaluacion.setFecha(fechaSql != null ? fechaSql.toString() : null);

                    BigDecimal puntaje = rs.getBigDecimal("puntaje_final");
                    evaluacion.setPuntajeFinal(puntaje != null ? puntaje.doubleValue() : 0.0);

                    evaluaciones.add(evaluacion);
                }
            }
        }

        return evaluaciones;
    }

    public static List<EvaluacionExposicion> obtenerEvaluacionesExposicionEstudiante(int idEstudiante) throws SQLException {
        List<EvaluacionExposicion> evaluaciones = new ArrayList<>();
        String query = "SELECT ee.idEvaluacionExposicion, ee.comentarios, ee.puntajeFinal " +
                       "FROM evaluacion_exposicion ee " +
                       "INNER JOIN expediente exp ON ee.idExpediente = exp.idExpediente " +
                       "WHERE exp.idEstudiante = ?";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idEstudiante);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EvaluacionExposicion evaluacion = new EvaluacionExposicion();
                    evaluacion.setIdEvaluacionExposicion(rs.getInt("idEvaluacionExposicion"));
                    evaluacion.setComentarios(rs.getString("comentarios"));

                    BigDecimal puntaje = rs.getBigDecimal("puntajeFinal");
                    evaluacion.setPuntajeFinal(puntaje != null ? puntaje.doubleValue() : 0.0);

                    evaluaciones.add(evaluacion);
                }
            }
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
                       "WHERE u.idUsuario = ?";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idUsuario);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
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

                    estudiante = new Estudiante(fechaNacimiento, matricula, idExperiencia,
                                                idUsuario, nombre, apellidoPaterno,
                                                apellidoMaterno, email, username,
                                                idExpediente, horasAcumuladas);
                }
            }
        }

        return estudiante;
    }
}